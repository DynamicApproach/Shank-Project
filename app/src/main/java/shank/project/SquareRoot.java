import java.util.ArrayList;

public class SquareRoot extends BuiltInFunctionNode {
    private Float value;

    public SquareRoot(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        try {
            value = (float) Math.sqrt(Float.parseFloat(arguments.get(0).toString()));
        } catch (NumberFormatException e) {
            System.out.println("Cannot get square root from input: " + value + "\n " + e + "\n");
        }
    }

    public String toString(String input) throws Exception {
        try {
            return Float.toString((float) Math.sqrt(Double.parseDouble(input)));
        } catch (Exception e) {
            throw new Exception("Cannot square root input:" + input + "\n " + e + "\n");
        }
    }

}

