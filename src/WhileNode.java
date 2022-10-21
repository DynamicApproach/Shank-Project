import java.util.ArrayList;

public class WhileNode extends Node {
    //While (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<Node> statementNodes;

    public WhileNode(Node booleanExpression, ArrayList<Node> statementNodes) {
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
    }

    @Override
    public String toString() {
        return "While: " + booleanExpression.toString() + " Do: " + statementNodes.toString();
    }
}