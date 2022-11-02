package edu.mit.csail.sdg.alloy4whole;


import java.util.List;

import at.unisalzburg.dbresearch.apted.costmodel.PerEditOperationStringNodeDataCostModel;
import at.unisalzburg.dbresearch.apted.distance.APTED;
import at.unisalzburg.dbresearch.apted.node.Node;
import at.unisalzburg.dbresearch.apted.node.StringNodeData;
import at.unisalzburg.dbresearch.apted.parser.BracketStringInputParser;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.ast.Module;
import edu.mit.csail.sdg.parser.CompUtil;

public class astDiff {

    public static void main(String[] args) {
        apted();
    }

    public static void alloyParse(String[] args) {
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

    public static void apted() {

        // Parse the input and transform to Node objects storing node information in MyNodeData.
        BracketStringInputParser parser = new BracketStringInputParser();
        Node<StringNodeData> t1 = parser.fromString("{A{B{X}{Y}{F}}{C}}");
        Node<StringNodeData> t2 = parser.fromString("{A{B{X}{Y}{F}}}");
        // Initialise APTED.
        APTED<PerEditOperationStringNodeDataCostModel,StringNodeData> apted = new APTED<>(new PerEditOperationStringNodeDataCostModel(1, 10, 0));
        // Execute APTED.
        float result = apted.computeEditDistance(t1, t2);
        System.out.println("Edit distance = " + result);
        List<int[]> mapping = apted.computeEditMapping();

        for (int[] indexes : mapping)
            System.out.println(indexes[0] + " -- " + indexes[1]);

        System.out.println();
        System.out.println(t1.getChildren());
        System.out.println(t2.getChildren());
    }

}
