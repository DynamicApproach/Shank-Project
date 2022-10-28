public class BooleanExpressionNode extends Node {
    // BooleanExpression (has a left expression and a right expression and a condition)
    private Node left;
    private Node right;
    private Type condition;

    public BooleanExpressionNode(Node left, Type condition, Node right) {
        this.left = left;
        this.right = right;
        this.condition = condition;
    }

    @Override
    public String toString() {
        return left.toString() + " " + condition.toString() + " " + right.toString();
    }
}
