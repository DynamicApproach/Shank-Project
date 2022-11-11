import java.util.ArrayList;

public class IfNode extends StatementNode {
    // (booleanExpressionNode, collection of statementNodes, ifNode)
    private BooleanExpressionNode booleanExpression;
    private ArrayList<StatementNode> statementNodes;
    private IfNode ifNode;

    public IfNode(BooleanExpressionNode statement, ArrayList<StatementNode> children) {
        super("IF", children);
    }

    public IfNode(BooleanExpressionNode statement, ArrayList<StatementNode> children, IfNode ifNode) {
        super("IF", children);
        this.ifNode = ifNode;
        this.booleanExpression = statement;
    }


    @Override
    public String toString() {
        return null;
    }
}