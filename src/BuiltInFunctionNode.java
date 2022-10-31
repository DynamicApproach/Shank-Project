import java.util.ArrayList;

public class BuiltInFunctionNode extends CallableNode {
    private String name;
    private ArrayList arguments;
    private boolean varadic;


    public BuiltInFunctionNode(String name, ArrayList<FuctionNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
        this.name = name;
        this.arguments = arguments;
        this.varadic = varadic;
    }

    public String getName() {
        return name;
    }

}
