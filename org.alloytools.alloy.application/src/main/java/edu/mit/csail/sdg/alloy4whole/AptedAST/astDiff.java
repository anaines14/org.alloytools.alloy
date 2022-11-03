package edu.mit.csail.sdg.alloy4whole.AptedAST;


import java.util.ArrayList;
import java.util.List;

import at.unisalzburg.dbresearch.apted.costmodel.StringUnitCostModel;
import at.unisalzburg.dbresearch.apted.distance.APTED;
import at.unisalzburg.dbresearch.apted.node.Node;
import at.unisalzburg.dbresearch.apted.node.StringNodeData;
import at.unisalzburg.dbresearch.apted.parser.BracketStringInputParser;
import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.parser.CompModule;
import edu.mit.csail.sdg.parser.CompUtil;

public class astDiff {

    public static void main(String[] args) {
        List<AptedAST> asts = new ArrayList<>();

        for (String filename : args) {
            asts.add(alloyParse(filename));
        }

        apted(asts.get(0), asts.get(1));
    }

    public static AptedAST alloyParse(String filename) {
        A4Reporter rep = new A4Reporter() {

            // For example, here we choose to display each "warning" by printing
            // it to System.out
            @Override
            public void warning(ErrorWarning msg) {
                System.out.print("Relevance Warning:\n" + (msg.toString().trim()) + "\n\n");
                System.out.flush();
            }
        };

        // Parse file
        CompModule world = CompUtil.parseEverything_fromFile(rep, null, filename);
        // Get first pred
        Expr pred = world.getAllFunc().get(0).getBody();
        return new AptedAST(pred);
    }


    public static void apted(AptedAST tree1, AptedAST tree2) {

        // Parse the input and transform to Node objects storing node information in MyNodeData.
        BracketStringInputParser parser = new BracketStringInputParser();
        Node<StringNodeData> t1 = parser.fromString(tree1.toString());
        Node<StringNodeData> t2 = parser.fromString(tree2.toString());

        // Initialise APTED.
        APTED<StringUnitCostModel,StringNodeData> apted = new APTED<>(new StringUnitCostModel());

        // Execute APTED.
        float result = apted.computeEditDistance(t1, t2);
        System.out.println("Edit distance = " + result);
        List<int[]> mapping = apted.computeEditMapping();

        for (int[] indexes : mapping)
            System.out.println(indexes[0] + " -- " + indexes[1]);

        System.out.println();
        System.out.println("AST1: " + t1.toString());
        System.out.println("AST2: " + t2.toString());
    }

}

