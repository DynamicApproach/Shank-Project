import java.util.ArrayList;

public class RepeatNode extends StatementNode {
    //Repeat (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<StatementNode> statementNodes;

    public RepeatNode(Node booleanExpression, ArrayList<StatementNode> statementNodes) {
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
    }

    public Node getBooleanExpression() {
        return booleanExpression;
    }

    public ArrayList<StatementNode> getStatementNodes() {
        return statementNodes;
    }

    @Override
    public String toString() {
        return "Repeat: " + statementNodes.toString() + " Until: " + booleanExpression.toString();
    }


}
