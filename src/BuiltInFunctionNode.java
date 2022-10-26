public class BuiltInFunctionNode extends ASTNode {
    private String name;
    private Type type;
    private boolean varadic;

    public BuiltInFunctionNode(String name, Type type, boolean varadic) {
        super(name);
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
