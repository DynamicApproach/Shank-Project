public class MathOpNode extends Node {
    private Node left;
    private Node right;
    private Operation op;
    public MathOpNode(Node left, Node right, Operation op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Operation getOp() {
        return op;
    }

    public String toString() {
        return "(" + left.toString() + " " + op.toString() + " " + right.toString() + ")";
    }
    enum Operation {ADD, SUB, MUL, DIV}
}