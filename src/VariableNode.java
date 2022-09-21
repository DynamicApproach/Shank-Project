//implement 'VariableNode' - is constant class, enum for int, real and ast node for the init value of real/intnode
public class VariableNode extends Node {
    boolean isConstant;
    private String name;
    private Node value;
    private Type type;

    public VariableNode(String name, Node value, Type type, boolean isConstant) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.isConstant = isConstant;
    }

    public String getName() {
        return name;
    }

    public Node getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return "\n" + name + ": = " + value + "\n" + " of type: " + type + "\n" + " is constant: " + isConstant + "\n";
    }

    enum variableType {INT, REAL}
}