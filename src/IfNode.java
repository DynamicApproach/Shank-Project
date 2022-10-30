import java.util.ArrayList;


// TODO: FIX THIS AND PARSER ifExpression()
public class IfNode extends Node {
    //  If (booleanExpression, collection of statementNodes, ifNode)
    private Node booleanExpression;
    private ArrayList<StatementNode> statementNodes;
    private IfNode ifNode;

    public IfNode(Node condition, ArrayList<StatementNode> statements) {
        this.booleanExpression = condition;
        this.statementNodes = statements;
    }

    public IfNode(Node condition, ArrayList<StatementNode> statements, IfNode ifNode) {
        this.booleanExpression = condition;
        this.statementNodes = statements;
        this.ifNode = ifNode;
    }


    @Override
    public String toString() {
        return null;
    }
}