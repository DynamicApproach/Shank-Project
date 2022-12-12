import java.util.ArrayList;
import java.util.Scanner;

public class Read extends BuiltInFunctionNode {
    String name;
    ArrayList<FunctionNode> arguments;
    Scanner scanner;

    public Read(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
        scanner = new Scanner(System.in);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {

        for(int i = 0; i< arguments.size(); i++)
        {
            arguments.get(i).fromString(scanner.nextLine());
        }
        scanner.close();
    }
}
