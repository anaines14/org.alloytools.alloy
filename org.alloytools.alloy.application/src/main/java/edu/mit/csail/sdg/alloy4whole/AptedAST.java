package edu.mit.csail.sdg.alloy4whole;

import java.util.ArrayList;
import java.util.List;

import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprUnary;

public class AptedAST {

    private String         name;
    private List<AptedAST> children;

    public AptedAST(Expr expression) {

        // Unary expression
        if (expression.getClass().equals(ExprUnary.class)) {
            ExprUnary unary = (ExprUnary) expression;
            String op = unary.op.toString();
            this.name = op;

            if (op.equals("NOOP")) {
                this.name = expression.type().toString();
            }
        }

        this.children = new ArrayList<AptedAST>();
    }

    @Override
    public String toString() {
        String ast = "{" + name;

        for (AptedAST child : children) {
            ast += child;
        }

        return ast + "}";
    }

}
