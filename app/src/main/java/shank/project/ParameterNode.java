package shank.project;

public class ParameterNode extends Node {
    private boolean isVariable = false;
    private String name;
    private Type type;
    private String value;

    public ParameterNode(String name, Type type, String value, Boolean isVariable) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isVariable = isVariable;
    }

    public ParameterNode(String name, Boolean isVariable) {
        this.name = name;
        this.isVariable = isVariable;
    }

    public boolean isVariable() {
        return isVariable;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String toString() {
        return isVariable ? "var: " + name + " : " + type : "const: " + name + " : " + type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
