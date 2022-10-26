import java.util.List;

public class FunctionCallNode extends StatementNode {
    private String name;
    private List<ASTNode> arguments;

    public FunctionCallNode(String name, List<ASTNode> arguments) {
        super(name);
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<ASTNode> getArguments() {
        return arguments;
    }

    public String toString() {
        return name + "(" + arguments + ")";
    }
}