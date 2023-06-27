package shank.project;
import java.util.ArrayList;


public class GetRandom extends BuiltInFunctionNode {
    private Float value;

    public GetRandom(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        for (InterpreterDataType idt : arguments) {
            try {
                value = (float) (Math.random() * Float.parseFloat(idt.toString()));
            } catch (NumberFormatException e) {
                throw new Exception("Cannot get random from input: " + value + "\n " + e + "\n");
            }
        }
    }

    public String toString(String input) throws Exception {
        // if cant parse then throw exception
        try {
            return Integer.toString((int) (Math.random() * Integer.parseInt(input)));
        } catch (NumberFormatException e) {
            throw new Exception("Cannot get random number from input:" + input + "\n " + e + "\n");
        }
    }
}
