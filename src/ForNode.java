import java.util.ArrayList;

public class ForNode extends Node {
    // For (variableReference, start ASTNode, end astNode, collection of statementNodes)
    private Node variableReference;
    private Node start;
    private Node end;
    private ArrayList<StatementNode> statementNodes;

    public ForNode(Node variableReference, Node start, Node end, ArrayList<StatementNode> statementNodes) {
        this.variableReference = variableReference;
        this.start = start;
        this.end = end;
        this.statementNodes = statementNodes;
    }

    @Override
    public String toString() {
        return "For: " + variableReference.toString() + " From: " + start.toString() + " To: " + end.toString() + " Do: " + statementNodes.toString();
    }
}
