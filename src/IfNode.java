import java.util.ArrayList;

public class IfNode extends Node {
    //  If (booleanExpression, collection of statementNodes, ifNode)
    private Node booleanExpression;
    private ArrayList<Node> statementNodes;
    private IfNode ifNode;

    public IfNode(Node booleanExpression, ArrayList<Node> statementNodes, IfNode ifNode) {
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
        this.ifNode = ifNode;
    }


    @Override
    public String toString() {
        return null;
    }
}
/*
Why not using typical structure like this?:
public class IfNode extends StatementNode {
    private ExpressionNode condition;
    private StatementNode thenPart;
    private StatementNode elsePart;

    public IfNode(ExpressionNode condition, StatementNode thenPart, StatementNode elsePart) {
        super("if");
        this.condition = condition;
        this.thenPart = thenPart;
        this.elsePart = elsePart;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public StatementNode getThenPart() {
        return thenPart;
    }

    public StatementNode getElsePart() {
        return elsePart;
    }

    public String toString() {
        return "if " + condition + " then " + thenPart + " else " + elsePart;
    }

}
*/