import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("unused")
public class Interpreter {
    private static InterpreterDataType InterpretFunction(FunctionNode function, ArrayList<InterpreterDataType> parameters) {
        // function was just called and have to make the hash map for your local variables and parameters.
        HashMap<String, InterpreterDataType> stringToFuct = new HashMap<>();
        for (int i = 0; i < function.getParameters().size(); i++)
            stringToFuct.put(function.getParameters().get(i).getName(), parameters.get(i));
        for (int i = 0; i < function.getVariables().size(); i++)
            stringToFuct.put(function.getVariables().get(i).getName(), parameters.get(i));

        return InterpretBlock(function.getBody(), stringToFuct);
    }

    //For now, the only statement type that we will handle is function calls.
    //If the statement is a function call, implement the process described in the background section,
    // otherwise we will ignore the statement (for now).
    // Functions body is a list of statements
    // InterpretBlock should take the collection of statements and a hashmap of variables.
    //        // We will loop over the collection of statements.
    //        // type you’re dealing with his function calls. In interpret block you are preparing the list of IDT’s. That will go to interpret function.
    //        // Locate the function definition; this could be a built-in (like read or write) or it could be user-defined.
    //        // Make sure that the number of parameters matches OR that the function definition is variadic and built-in.
    //        // Make a collection of values (InterpreterDataType):
    //        // if in hashmap of functions, get the built in function
    //        // if not, get the user defined function
    private static InterpreterDataType InterpretBlock(ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> stringToFunc) {

        for (StatementNode statement : statements) {
            if (statement instanceof FunctionCallNode functionCall) {
                if (Shank.functionNames.containsKey(functionCall.getName())) {
                    // is built-in function,
                    // check if varadic, if not
                    if (functionCall.getVaradic()) {
                        // is varadic

                        // make a collection of InterpDataType
                        functionCall.getParameters().forEach(param -> {
                            // Add the constant value or the current value of the variable in the invocation`
                        });

                        // Now we call the function (either the interpreter or the “execute” of the built-in function),
                        // passing it our collection.

                        // Finally, we loop over that set of values – the called function might have changed some!
                        //      For each value, if the called function is variadic or the called function is marked as VAR and
                        // the invocation is marked as VAR then`

                        // Update the working variable value with the values “passed back” from the function.`

                    } else {
                        // is not varadic
                        // check if number of parameters matches
                        if (functionCall.getParameters().size() == stringToFunc.size()) {

                            // make a collection of InterpDataType

                            functionCall.getParameters().forEach(param -> {
                                // add to collection

                            });

                        } else {
                            throw new RuntimeException("Number of parameters does not match");
                        }
                    }


                } else {
                    // not a built-in fuction
                    // get the function definition
                    // make sure params match
                    // make a collection of InterpDataType
                    if (functionCall.getParameters().size() == stringToFunc.size()) {

                        // make a collection of InterpDataType

                        functionCall.getParameters().forEach(param -> {
                            // add to collection

                        });

                    } else {
                        throw new RuntimeException("Number of parameters does not match");
                    }
                }


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
            throw new IllegalArgumentException("Node cannot resolve node that's null");
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
