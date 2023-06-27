import java.util.ArrayList;

// return substring of s from start to end of given int and string
public class Left extends BuiltInFunctionNode {

    public Left(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }
    
    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        try {
            int start = Integer.parseInt(arguments.get(1).toString());
            arguments.get(2).fromString(arguments.get(2).toString().substring(start));
        } catch (Exception e) {
            throw new RuntimeException("Error: left: " + e);
        }
    }
}
