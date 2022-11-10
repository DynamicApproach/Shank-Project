import java.util.ArrayList;

// A function call has a name (of the function) and a list of parameters.
// A parameter needs to be its own ASTNode because a parameter can be a variable (VariableReferenceNode) or a constant value (an ASTNode).
public class FunctionCallNode extends StatementNode {
    private String name;
    private ArrayList<StatementNode> parameters;

    public FunctionCallNode(String name, ArrayList<StatementNode> parameters) {
        super(name, parameters);
        this.name = name;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public ArrayList<StatementNode> getParameters() {
        return parameters;
    }

    public String toString() {
        return name + "(" + parameters + ")";
    }
}
