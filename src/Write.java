import java.util.ArrayList;

public class Write extends BuiltInFunctionNode {
    public Write(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        // IDT = interpreter data type
        // write out IDT to console
        System.out.print("Write: \n");
        for (InterpreterDataType idt : arguments) {
            if (idt.toString() != null) {
                System.out.print(idt);
                System.out.print("\n");
            } else {
                throw new Exception("Error: write: IDT is null");
            }
        }
    }

}
