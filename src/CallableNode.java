import java.util.ArrayList;

public class CallableNode extends Node {
    private String name;
    private ArrayList<VariableNode> arguments;

    public CallableNode(String name, ArrayList<VariableNode> arguments) {
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
