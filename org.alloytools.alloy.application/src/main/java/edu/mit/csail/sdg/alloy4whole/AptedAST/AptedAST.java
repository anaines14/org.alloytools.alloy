package edu.mit.csail.sdg.alloy4whole.AptedAST;

import java.util.ArrayList;
import java.util.List;

import edu.mit.csail.sdg.ast.Decl;
import edu.mit.csail.sdg.ast.Expr;
import edu.mit.csail.sdg.ast.ExprBinary;
import edu.mit.csail.sdg.ast.ExprQt;
import edu.mit.csail.sdg.ast.ExprUnary;
import edu.mit.csail.sdg.ast.ExprUnary.Op;
import edu.mit.csail.sdg.ast.ExprVar;
import edu.mit.csail.sdg.ast.Sig.PrimSig;
import edu.mit.csail.sdg.ast.Sig.SubsetSig;

public class AptedAST {

    private String         name;
    private List<AptedAST> children;

    public AptedAST(Expr expression) {
        this.name = "";
        this.children = new ArrayList<AptedAST>();

        System.out.println(expression.getClass().getSimpleName());

        if (expression.getClass().equals(ExprUnary.class))
            this.parse((ExprUnary) expression);
        else if (expression.getClass().equals(ExprQt.class))
            this.parse((ExprQt) expression);
        else if (expression.getClass().equals(PrimSig.class))
            this.name = expression.toString();
        else if (expression.getClass().equals(SubsetSig.class))
            this.name = expression.toString();
        else if (expression.getClass().equals(ExprBinary.class))
            this.parse((ExprBinary) expression);
        else if (expression.getClass().equals(ExprVar.class)) {
            String type = expression.type().toString();
            this.name = "var/" + type.substring(1, type.length()-1);
        }

    }

    public void parse(ExprUnary unary) {
        this.name = unary.op.toString();

        if (unary.op == Op.NOOP) {
            AptedAST sub = new AptedAST(unary.sub);
            this.name = sub.name;
            this.children.addAll(sub.children);
        }

        else
            this.children.add(new AptedAST(unary.sub));
    }

    public void parse(ExprQt qt) {
        this.name = qt.op.toString();

        for (Decl d : qt.decls) {
            this.children.add(new AptedAST(d.expr));
        }

        this.children.add(new AptedAST(qt.sub));
    }

    public void parse(ExprBinary binary) {
        this.name = binary.op.toString();
        this.children.add(new AptedAST(binary.left));
        this.children.add(new AptedAST(binary.right));
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
