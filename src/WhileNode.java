import java.util.ArrayList;

public class WhileNode extends StatementNode {
    //While (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<StatementNode> statementNodes;

    public WhileNode(BooleanExpressionNode booleanExpression, ArrayList<StatementNode> statementNodes) {
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
        return "While: " + booleanExpression.toString() + " Do: " + statementNodes.toString();
    }
}