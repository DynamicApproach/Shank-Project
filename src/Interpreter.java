import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("unused")
public class Interpreter {
    private static InterpreterDataType InterpretFunction(FunctionNode function, ArrayList<InterpreterDataType> parameters) {
        // function was just called and have to make the hash map for your local variables and parameters.
        HashMap<String, InterpreterDataType> stringToFuct = new HashMap<>();
        // Add all of our parameters to the hashmap using the names that our function expects
        // run over the functionNode passed in and attach the variables to the passed in interpeterdatatype
        for (int i = 0; i < function.getParameters().size(); i++)
            stringToFuct.put(function.getParameters().get(i).getName(), parameters.get(i));
        // Next add all the local variables to the hashmap
        // run over the functionNode passed in and attach the local variables to the passed in interpeterdatatype
        for (int i = 0; i < function.getVariables().size(); i++) {
            stringToFuct.put(function.getVariables().get(i).getName(), (parameters.get(i))); // TODO: swap to figure out type using type from variablenode
        }
        return InterpretBlock(function.getBody(), stringToFuct);
    }

    //For now, the only statement type that we will handle is function calls.
    //If the statement is a function call, implement the process described in the background section,
    // otherwise we will ignore the statement (for now).
    // Functions body is a list of statements
    private static InterpreterDataType InterpretBlock(ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> stringToFunc) {
        // InterpretBlock should take the collection of statements and a hashmap of variables.
        // We will loop over the collection of statements.
        // you are interpreting statements that are part of the function right now the only statement
        // type you’re dealing with his function calls. In interpret block you are preparing the list of IDT’s. That will go to interpret function.
        for (StatementNode statement : statements) {
            if (statement instanceof FunctionCallNode functionCall) {
                // If the statement is a function call, implement the process described in the background section,
                // otherwise we will ignore the statement (for now).
                // get the function from the hashmap
                InterpreterDataType function = stringToFunc.get(functionCall.getName());
                // get the parameters from the function call
                ArrayList<VariableNode> parameters = functionCall.getArguments();
                // create a list of interpreter data types
                ArrayList<InterpreterDataType> interpreterDataTypes = new ArrayList<>();
                // loop over the parameters and add the interpreter data types to the list
                for (VariableNode parameter : parameters) {
                    //  interpreterDataTypes.add(InterpretExpression(parameter, stringToFunc));
                }
                // call the function with the list of interpreter data types
                // InterpretFunction(function, interpreterDataTypes);
            }

        }
        return null;
    }

    public static void executeFunction(String functionName, ArrayList<InterpreterDataType> parameters) {
        // get function from function name
        // execute function
        // pass in parameters
        // return value
        functionName = functionName.toLowerCase();

    }

    @SuppressWarnings("unused")
    public float Resolve(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("Node cannot be null");
        }
        if (node instanceof IntegerNode) {
            return ((IntegerNode) node).getValue();
        } else if (node instanceof FloatNode) {
            return ((FloatNode) node).getValue();
        } else if (node instanceof MathOpNode mathOpNode) {
            Float left = Resolve(mathOpNode.getLeft());
            Float right = Resolve(mathOpNode.getRight());
            return switch (mathOpNode.getOp()) {
                case ADD -> left + right;
                case MINUS -> left - right;
                case MULTIPLY -> left * right;
                case DIVIDE -> left / right;
                case MOD -> left % right;
                default -> throw new RuntimeException("Unknown operator: " + mathOpNode.getOp());
            };
        } else {
            throw new RuntimeException("Unknown node type: " + node.getClass().getName());
        }
    }

    public void printTree(Node tre) {
        System.out.println("Tree: --------------------");
        // if int or float print
        if (tre.toString() == null) {
        }
        if (tre instanceof IntegerNode || tre instanceof FloatNode) {
            System.out.print(tre);
        } else if (tre instanceof MathOpNode mathOpNode) {
            // if mathopnode print
            System.out.print(mathOpNode);
            // print left
            System.out.print("(");
            printTree(mathOpNode.getLeft());
            // print right
            System.out.print(",");
            printTree(mathOpNode.getRight());
            System.out.println(")");
        } else {
            System.out.println("End Tree---------------------");
            throw new RuntimeException("Unknown node type: " + tre.getClass().getName());
        }

        System.out.println("End Tree---------------------");
    }
}
