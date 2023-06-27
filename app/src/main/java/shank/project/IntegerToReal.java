import java.util.ArrayList;

public class IntegerToReal extends BuiltInFunctionNode {
    private Float value;

    public IntegerToReal(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }


    public String toString(String input) throws Exception {
        // try to convert string to real if it can return else exception
        try {
            return Float.toString(Float.parseFloat(input));
        } catch (NumberFormatException e) {
            throw new Exception("Cannot convert to real from input: " + input + "\n " + e + "\n");
        }
    }

    public void fromString(String input) throws Exception {
        try {
            this.value = Float.parseFloat(input);
        } catch (NumberFormatException e) {
            throw new Exception("Cannot convert to real from input: " + input + "\n " + e + "\n");
        }
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        for (InterpreterDataType idt : arguments) {
            try {
                value = Float.parseFloat(idt.toString());
            } catch (Exception e) {
                System.out.println("Cannot convert to real from input: " + value + "\n " + e + "\n");
            }
        }

    }
}

