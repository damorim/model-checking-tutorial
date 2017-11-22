/** @author Tomas Balyo, KIT, Karlsruhe */
package ipasir4jEssentials;

import java.util.ArrayList;
import java.util.List;

import ipasir4j.Ipasir4j;
import ipasir4j.Ipasir4j.IPASIR_RESULT;
import ipasir4jEssentials.DimacsParser.BasicFormula;

/**
 * This sample application finds all the variables of a formula
 * that are essential for its satisfiability, i.e., variables
 * that must be assigned to either True or False in each partial
 * satisfying truth assignment of the formula.
 *
 * First the formula is transformed using the dual rail encoding
 * (see DualRailEncoder.java for more information) and then using
 * assumptions we test for each variable if it is essential for
 * the satisfiability of the formula. This is achieved by solving
 * the formula under the assumptions encoding that the tested
 * variable is unassigned.
 */
public class Main {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("USAGE: run.sh <cnf-file>");
			return;
		}
		// parse the input formula.
		BasicFormula f = DimacsParser.parseFromFile(args[0]);
		// construct the dual rail encoding.
		BasicFormula drf = DualRailEncoder.getDualRailEncoding(f);
		// initialize the SAT solver.
		Ipasir4j i4j = new Ipasir4j();
		System.out.println("Initialized solver: " + i4j.getSignature());
		// add the clauses of the dual rail formula to the solver.
		for (int[] cl : drf.clauses) {
			i4j.addClause(cl);
		}
		// solve the formula
		IPASIR_RESULT res = i4j.solve();
		// unsatisfiable formulas have no essential variables.
		if (res != IPASIR_RESULT.SATISFIABLE) {
			System.out.println("Input formula is not satisfiable.");
			return;
		}
		// test each variable to find all the variables that are essential for satisfiability.
		List<Integer> essentials = new ArrayList<Integer>();
		for (int var = 1; var <= f.variablesCount; var++) {
			System.out.println(String.format("Testing variable %3d of %3d", var, f.variablesCount));
			// assume that the variable is unassigned, i.e., neither True nor False.
			i4j.assume(-DualRailEncoder.getPX(var), -DualRailEncoder.getNX(var));
			res = i4j.solve();
			System.out.print("Variable " + var);
			if (res == IPASIR_RESULT.SATISFIABLE) {
				System.out.println(" is Not essential");
			} else {
				System.out.println(" is essential");
				essentials.add(var);
			}
		}
		System.out.println(String.format("Essential variables: %d of %d", essentials.size(), f.variablesCount));
		System.out.println("Esential variables: " + essentials.toString());
	}

}
