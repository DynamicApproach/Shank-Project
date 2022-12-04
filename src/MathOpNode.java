public class MathOpNode extends Node {
    private Node left;
    private Node right;
    private Type op;

    public MathOpNode(Node left, Node right, Type op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @SuppressWarnings("unused")
    public Node getLeft() {
        return left;
    }

    @SuppressWarnings("unused")
    public Node getRight() {
        return right;
    }

    @SuppressWarnings("unused")
    public Type getOp() {
        return op;
    }

    public String toString() {
        return "(" + left.toString() + " " + op.toString() + " " + right.toString() + ")";
    }

    public String getOperator() {
        return op.toString();
    }
}