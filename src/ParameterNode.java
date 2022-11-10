public class ParameterNode extends Node {
    private boolean isVariable = false;
    private String name;
    private Type type;

    public ParameterNode(String name, Type type, Boolean isVariable) {
        this.name = name;
        this.type = type;
        this.isVariable = isVariable;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        // if is var print var first
        if (isVariable) {
            return "var: " + name + " : " + type;
        } else {
            return "const: " + name + " : " + type;
        }
    }
}
