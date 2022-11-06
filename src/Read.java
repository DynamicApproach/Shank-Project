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
            // Take item from command line ->
            // shove thing of that type from cmdline into it
            if (idt instanceof IntDataType) {
                idt = new IntDataType(System.console().readLine());
            } else if (idt instanceof FloatDataType) {
                idt = new FloatDataType(System.console().readLine());
            } else {
                throw new Exception("Invalid type");
            }
        }
    }
}
