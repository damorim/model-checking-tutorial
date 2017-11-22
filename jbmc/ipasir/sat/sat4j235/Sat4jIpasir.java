/** @author Tomas Balyo, KIT, Karlsruhe */
import java.util.ArrayList;
import java.util.List;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;


public class Sat4jIpasir {
	
	enum SolverState {
		input,
		inputContradiction,
		searching,
		satisfiable,
		unsatisfiable,
	}
	
	private static List<ISolver> solvers = new ArrayList<ISolver>();
	private static List<VecInt> clauseToAdd = new ArrayList<VecInt>();
	private static List<VecInt> assumptions = new ArrayList<VecInt>();
	private static List<SolverState> states = new ArrayList<SolverState>();
	
	public static int initSolver() {
		int id = solvers.size();
		solvers.add(SolverFactory.newDefault());
		clauseToAdd.add(new VecInt());
		assumptions.add(new VecInt());
		states.add(SolverState.input);
		return id;
	}
	
	public static void reset(int solverId) {
		solvers.get(solverId).reset();
		clauseToAdd.get(solverId).clear();
		assumptions.get(solverId).clear();
		states.set(solverId, SolverState.input);
	}
	
	public static void add(int solverId, int lit) {
		SolverState state = states.get(solverId);
		if (state == SolverState.inputContradiction) {
			return;
		}
		if (state == SolverState.searching) {
			System.out.println("Cannot add literals while searching.");
			return;
		}
		if (lit == 0) {
			try {
				solvers.get(solverId).addClause(clauseToAdd.get(solverId));
				clauseToAdd.get(solverId).clear();
			} catch (ContradictionException e) {
				states.set(solverId, SolverState.inputContradiction);
			}
		} else {
			clauseToAdd.get(solverId).push(lit);
		}
	}
	
	public static void assume(int solverId, int lit) {
		SolverState state = states.get(solverId);
		if (lit == 0 || state == SolverState.inputContradiction) {
			return;
		}
		if (state == SolverState.searching) {
			System.out.println("Cannot add literals while searching.");
			return;
		}
		assumptions.get(solverId).push(lit);
	}
	
	public static int solve(int solverId) {
		if (states.get(solverId) == SolverState.inputContradiction) {
			return 20;
		}
		states.set(solverId, SolverState.searching);
		try {
			boolean result = solvers.get(solverId).isSatisfiable(assumptions.get(solverId));
			if (result) {
				states.set(solverId, SolverState.satisfiable);
				return 10;
			} else {
				states.set(solverId, SolverState.unsatisfiable);
				return 20;
			}
		} catch (TimeoutException e) {
			states.set(solverId, SolverState.input);
			return 0;
		} finally {
			assumptions.get(solverId).clear();			
		}
	}
	
	public static int val(int solverId, int lit) {
		if (states.get(solverId) != SolverState.satisfiable) {
			System.out.println("ERROR: Cannot use val if the formula is not found to be SAT.");
			return 0;
		}
		int var = Math.abs(lit);
		boolean tval = solvers.get(solverId).model(var);
		if (lit > 0) {
			return tval ? var : -var;
		} else {
			return tval ? -var : var;
		}
	}
	
	public static int failed(int solverId, int lit) {
		if (states.get(solverId) != SolverState.unsatisfiable) {
			System.out.println("ERROR: Cannot use 'failed' if the formula is not found to be UNSAT.");
			return -1;
		}
		boolean failed = solvers.get(solverId).unsatExplanation().contains(lit);
		return failed ? 1 : 0;
	}
	
	public static void terminate(int solverId) {
		solvers.get(solverId).expireTimeout();
	}

}
