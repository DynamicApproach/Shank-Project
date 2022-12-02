import java.util.ArrayList;

public class IfNode extends StatementNode {
    // (booleanExpressionNode, collection of statementNodes, ifNode)
    private BooleanExpressionNode booleanExpression;
    private ArrayList<StatementNode> statementNodes;
    private IfNode ifNode;

    public IfNode(BooleanExpressionNode boolexpress, ArrayList<StatementNode> childrenToExe) {
        this.booleanExpression = boolexpress;
        this.statementNodes = childrenToExe;
    }

    public IfNode(BooleanExpressionNode statement2, ArrayList<StatementNode> children2, IfNode ifNode) {
        this.ifNode = ifNode;
    }

    public BooleanExpressionNode getBooleanExpression() {
        return booleanExpression;
    }

    public ArrayList<StatementNode> getStatementNodes() {
        return statementNodes;
    }

    public IfNode getIfNode() {
        return ifNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (StatementNode statementNode : statementNodes) {
            if (statementNode != null) {
                sb.append(statementNode);
            }
        }

        return (booleanExpression != null ? booleanExpression.toString() : "") + " " + (sb);
    }


    public void setElseStatements(ArrayList<StatementNode> statements) {
        if (ifNode != null) {
            ifNode.setStatements(statements);
        } else {
            this.ifNode = new IfNode(null, statements);
        }
    }

    public void setStatements(ArrayList<StatementNode> statements) {
        this.statementNodes = statements;
    }
}