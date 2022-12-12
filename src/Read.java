import java.util.ArrayList;

public class Read extends BuiltInFunctionNode {
    String name;
    ArrayList<FunctionNode> arguments;

    public Read(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {

        for(int i = 0; i< arguments.size(); i++)
        {
            arguments.get(i).fromString(System.console().readLine());
        }

    }
}
