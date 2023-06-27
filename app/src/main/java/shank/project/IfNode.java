import java.util.ArrayList;

public class IfNode extends StatementNode {

    StringBuilder sb = new StringBuilder();
    // (booleanExpressionNode, collection of statementNodes, ifNode)
    private BooleanExpressionNode booleanExpression;
    private ArrayList<StatementNode> statementNodes;
    private IfNode ifNode;

    public IfNode(BooleanExpressionNode boolexpress, ArrayList<StatementNode> childrenToExe) {
        this.booleanExpression = boolexpress;
        this.statementNodes = childrenToExe;
    }

    public IfNode(BooleanExpressionNode statement2, ArrayList<StatementNode> children2, IfNode ifNode) {
        this.booleanExpression = statement2;
        this.statementNodes = children2;
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

    public void setIfNode(IfNode ifNode) {
        this.ifNode = ifNode;
    }

    @Override
    public String toString() {

        for (StatementNode statementNode : statementNodes) {
            if (statementNode != null) if (statementNode instanceof IfNode)
                if (((IfNode) statementNode).getIfNode() != null)
                    sb.append("If: ").append(booleanExpression.toString()).append(" THEN: ").append(statementNodes.toString()).append(" ELSE: ").append(((IfNode) statementNode).getIfNode().toString());
                else {
                    sb.append("If: ").append(booleanExpression.toString()).append(" THEN: ").append(statementNodes.toString());
                }
            else if (statementNode instanceof ForNode)
                sb.append("FOR: ").append(((ForNode) statementNode).getStart()).append(" Then: ").append(((ForNode) statementNode).getVariableReference()).append(((ForNode) statementNode).getEnd());
            else if (statementNode instanceof WhileNode)
                sb.append("WHILE: ").append(((WhileNode) statementNode).getBooleanExpression()).append(" Then: ").append(((WhileNode) statementNode).getBlock());
            else if (statementNode instanceof FunctionCallNode)
                sb.append("FUNCTIONCALL: ").append(((FunctionCallNode) statementNode).getName()).append(" Then: ").append(((FunctionCallNode) statementNode).getParameters());
            else if (statementNode instanceof AssignmentNode)
                sb.append("ASSIGN: ").append(((AssignmentNode) statementNode).getTarget().toString()).append(" Then: ").append(((AssignmentNode) statementNode).getExpression().toString());
            else if (statementNode instanceof RepeatNode)
                sb.append("Repeat: ").append(((RepeatNode) statementNode).getBooleanExpression()).append(" Then: ").append(((RepeatNode) statementNode).getBlock());
            else {
                System.err.println("Error in IfNode.java");
            }
        }
        return (booleanExpression != null ? booleanExpression.toString() : "") + " " + (sb);
    }


    public void setElseStatements(ArrayList<StatementNode> statements) {
        this.setStatements(statements);
    }

    public void setStatements(ArrayList<StatementNode> statements) {
        this.statementNodes = statements;
    }
}