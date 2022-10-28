import java.util.List;

// A function call has a name (of the function) and a list of parameters.
// A parameter needs to be its own ASTNode because a parameter can be a variable (VariableReferenceNode) or a constant value (an ASTNode).
public class FunctionCallNode extends StatementNode {
    private String name;
    private List<FuctionNode> arguments;

    public FunctionCallNode(String name, List<FuctionNode> arguments) {
        super(name);
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<FuctionNode> getArguments() {
        return arguments;
    }

    public String toString() {
        return name + "(" + arguments + ")";
    }
}
