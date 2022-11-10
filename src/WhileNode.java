import java.util.ArrayList;

public class WhileNode extends BuiltInFunctionNode {
    //While (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<StatementNode> statementNodes;

    public WhileNode(Node booleanExpression, ArrayList<StatementNode> statementNodes) {
        super("while", null, false);
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
    }

    public WhileNode(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        if (arguments.size() != 0) {
            throw new Exception("Invalid number of arguments");
        }
        // TODO: finish
        // check condition
        // if true, continue statements
        // if false, stop

    }

    @Override
    public String toString() {
        return "While: " + booleanExpression.toString() + " Do: " + statementNodes.toString();
    }
}