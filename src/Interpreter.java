import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("unused")
public class Interpreter {
    static HashMap<String, CallableNode> hashmapfuncts;

    public Interpreter(HashMap<String, CallableNode> hashmapfuncts) {
        Interpreter.hashmapfuncts = hashmapfuncts;
    }

    public static void InterpretFunction(FunctionCallNode function, ArrayList<InterpreterDataType> parameters) throws Exception {
        // function was just called and have to make the hash map for your local variables and parameters.
        HashMap<String, InterpreterDataType> VariableHashMap = new HashMap<>();
        for (int i = 0; i < function.getParameters().size(); i++)
            VariableHashMap.put(function.getParameters().get(i).getName(), parameters.get(i));
        if (hashmapfuncts.get(function.getName()) instanceof FunctionNode no) {
            for (VariableNode var : ((FunctionNode) hashmapfuncts.get(function.getName())).getVariables()) {
                if (var.getValue() instanceof IntegerNode) {
                    VariableHashMap.put(var.getName(), new IntDataType(var.getValue().toString()));
                } else if (var.getValue() instanceof FloatNode) {
                    VariableHashMap.put(var.getName(), new FloatDataType(var.getValue().toString()));
                } else if (var.getValue() instanceof CharNode) {
                    VariableHashMap.put(var.getName(), new CharDataType(var.getValue().toString().toCharArray()[0]));
                } else if (var.getValue() instanceof StringNode) {
                    VariableHashMap.put(var.getName(), new StringDataType(var.getValue().toString()));
                } else if (var.getValue() instanceof BooleanNode) {
                    //This is kinda difficult to implement. Wait until everything else works to implement this.
//                    VariableHashMap.put(var.getName(),new BooleanDataType(var.getValue().toString()));
                } else {
                    throw new Exception("uhh");
                }
            }
        }
        FunctionNode functionNode = (FunctionNode) hashmapfuncts.get(function.getName());
        Interpreter.InterpretBlock(functionNode.getBody(), VariableHashMap);
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
        // TODO: INTERPRET BLOCK
        for (StatementNode statement : statements) { // statement is instance of any node
            if (statement instanceof FunctionCallNode functionCall) { // else function call execute
                // is it built in? Is in hashmap?
                if (Shank.functionNames.containsKey(functionCall.getName())) {
                    //builtin
                    FunctionCallNode current;
                    // check param size of FunctionCall and FunctionNode
                    // for each param in functioncallnode, check type and create a datatype with the value in the param
                    // after add all params for each of the data types
                    // check the passed in params from the functioncallnode matches params required in the functionNode
                    // if it does, interpretfunction with the types passed in
                    // iterate over function hashmap
                } else {
                    // funcNode

                }
            } else if (statement instanceof ForNode) {

            } else if (statement instanceof WhileNode) {

            } else if (statement instanceof RepeatNode) {

            } else if (statement instanceof IfNode) {

            } else if (statement instanceof AssignmentNode) {

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

    public boolean EvalBooleanExpression(BooleanExpressionNode boolNode) {
        //TODO: EvalBoolExpression - Assign 8
        return false;
    }

    @SuppressWarnings("unused")
    public String Resolve(Node node) {
        // TODO: Check FIX
        if (node == null) {
            throw new IllegalArgumentException("Node cannot resolve node that's null");
        }
        if (node instanceof IntegerNode) {
            return String.valueOf(((IntegerNode) node).getValue());
        } else if (node instanceof FloatNode) {
            return String.valueOf(((FloatNode) node).getValue());
        } else if (node instanceof MathOpNode mathOpNode) {
            String left = Resolve(mathOpNode.getLeft());
            String right = Resolve(mathOpNode.getRight());
            if (mathOpNode.getLeft() instanceof FloatNode || mathOpNode.getRight() instanceof FloatNode) {
                return switch (mathOpNode.getOperator()) {
                    case "+" -> String.valueOf(Float.parseFloat(left) + Float.parseFloat(right));
                    case "-" -> String.valueOf(Float.parseFloat(left) - Float.parseFloat(right));
                    case "*" -> String.valueOf(Float.parseFloat(left) * Float.parseFloat(right));
                    case "/" -> String.valueOf(Float.parseFloat(left) / Float.parseFloat(right));
                    case "%" -> String.valueOf(Float.parseFloat(left) % Float.parseFloat(right));
                    case "^" ->
                            String.valueOf(Math.pow(Float.parseFloat(left), Float.parseFloat(right)));
                    default -> throw new IllegalArgumentException("Invalid operator");
                };
            } else if (mathOpNode.getLeft() instanceof IntegerNode || mathOpNode.getRight() instanceof IntegerNode) {
                return switch (mathOpNode.getOperator()) {
                    case "+" -> String.valueOf(Integer.parseInt(left) + Integer.parseInt(right));
                    case "-" -> String.valueOf(Integer.parseInt(left) - Integer.parseInt(right));
                    case "*" -> String.valueOf(Integer.parseInt(left) * Integer.parseInt(right));
                    case "/" -> String.valueOf(Integer.parseInt(left) / Integer.parseInt(right));
                    case "%" -> String.valueOf(Integer.parseInt(left) % Integer.parseInt(right));
                    case "^" ->
                            String.valueOf(Math.pow(Integer.parseInt(left), Integer.parseInt(right)));
                    // new IntegerNode(Integer.parseInt(String.valueOf(Integer.parseInt(left) % Integer.parseInt(right))));
                    default -> throw new IllegalArgumentException("Invalid operator");
                };
            } else if (mathOpNode.getLeft() instanceof StringNode || mathOpNode.getRight() instanceof StringNode) {
                if ("+".equals(mathOpNode.getOperator())) {
                    return (right.concat(left));
                }
            } else {
                throw new IllegalArgumentException("Invalid operator");
            }
        } else {
            throw new RuntimeException("Unknown node type: " + node.getClass().getName());
        }
        throw new RuntimeException("Unknown node type: " + node.getClass().getName() + "LEFT: " + ((MathOpNode) node).getLeft() + "RIGHT: " + ((MathOpNode) node).getRight());
    }


    public void printTree(Node tre) {
        System.out.println("Tree: --------------------");
        // if int or float print
        if (tre.toString() == null) {
            System.out.println("null");
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
