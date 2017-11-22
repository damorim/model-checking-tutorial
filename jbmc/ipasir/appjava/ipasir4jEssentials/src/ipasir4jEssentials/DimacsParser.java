/** @author Tomas Balyo, KIT, Karlsruhe */
package ipasir4jEssentials;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DimacsParser {

    /**
     * Basic representation of a CNF formula
     */
    public static class BasicFormula {
        public int variablesCount;
        public List<int[]> clauses;

        public BasicFormula copy() {
        	BasicFormula f = new BasicFormula();
        	f.variablesCount = variablesCount;
        	f.clauses = new ArrayList<int[]>();
        	for (int[] cl : clauses) {
        		f.clauses.add(Arrays.copyOf(cl, cl.length));
        	}
        	return f;
        }

        public BasicFormula shallowCopy() {
        	BasicFormula f = new BasicFormula();
        	f.variablesCount = variablesCount;
        	f.clauses = new ArrayList<int[]>(clauses);
        	return f;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("p cnf %d %d\n", variablesCount, clauses.size()));
            for (int[] cl : clauses) {
                sb.append(Arrays.toString(cl));
                sb.append("\n");
            }
            return sb.toString();
        }

        public void printDimacsToFile(FileWriter out) throws IOException {
            out.write(String.format("p cnf %d %d\n", variablesCount, clauses.size()));
            int varsize = 2 + (int) Math.log10(variablesCount);
            for (int[] cl : clauses) {
                StringBuilder sb = new StringBuilder(varsize*cl.length + 3);
        		for (int lit : cl) {
        			sb.append(lit);
        			sb.append(" ");
        		}
                sb.append("0 \n");
                out.write(sb.toString());
            }
        }

        public void printDimacs(PrintStream out) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("p cnf %d %d\n", variablesCount, clauses.size()));
            for (int[] cl : clauses) {
        		for (int lit : cl) {
        			sb.append(lit);
        			sb.append(" ");
        		}
                sb.append("0 \n");
            }
            out.println(sb.toString());
        }
    }


    /**
     * Parse a dimacs formula file into a basic formula
     * @param filename
     * @return the basic formula or null
     */
    public static BasicFormula parseFromFile(String filename) {
        BasicFormula formula = new BasicFormula();
        formula.clauses = new ArrayList<int[]>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();
			while (line != null) {
	            if (line.startsWith("c")) {
					line = reader.readLine();
	                continue;
	            }
	            if (line.startsWith("p")) {
	                String[] tokens = line.split(" +");
	                formula.variablesCount = Integer.parseInt(tokens[2]);
	                line = reader.readLine();
	                continue;
	            }
	            line = line.trim();
	            if (line.isEmpty()) {
	                line = reader.readLine();
	            	continue;
	            }
	            String[] lits = line.split(" +");
	            int litInClause = 0;
	            int clauseLength = lits.length - 1;
	            int[] literals = new int[clauseLength];
	            for (String slit : lits) {
	                if ("".equals(slit)) {
	                    continue;
	                }
	                int lit = Integer.parseInt(slit);
	                if (lit == 0) {
	                    break;
	                }
	                literals[litInClause] = lit;
	                litInClause++;
	            }
            	formula.clauses.add(literals);
				line = reader.readLine();
			}
	    	reader.close();
	    	return formula;
		} catch (IOException e) {
			try {
				reader.close();
			} catch (Exception e2) {
			}
			return null;
		}
    }
}