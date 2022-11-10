public class ParameterNode extends Node {
    private boolean isVariable = false;
    private String name;
    private String type;
    private String value;

    public ParameterNode(String name, String type, String value, Boolean isVariable) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.isVariable = isVariable;
    }

    public String getName() {
        return name;
    }

    public String getType() {
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}