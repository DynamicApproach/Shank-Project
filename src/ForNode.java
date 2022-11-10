import java.util.ArrayList;

public class ForNode extends BuiltInFunctionNode {
    // For (variableReference, start ASTNode, end astNode, collection of statementNodes)
    private Node variableReference;
    private Node start;
    private Node end;
    private ArrayList<StatementNode> statementNodes;

    public ForNode(Node variableReference, Node start, Node end, ArrayList<StatementNode> statementNodes) {
        super("for", null, true);
        this.variableReference = variableReference;
        this.start = start;
        this.end = end;
        this.statementNodes = statementNodes;
    }

    @Override
    public String toString() {
        return "For: " + variableReference.toString() + " From: " + start.toString() + " To: " + end.toString() + " Do: " + statementNodes.toString();
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        // TODO: finish
    }
}
