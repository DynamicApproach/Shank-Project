import java.util.ArrayList;

public class RealToInteger extends BuiltInFunctionNode {
    private int value;


    public RealToInteger(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        try {
            for (InterpreterDataType idt : arguments) {
                value = Integer.parseInt(idt.toString());
            }
        } catch (Exception e) {
            throw new Exception("Cannot convert to integer from input: " + value + "\n " + e + "\n");
        }
    }

    public String toString(String input) {
        try {
            return Integer.toString(Integer.parseInt(input));
        } catch (Exception e) {
            return "Could not convert to integer from input:" + input + "\n " + e + "\n";
        }
    }

    public void fromString(String input) {
        this.value = Integer.parseInt(input);
    }
}
