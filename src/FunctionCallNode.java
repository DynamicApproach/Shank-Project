import java.util.ArrayList;

// A function call has a name (of the function) and a list of parameters.
// A parameter needs to be its own ASTNode because a parameter can be a variable (VariableReferenceNode) or a constant value (an ASTNode).
public class FunctionCallNode extends StatementNode {
    private String name;
    private ArrayList<VariableNode> arguments;

    public FunctionCallNode(String name, ArrayList<VariableNode> arguments) {
        super(name, arguments);
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public ArrayList<VariableNode> getArguments() {
        return arguments;
    }

    public String toString() {
        return name + "(" + arguments + ")";
    }
}
