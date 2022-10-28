public class BuiltInFunctionNode extends FuctionNode {
    private String name;
    private Type type;
    private boolean varadic;

    public BuiltInFunctionNode(String name, Type type, boolean varadic) {
        super(name);
        this.name = name;
        this.type = type;
        this.varadic = varadic;
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
