import java.util.ArrayList;

public class RepeatNode extends BuiltInFunctionNode {
    //Repeat (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<Node> statementNodes;

    public RepeatNode(Node booleanExpression, ArrayList<Node> statementNodes) {
        super("repeat", null, false);
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
    }

    @Override
    public String toString() {
        return "Repeat: " + statementNodes.toString() + " Until: " + booleanExpression.toString();
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {

    }
}
