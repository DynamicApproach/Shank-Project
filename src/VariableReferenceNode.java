// VariableReferenceNode should contain the name of the variable being referenced.
public class VariableReferenceNode extends FunctionNode {
    private String name;
    private Type type;

    public VariableReferenceNode(String name) {
        super(name);
    }

}

