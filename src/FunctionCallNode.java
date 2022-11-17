import java.util.ArrayList;

// A function call has a name (of the function) and a list of parameters.
// A parameter needs to be its own ASTNode because a parameter can be a variable (VariableReferenceNode) or a constant value (an ASTNode).
public class FunctionCallNode extends StatementNode {
    private String name;
    private ArrayList<ParameterNode> parameters;
    private Boolean varadic;

    public FunctionCallNode(String name, ArrayList<ParameterNode> parameters, Boolean varadic) {
        this.name = name;
        this.parameters = parameters;
        this.varadic = varadic;
    }

    public String getName() {
        return name;
    }

    public ArrayList<ParameterNode> getParameters() {
        return parameters;
    }

    public String toString() {
        return name + "(" + parameters + ")";
    }
}
