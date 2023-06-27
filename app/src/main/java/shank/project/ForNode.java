package shank.project;
import java.util.ArrayList;

public class ForNode extends StatementNode {
    // For (variableReference, start ASTNode, end astNode, collection of statementNodes)
    private VariableReferenceNode variableReference;
    private Node start;
    private Node end;
    private ArrayList<StatementNode> statementNodes;

    public ForNode(VariableReferenceNode variableReference, Node start, Node end, ArrayList<StatementNode> statements) {
        this.variableReference = variableReference;
        this.start = start;
        this.end = end;
        this.statementNodes = statements;
    }

    public Node getStart() {
        return start;
    }

    public VariableReferenceNode getVariableReference() {
        return variableReference;
    }

    public Node getEnd() {
        return end;
    }

    public ArrayList<StatementNode> getStatementNodes() {
        return statementNodes;
    }

    @Override
    public String toString() {
        return "For: " + variableReference.toString() + "START: " + start.toString() + " END: " + end.toString() + " STATEMENT: " + statementNodes.toString();
    }

}
