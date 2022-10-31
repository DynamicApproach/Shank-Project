import java.util.ArrayList;

public class CallableNode extends Node {
    private String name;
    private ArrayList<VariableNode> arguments;
    private boolean varadic;

    public CallableNode(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        this.name = name;
        this.arguments = arguments;
        this.varadic = varadic;
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
