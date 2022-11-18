import java.util.ArrayList;

public class IfNode extends StatementNode {
    // (booleanExpressionNode, collection of statementNodes, ifNode)
    private BooleanExpressionNode booleanExpression;
    private ArrayList<StatementNode> statementNodes;
    private IfNode ifNode;

    public IfNode(BooleanExpressionNode statement, ArrayList<StatementNode> children) {
        this.booleanExpression = statement;
        this.statementNodes = children;
    }

    public IfNode(BooleanExpressionNode statement, ArrayList<StatementNode> children, IfNode ifNode) {
        this.booleanExpression = statement;
        this.statementNodes = children;
        this.ifNode = ifNode;
    }


    @Override
    public String toString() {
        return booleanExpression.toString() + " " + statementNodes.toString() + " " + (ifNode != null ? ifNode.toString() : "");
    }
}