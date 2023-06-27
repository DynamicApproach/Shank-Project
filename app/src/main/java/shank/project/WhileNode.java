import java.util.ArrayList;
import java.util.stream.Collectors;

public class WhileNode extends StatementNode {
    //While (booleanExpression and collection of statementNodes)
    private Node booleanExpression;
    private ArrayList<StatementNode> statementNodes;

    public WhileNode(BooleanExpressionNode booleanExpression, ArrayList<StatementNode> statementNodes) {
        this.booleanExpression = booleanExpression;
        this.statementNodes = statementNodes;
    }

    public BooleanExpressionNode getBooleanExpression() {
        return (BooleanExpressionNode) booleanExpression;
    }

    public ArrayList<StatementNode> getBlock() {
        return statementNodes;
    }

    @Override
    public String toString() {/*
        for (StatementNode statementNode : statementNodes) {
            System.out.println(statementNode);
        }*/
        return "While: " + booleanExpression.toString() + " Do: " + statementNodes.stream().map(Object::toString).collect(Collectors.joining("\n"));
    }
}