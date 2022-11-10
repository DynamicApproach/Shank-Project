import java.util.ArrayList;

public abstract class BuiltInFunctionNode extends CallableNode {
    private boolean varadic;
    private ArrayList<VariableNode> arguments;
    private String name;

    public BuiltInFunctionNode(String name, ArrayList<VariableNode> arguments) {
        super(name, arguments);
    }

    public BuiltInFunctionNode(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments);
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
