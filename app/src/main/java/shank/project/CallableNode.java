package shank.project;
import java.util.ArrayList;


public class CallableNode extends Node {
    private String name;
    private ArrayList<VariableNode> arguments;
    private boolean varadic = false;

    public CallableNode(String name, ArrayList<VariableNode> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public CallableNode(String name, ArrayList<VariableNode> arguments, Boolean varadic) {
        this.name = name;
        this.arguments = arguments;
        this.varadic = varadic;
    }

    public boolean isVaradic() {
        return varadic;
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
