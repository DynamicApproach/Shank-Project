import java.util.ArrayList;

public class Substring extends BuiltInFunctionNode {


    public Substring(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }


    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        // instring, index, length, outstring
        try {
            int start = Integer.parseInt(arguments.get(1).toString());
            int length = Integer.parseInt(arguments.get(2).toString());
            arguments.get(3).fromString(arguments.get(0).toString().substring(start, length));
        } catch (Exception e) {
            throw new RuntimeException("Error: substring: " + e);
        }
    }
}
