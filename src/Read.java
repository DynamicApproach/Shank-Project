import java.util.ArrayList;

public class Read extends BuiltInFunctionNode {
    String name;



    ArrayList<FunctionNode> arguments;

    public Read(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }


    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        // Iterate over the varargs array and read input for each item
        for (InterpreterDataType idt : arguments) {
            // Take item from command line ->
            // shove thing of that type from cmdline into it
            if (idt instanceof IntDataType) {
                idt = new IntDataType(System.console().readLine());
            } else if (idt instanceof FloatDataType) {
                idt = new FloatDataType(System.console().readLine());
            } else if (idt instanceof StringDataType) {
                idt = new StringDataType(System.console().readLine());
            } else if (idt instanceof BooleanDataType) {
                idt = new BooleanDataType(System.console().readLine());
            } else {
                throw new Exception("Cannot read input of type: " + idt.getClass().getName());
            }
        }
    }
}
