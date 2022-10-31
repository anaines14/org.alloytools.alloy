package edu.mit.csail.sdg.alloy4whole;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;

public class astDiff {

    public static void main(String[] args) {
        A4Reporter rep = new A4Reporter() {

            // For example, here we choose to display each "warning" by printing
            // it to System.out
            @Override
            public void warning(ErrorWarning msg) {
                System.out.print("Relevance Warning:\n" + (msg.toString().trim()) + "\n\n");
                System.out.flush();
            }
        };

        for (String filename : args) {
            // Parse+typecheck the model
            System.out.println("=========== Parsing+Typechecking " + filename + " =============");
            Module world = CompUtil.parseEverything_fromFile(rep, null, filename);
            world.showAsTree(null);
        }
    }

}
