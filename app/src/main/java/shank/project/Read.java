import java.util.ArrayList;
import java.util.Scanner;

public class Read extends BuiltInFunctionNode {
    String name;
    ArrayList<FunctionNode> arguments;
    Scanner scanner;

    public Read(String name, ArrayList<VariableNode> arguments, boolean varadic) {
        super(name, arguments, varadic);
        if(scanner == null)
            scanner = new Scanner(System.in);
    }

    @Override
    public void execute(ArrayList<InterpreterDataType> arguments) throws Exception {
        for (InterpreterDataType argument : arguments) {
            System.out.println("Enter a value to read in: ");
            argument.fromString(scanner.nextLine());
        }
    }
}
