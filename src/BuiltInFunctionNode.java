import java.util.ArrayList;

public abstract class BuiltInFunctionNode extends CallableNode {
    private String name;
    private ArrayList arguments;
    private boolean varadic;


    public BuiltInFunctionNode(String name, ArrayList<FuctionNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
        this.name = name;
        this.arguments = arguments;
        this.varadic = varadic;
    }

    //Execute will take a collection of InterpreterDataType objects. Why? Well, when the interpreter finds a call to “read”, for example,
    // it has to be able to call your Java code.
    public abstract void execute(ArrayList<InterpreterDataType> arguments) throws Exception;

    public String getName() {
        return name;
    }

}
