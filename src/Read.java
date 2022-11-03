import java.util.ArrayList;

public class Read extends BuiltInFunctionNode {
    String name;
    ArrayList<FunctionNode> arguments;
    boolean varadic;

    public Read(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        for (InterpreterDataType idt : arguments) {
            // set argu to input from console
            // instance of to get type -> shove thing of that type from cmdline into it
            idt = arguments.set(arguments.indexOf(idt), new InterpreterDataType() {
                @Override
                public String toString(String input) throws Exception {
                    System.out.println("Input a value for toString: ");
                    return System.console().readLine();
                }

                @Override
                public void fromString(String input) throws Exception {
                    System.out.println("Input a value for fromString: ");
                    System.console().readLine();
                }
            });
            // TODO: Fix Read, need to find out type of idt and then read in the correct type or make a string type???
        }
    }
}
