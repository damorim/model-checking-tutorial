/** @author Tomas Balyo, KIT, Karlsruhe */

import java.util.Random;

public class Sat4jIpasirTest {
	
	private boolean check(boolean condition, String message) {
		if (condition) {
			System.out.println(message);
		}
		return condition;
	}
	
	private void addRandom3satFormula(int solverId, int vars, long seed) {
		// cls = 4.25*vars -- phase transition for 3 SAT
		int cls = (vars << 2) + (vars >> 2);
		Random rnd = new Random(seed);
		for (int c = 0; c < cls; c++) {
			for (int l = 0; l < 3; l++) {
				int lit = (1+ rnd.nextInt(vars)) * (rnd.nextBoolean() ? 1 : -1);
				Sat4jIpasir.add(solverId, lit);
			}
			Sat4jIpasir.add(solverId, 0);
		}
	}
	
	private boolean checkSolutionOfRandom3sat(int solverId, int vars, long seed) {
		int cls = (vars << 2) + (vars >> 2);
		Random rnd = new Random(seed);
		for (int c = 0; c < cls; c++) {
			boolean clsSat = false;
			for (int l = 0; l < 3; l++) {
				int lit = (1+ rnd.nextInt(vars)) * (rnd.nextBoolean() ? 1 : -1);
				int var = Math.abs(lit);
				if (Sat4jIpasir.val(solverId, var) == lit) {
					clsSat = true;
				}
			}
			if (!clsSat) {
				return false;
			}
		}
		return true;
	}
	
	private void testBasicSolve() {
		int id = Sat4jIpasir.initSolver();
		addRandom3satFormula(id, 100, 2015);
		int res = Sat4jIpasir.solve(id);
		if (res == 10 && checkSolutionOfRandom3sat(id, 100, 2015)) {
			System.out.println("Test OK.");
		} else {
			System.out.println("Test FAILED.");
		}
	}
	
	private void testSequenceOfSolves() {
		int[] results = {10,10,20,20,20,20,10,20,20,10,20,20,10,10,10,20,10,10,20,20,20,10,20,10,20,20,10,20,10,10};
		int id = Sat4jIpasir.initSolver();
		for (int rep = 0; rep < 30; rep++) {
			Sat4jIpasir.reset(id);
			addRandom3satFormula(id, 125, rep);
			int res = Sat4jIpasir.solve(id);
			if (check(res == 10 && !checkSolutionOfRandom3sat(id, 125, rep), "Test FAILED -- wrong model")
				|| check((res != results[rep]), "Test FAILED -- wrong answer")) {
				return;
			}
		}
		System.out.println("Test OK.");
	}
	
	private void addClause(int solverId, int ... lits) {
		for (int lit : lits) {
			Sat4jIpasir.add(solverId, lit);
		}
		Sat4jIpasir.add(solverId, 0);
	}
	
	private void testAssumptions() {
		int id = Sat4jIpasir.initSolver();
		addClause(id, 1, 2, 3);
		addClause(id, -1, 2);
		addClause(id, 1, -2);
		addClause(id, -1, -2);
		addClause(id, 4, 5);
		
		int res = Sat4jIpasir.solve(id);
		if (check(res != 10, "Test FAILED")) return;
		Sat4jIpasir.assume(id, -3);
		Sat4jIpasir.assume(id, 5);
		res = Sat4jIpasir.solve(id);
		if (check(res != 20, "Test FAILED -- assumption ingored")) return;
		int fail = Sat4jIpasir.failed(id, -3);
		if (check(fail != 1, "Test FAILED -- assumption fail not detected")) return;
		fail = Sat4jIpasir.failed(id, 5);
		if (check(fail != 0, "Test FAILED -- assumption fail wrongly detected")) return;
		res = Sat4jIpasir.solve(id);
		if (check(res != 10, "Test FAILED -- assumption not cleared")) return;
		System.out.println("Test OK.");
	}
	
	// This test assumes that the time to solve the
	// generated formula will be more than 0.1 sec.
	private void testInterrupt() {
		final int id = Sat4jIpasir.initSolver();
		addRandom3satFormula(id, 270, 2015);
		
		Thread stopper = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
					Sat4jIpasir.terminate(id);				
				} catch (InterruptedException e) {
				}
			}
		});
		stopper.start();
		int res = Sat4jIpasir.solve(id);
		if (check(res != 0, "Test FAILED -- not interrupted")) return;
		res = Sat4jIpasir.solve(id);
		if (check(res != 10, "Test FAILED -- wrong answer")) return;
		System.out.println("Test OK.");
	}
	
	private void testConcurrent() {
		Thread[] threads = {null, null, null, null};
		for (int i = 0; i < 4; i++) {
			final int id = Sat4jIpasir.initSolver();
			addRandom3satFormula(id, 260, i);
			threads[i] = new Thread(new Runnable() {
				public void run() {
					int res = Sat4jIpasir.solve(id);
					System.out.println("Result of " + id + " is " + res);
				}
			});
		}
		for (int i = 0; i < 4; i++) {
			threads[i].start();
		}
		for (int i = 0; i < 4; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Test OK.");
	}

	public static void main(String[] args) {
		Sat4jIpasirTest test = new Sat4jIpasirTest();
		test.testBasicSolve();
		test.testSequenceOfSolves();
		test.testAssumptions();
		test.testInterrupt();
		test.testConcurrent();
	}
}
