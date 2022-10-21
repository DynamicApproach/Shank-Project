import java.util.ArrayList;

public class RepeatNode extends Node {
    //Repeat (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<Node> statementNodes;

    public RepeatNode(Node booleanExpression, ArrayList<Node> statementNodes) {
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
    }

    @Override
    public String toString() {
        return "Repeat: " + statementNodes.toString() + " Until: " + booleanExpression.toString();
    }
}
