/** @author Tomas Balyo, KIT, Karlsruhe */
package ipasir4jEssentials;

import ipasir4jEssentials.DimacsParser.BasicFormula;

import java.util.ArrayList;

/**
 * Class for constructing the dual rail encoding of a given formula.
 * In dual rail encoding we define two variables for each variable x:
 * - p_x meaning x is assigned to True
 * - n_x meaning x is assigned to False
 *
 * Then we replace each positive occurence of x by p_x and each negative
 * occurence of x by n_x for each variable x. We add a binary clause
 * of the form (-p_x \/ -n_x) for each x to enforce that the variable x
 * cannot be both True and False. It is however allowed for a variable
 * to be neither True nor False, i.e., unassigned.
 */
public class DualRailEncoder {
	
	public static BasicFormula getDualRailEncoding(BasicFormula f) {
		BasicFormula result = new BasicFormula();
		result.variablesCount = f.variablesCount*2;
		result.clauses = new ArrayList<int[]>();
		
		for (int i = 1; i <= f.variablesCount; i++) {
			result.clauses.add(new int[]{-getPX(i), -getNX(i)});
		}
		
		for (int[] cl : f.clauses) {
			int[] ncl = new int[cl.length];
			for (int i = 0; i < cl.length; i++) {
				ncl[i] = getDR(cl[i]);
			}
			result.clauses.add(ncl);
		}
		return result;
	}
	
	public static int getDR(int lit) {
		return lit > 0 ? getPX(lit) : getNX(-lit);
	}
	
	public static int getPX(int x) {
		return 2*x;
	}
	
	public static int getNX(int x) {
		return 2*x - 1;
	}

}
