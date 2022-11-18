import java.util.ArrayList;

public class Right extends BuiltInFunctionNode {


    public Right(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        try {
            int start = Integer.parseInt(arguments.get(1).toString());
            arguments.get(2).fromString(arguments.get(0).toString().substring(arguments.get(0).toString().length() - start));
        } catch (Exception e) {
            throw new RuntimeException("Error: right: " + e);
        }
    }

}
