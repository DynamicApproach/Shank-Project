/*
Finally, we will build (the beginning of) our interpreter.
        Create a new class called Interpreter.
        Add a method called “Resolve”.
        It will take a Node as a parameter and return a float.
        For now, we will do all math as floating point.
        The parser handles the order of operations, so this function is very simple.
        It should check to see what type the Node is
*/
@SuppressWarnings("unused")
public class Interpreter {
    @SuppressWarnings("unused")
    public float Resolve(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (node instanceof IntegerNode) {
            return ((IntegerNode) node).getValue();
        } else if (node instanceof FloatNode) {
            return ((FloatNode) node).getValue();
        } else if (node instanceof MathOpNode mathOpNode) {
            float left = Resolve(mathOpNode.getLeft());
            float right = Resolve(mathOpNode.getRight());
            return switch (mathOpNode.getOp()) {
                case ADD -> left + right;
                case MINUS -> left - right;
                case MULTIPLY -> left * right;
                case DIVIDE -> left / right;
                default -> throw new RuntimeException("Unknown operator: " + mathOpNode.getOp());
            };
        } else {
            throw new RuntimeException("Unknown node type: " + node.getClass().getName());
        }
    }
}
