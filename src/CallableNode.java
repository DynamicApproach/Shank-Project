import java.util.List;

public class CallableNode extends FuctionNode {
    private String name;
    private List<FuctionNode> arguments;

    public CallableNode(String name, List<FuctionNode> arguments) {
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
