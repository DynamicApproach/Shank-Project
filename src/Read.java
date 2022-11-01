import java.util.ArrayList;

public class Read extends BuiltInFunctionNode {
    String name;
    ArrayList<FuctionNode> arguments;
    boolean varadic;

    public Read(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        for (InterpreterDataType idt : arguments) {
            // set argu to input from console
            // idt = arguments.set(arguments.indexOf(idt), idt.fromString(System.console().readLine()));
            // TODO: Fix Read, need to find out type of idt and then read in the correct type
        }
    }
}
