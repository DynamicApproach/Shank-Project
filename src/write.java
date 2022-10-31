import java.util.ArrayList;

public class write extends BuiltInFunctionNode {
    public write(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        // IDT = interpreter data type
        // write out IDT to console
        for (InterpreterDataType idt : arguments) {
            if (idt.toString() != null) {
                System.out.print(idt);
            } else {
                throw new Exception("Error: write: IDT is null");
            }
        }
    }

}
