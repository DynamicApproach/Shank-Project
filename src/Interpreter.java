import java.util.ArrayList;
import java.util.HashMap;


@SuppressWarnings("unused")
public class Interpreter {
    static HashMap<String, CallableNode> hashmapfuncts;

    public Interpreter(HashMap<String, CallableNode> hashmapfuncts) {
        Interpreter.hashmapfuncts = hashmapfuncts;
    }

    // gets handed a function node and a list of params
    // makes space for the local variables
    // match the parameters to names
    // e.g. Take a,b and 1,2 -> both are now local variables
    // once we have that done, the env is set up
    // now we can call interp block -> Which will be reused
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
                    //VariableHashMap.put(var.getName(),new BooleanDataType(var.getValue().toString()));
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
    // We will loop over the collection of statements.
    // type you’re dealing with his function calls. In interpret block you are preparing the list of IDT’s. That will go to interpret function.
    // Locate the function definition; this could be a built-in (like read or write) or it could be user-defined.
    // Make sure that the number of parameters matches OR that the function definition is variadic and built-in.
    // Make a collection of values (InterpreterDataType):
    // if in hashmap of functions, get the built-in function
    // if not, get the user defined function
    private static InterpreterDataType InterpretBlock(ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> VariableHashMap) {
        // TODO: INTERPRET BLOCK
        //
        // Passes a collection of statements and a hashmap of variables
        // walk the list of statements and do each one
        // check what type of statement it is
        //


        for (StatementNode statement : statements) { // statement is instance of any node
            if (statement instanceof FunctionCallNode functionCall) { // else function call execute
                String name = functionCall.getName();
                if (hashmapfuncts.containsKey(name)) { // is it built in? Is in hashmap?
                    FunctionCallNode current;
                    //builtin
                    // a, b, 2
                    //just pushing all params
                    // check param size of FunctionCall and FunctionNode
                    if (hashmapfuncts.get(name).getArguments().size() == functionCall.getParameters().size()) {
                        ArrayList<InterpreterDataType> parameters = new ArrayList<>();
                        for (var param : functionCall.getParameters()) {
                            // for each param in functioncallnode, check type and create a datatype with the value in the param
                            Type parType = param.getType();
                            if (parType == Type.INTEGER) {
                                parameters.add(new IntDataType(param.toString()));
                            } else if (parType == Type.FLOAT) {
                                parameters.add(new FloatDataType(param.toString()));
                            } else if (parType == Type.CHAR) {
                                parameters.add(new CharDataType(param.toString().toCharArray()[0]));
                            } else if (parType == Type.STRING) {
                                parameters.add(new StringDataType(param.toString()));
                            }
                        }
                        try {
                            // if in hashmap, call execute
                            if (hashmapfuncts.containsKey(name)) {
                                executeFunction(hashmapfuncts.get(name), parameters);
                            } else {
                                InterpretFunction(functionCall, parameters);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        throw new RuntimeException("Error: Incorrect number of parameters for function " + functionCall.getName());
                    }

                    // after adding all the params for each of the data types
                    // interpretfunction with the types passed in
                    // iterate over function hashmap
                    // need to know if both are var or not - do the vars match up between call and definition
                } else {
                    // funcNode
                    // not in hashmap
                    throw new RuntimeException("Error: Function " + functionCall.getName() + " is not defined");
                    /*ArrayList<ParameterNode> parameters = functionCall.getParameters();
                    for (int i = 0; i < parameters.size(); i++) {
                        ParameterNode param = parameters.get(i);
                        // check if both have is var or not
                        // if both are var is ok, else throw error
                        boolean functparam = hashmapfuncts.get(name).getArguments().get(i).isConstant();
                        boolean callparam = param.isVariable();
                        if (functparam != callparam) {
                            throw new RuntimeException("Error: Mismatch of parameters for function " + functionCall.getName());
                        }
                    }
                    for (var param : functionCall.getParameters()) {
                        Type parType = param.getType();
                        if (parType == Type.INTEGER) {
                            stringToFunc.put(param.getName(), new IntDataType(param.toString()));
                        } else if (parType == Type.FLOAT) {
                            stringToFunc.put(param.getName(), new FloatDataType(param.toString()));
                        } else if (parType == Type.CHAR) {
                            stringToFunc.put(param.getName(), new CharDataType(param.toString().toCharArray()[0]));
                        } else if (parType == Type.STRING) {
                            stringToFunc.put(param.getName(), new StringDataType(param.toString()));
                        }
                    }
                    // hashmapfuncts.put(functionCall.getName(), new FunctionNode(functionCall.getName(),*/
                }
            } else if (statement instanceof ForNode) {


            } else if (statement instanceof WhileNode) {

            } else if (statement instanceof RepeatNode) {

            } else if (statement instanceof IfNode) {


            } else if (statement instanceof AssignmentNode) {
                // we get a variable, and we know it's an assignment node
                // look up var ref node in the hash map
                if (VariableHashMap.containsKey(((AssignmentNode) statement).getTarget().toString())) {
                    // if it's in the hashmap, we know it's a variable
                    // get the value of the assignment node
                    var value = ((AssignmentNode) statement).getExpression();
                    // get the type of the value
                    if (value instanceof IntegerNode) {
                        // if it's an integer, we know it's an int
                        // get the value of the integer node
                        int val = ((IntegerNode) value).getValue();
                        // put the value in the hashmap
                        VariableHashMap.put(((AssignmentNode) statement).getTarget().toString(), new IntDataType(val));
                    } else if (value instanceof FloatNode) {
                        // if it's a float, we know it's a float
                        // get the value of the float node
                        float val = ((FloatNode) value).getValue();
                        // put the value in the hashmap
                        VariableHashMap.put(((AssignmentNode) statement).getTarget().toString(), new FloatDataType(val));
                    } else if (value instanceof CharNode) {
                        // if it's a char, we know it's a char
                        // get the value of the char node
                        char val = ((CharNode) value).getValue();
                        // put the value in the hashmap
                        VariableHashMap.put(((AssignmentNode) statement).getTarget().toString(), new CharDataType(val));
                    } else if (value instanceof StringNode) {
                        // if it's a string, we know it's a string
                        // get the value of the string node
                        String val = ((StringNode) value).getValue();
                        // put the value in the hashmap
                        VariableHashMap.put(((AssignmentNode) statement).getTarget().toString(), new StringDataType(val));
                    }
                    // set the value of the variable in the hashmap to the value of the assignment node
                } else {
                    // if it's not in the hashmap, we know it's a constant
                    // throw an error
                    throw new RuntimeException("Error: Cannot assign to constant " + statement);
                }
                // throw except if not exsist
                // if it does, get the type
                // resolve the right hand side
                // update the value of the variable
            }
        }
        return null;
    }

    public static void executeFunction(CallableNode functionName, ArrayList<InterpreterDataType> parameters) throws Exception {
        BuiltInFunctionNode exe = (BuiltInFunctionNode) functionName;
        exe.execute(parameters);
    }

    // resolve boolean ->
    // resolve float
    // resolve int
    // if not int or float, throw exception if not their type
    // inside try catch
    // resolve float on right left
    // if not throw execption
    // resolve int on right left
    // if not throw exception
    // check for true false
    public boolean EvalBooleanExpression(BooleanExpressionNode boolNode) {
        //TODO: EvalBoolExpression - Assign 8
        return false;
    }

    public int ResolveInt(Node nodey) {
        if (nodey instanceof FloatNode) {
            return (int) Float.parseFloat(nodey.toString());
        } else if (nodey instanceof MathOpNode) {
            return switch (((MathOpNode) nodey).getOperator()) {
                case "+" -> ResolveInt(((MathOpNode) nodey).getLeft()) + ResolveInt(((MathOpNode) nodey).getRight());
                case "-" -> ResolveInt(((MathOpNode) nodey).getLeft()) - ResolveInt(((MathOpNode) nodey).getRight());
                case "*" -> ResolveInt(((MathOpNode) nodey).getLeft()) * ResolveInt(((MathOpNode) nodey).getRight());
                case "/" -> ResolveInt(((MathOpNode) nodey).getLeft()) / ResolveInt(((MathOpNode) nodey).getRight());
                case "%" -> ResolveInt(((MathOpNode) nodey).getLeft()) % ResolveInt(((MathOpNode) nodey).getRight());
                case "^" ->
                        (int) Math.pow(ResolveInt(((MathOpNode) nodey).getLeft()), ResolveInt(((MathOpNode) nodey).getRight()));
                default -> throw new RuntimeException("Error: Invalid operator in math expression");
            };

        } else {
            throw new RuntimeException("Not an int or float");
        }
    }

    public float ResolveFloat(Node nodey) {
        if (nodey instanceof FloatNode) {
            return Float.parseFloat(nodey.toString());
        } else {
            throw new RuntimeException("Not an int or float");
        }
    }

    public char ResolveChar(Node nodey) {
        if (nodey instanceof CharNode) {
            return nodey.toString().toCharArray()[0];
        } else {
            throw new RuntimeException("Not a char");
        }
    }

    public String ResolveString(Node nodey) {
        if (nodey instanceof StringNode) {
            return nodey.toString();
        } else {
            throw new RuntimeException("Not a string");
        }
    }


    public boolean ResolveBoolean(Node nodey) {
        if (nodey instanceof BooleanExpressionNode) {
            return Boolean.parseBoolean(nodey.toString()); // TODO: change from inbuilt parser
        } else if (nodey instanceof MathOpNode) {
            // call resolvebool on left and right
            // call resolveint on left and right
            // call resolvefloat
        } else {
            throw new RuntimeException("Not a boolean");


        }
        return false;
    }

    @SuppressWarnings("unused")
    public String Resolve(Node node) {
        // TODO: Change to individual -> ResolveInt, ResolveFloat, ResolveChar, ResolveString, ResolveBoolean - resolve bool has to be recursive to look for the type
        // resolve just returns the value of the node
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
                    case "^" -> String.valueOf(Math.pow(Float.parseFloat(left), Float.parseFloat(right)));
                    default -> throw new IllegalArgumentException("Invalid operator");
                };
            } else if (mathOpNode.getLeft() instanceof IntegerNode || mathOpNode.getRight() instanceof IntegerNode) {
                return switch (mathOpNode.getOperator()) {
                    case "+" -> String.valueOf(Integer.parseInt(left) + Integer.parseInt(right));
                    case "-" -> String.valueOf(Integer.parseInt(left) - Integer.parseInt(right));
                    case "*" -> String.valueOf(Integer.parseInt(left) * Integer.parseInt(right));
                    case "/" -> String.valueOf(Integer.parseInt(left) / Integer.parseInt(right));
                    case "%" -> String.valueOf(Integer.parseInt(left) % Integer.parseInt(right));
                    case "^" -> String.valueOf(Math.pow(Integer.parseInt(left), Integer.parseInt(right)));
                    // new IntegerNode(Integer.parseInt(String.valueOf(Integer.parseInt(left) % Integer.parseInt(right))));
                    default -> throw new IllegalArgumentException("Invalid operator");
                };
            } else if (mathOpNode.getLeft() instanceof StringNode || mathOpNode.getRight() instanceof StringNode) {
                if ("+".equals(mathOpNode.getOperator())) {
                    return (right + left);
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
