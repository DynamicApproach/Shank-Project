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
            if (((FunctionNode) hashmapfuncts.get(function.getName())).getVariablesAvalible()) {
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
                        VariableHashMap.put(var.getName(), new BooleanDataType(((BooleanNode) var.getValue()).getValue()));
                    } else {
                        throw new Exception("InterpretFunction: Variable type not found");
                    }
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
        // Passes a collection of statements and a hashmap of variables
        // walk the list of statements and do each one
        // check what type of statement it is
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
                }
            } else if (statement instanceof ForNode forNode) {
                VariableReferenceNode variable = forNode.getVariableReference();
                Node start = forNode.getStart();
                ArrayList<StatementNode> body = forNode.getStatementNodes();
                Node end = forNode.getEnd();
                if (!VariableHashMap.containsKey((variable.toString()))) {
                    // if it's not in the hashmap throw an error
                    throw new RuntimeException("Error: Cannot assign to constant " + statement);
                } else {
                    // get the value of the ref node
                    // compare it to the end
                    // if it's less than the end, execute the body
                    // increment the value of the ref node
                    // repeat
                    // if it's greater than the end, stop
                    var value = VariableHashMap.get(variable.toString());
                    if (value instanceof IntDataType) {
                        int startValue = Integer.parseInt(start.toString());
                        int endValue = Integer.parseInt(end.toString());
                        for (int i = startValue; i < endValue; i++) {
                            VariableHashMap.put(variable.toString(), new IntDataType(i));
                            InterpretBlock(body, VariableHashMap);
                        }
                    } else if (value instanceof FloatDataType) {
                        float startValue = Float.parseFloat(start.toString());
                        float endValue = Float.parseFloat(end.toString());
                        for (float i = startValue; i < endValue; i++) {
                            VariableHashMap.put(variable.toString(), new FloatDataType(i));
                            InterpretBlock(body, VariableHashMap);
                        }
                    } else {
                        throw new RuntimeException("Error: Cannot iterate over " + value);
                    }
                }
            } else if (statement instanceof WhileNode) {
                BooleanExpressionNode express = ((WhileNode) statement).getBooleanExpression();
                ArrayList<StatementNode> block = ((WhileNode) statement).getBlock();
                if (ResolveBoolean(express)) {
                    InterpretBlock(block, VariableHashMap);
                }
            } else if (statement instanceof RepeatNode) {
                // we have a repeat node with an expression and a block
                // we need to evaluate the expression
                // if true, we need to interpret the block
                // if false, we need to break out of the loop
                BooleanExpressionNode express = (BooleanExpressionNode) ((RepeatNode) statement).getBooleanExpression();
                ArrayList<StatementNode> block = ((RepeatNode) statement).getBlock();
                if (ResolveBoolean(express)) {
                    InterpretBlock(block, VariableHashMap);
                } else {
                    break;
                }
            } else if (statement instanceof IfNode) {
                // we have an ifnode with an expression and a block
                // we need to evaluate the expression
                // if true, we need to interpret the block
                // if false, we need to break out of the loop
                BooleanExpressionNode express = ((IfNode) statement).getBooleanExpression();
                ArrayList<StatementNode> block = ((IfNode) statement).getStatementNodes();
                if (ResolveBoolean(express)) {
                    InterpretBlock(block, VariableHashMap);
                }
            } else if (statement instanceof AssignmentNode curStatement) {
                // cast to assignment node
                if (!VariableHashMap.containsKey((curStatement.getTarget().toString()))) {
                    // if it's not in the hashmap throw an error
                    throw new RuntimeException("Error: Cannot assign to constant " + statement);
                } else {
                    var value = curStatement.getExpression();
                    if (value instanceof IntegerNode) {
                        int val = ((IntegerNode) value).getValue();
                        VariableHashMap.put((curStatement).getTarget().toString(), new IntDataType(val));
                    } else if (value instanceof FloatNode) {
                        float val = ((FloatNode) value).getValue();
                        VariableHashMap.put((curStatement).getTarget().toString(), new FloatDataType(val));
                    } else if (value instanceof CharNode) {
                        char val = ((CharNode) value).getValue();
                        VariableHashMap.put((curStatement).getTarget().toString(), new CharDataType(val));
                    } else if (value instanceof StringNode) {
                        String val = ((StringNode) value).getValue();
                        VariableHashMap.put((curStatement).getTarget().toString(), new StringDataType(val));
                    }
                }
            }
        }
        return null;
    }

    public static void executeFunction(CallableNode functionName, ArrayList<InterpreterDataType> parameters) throws Exception {
        BuiltInFunctionNode exe = (BuiltInFunctionNode) functionName;
        exe.execute(parameters);
    }


    // a > b
    // have to resolve both side
    // try to resolve both to float or int
    // resolve boolean ->
    // resolve float
    // resolve int
    // resolve true or false

    // if not int or float, throw exception if not their type
    // inside try catch
    // resolve float on right left
    // if not throw execption
    // resolve int on right left
    // if not throw exception
    // check for true false

    public static boolean ResolveBoolean(Node nodey) {
        if (nodey instanceof BooleanExpressionNode) {
            return resolveBoolean((BooleanExpressionNode) nodey);
        } else if (nodey instanceof MathOpNode) {
            // call resolvebool on left and right
            try {
                return ResolveBoolean(((MathOpNode) nodey).getLeft()) && ResolveBoolean(((MathOpNode) nodey).getRight());
            } catch (Exception e) {
                // call resolveint on left and right
                try {
                    return ResolveInt(((MathOpNode) nodey).getLeft()) == ResolveInt(((MathOpNode) nodey).getRight());
                } catch (Exception ex) {
                    // call resolvefloat
                    try {
                        return ResolveFloat(((MathOpNode) nodey).getLeft()) == ResolveFloat(((MathOpNode) nodey).getRight());
                    } catch (Exception res) {
                        // call resolve string
                        try {
                            return ResolveString(((MathOpNode) nodey).getLeft()).equals(ResolveString(((MathOpNode) nodey).getRight()));
                        } catch (Exception re) {
                            // call resolve char
                            try {
                                return ResolveChar(((MathOpNode) nodey).getLeft()) == ResolveChar(((MathOpNode) nodey).getRight());
                            } catch (Exception r) {
                                // call resolve bool
                                try {
                                    return ResolveBoolean(((MathOpNode) nodey).getLeft()) == ResolveBoolean(((MathOpNode) nodey).getRight());
                                } catch (Exception e1) {
                                    throw new RuntimeException("Error: Cannot resolve " + nodey);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Not a boolean");
        }
    }

    public static boolean resolveBoolean(BooleanExpressionNode boolNode) {
        try {
            try {
                int left = ResolveInt(boolNode.getLeft());
                int right = ResolveInt(boolNode.getRight());
                if (boolNode.getOperator() == Type.GREATER) {
                    return left > right;
                } else if (boolNode.getOperator() == Type.LESS) {
                    return left < right;
                } else if (boolNode.getOperator() == Type.GREATER_EQUAL) {
                    return left >= right;
                } else if (boolNode.getOperator() == Type.LESS_EQUAL) {
                    return left <= right;
                } else if (boolNode.getOperator() == Type.EQUAL) {
                    return left == right;
                } else if (boolNode.getOperator() == Type.NOT_EQUAL) {
                    return left != right;
                }
            } catch (Exception e) {
                try {
                    float left = ResolveFloat(boolNode.getLeft());
                    float right = ResolveFloat(boolNode.getRight());
                    if (boolNode.getOperator() == null) {
                        return false;
                    } else if (boolNode.getOperator() == Type.LESS) {
                        return left < right;
                    } else if (boolNode.getOperator() == Type.GREATER_EQUAL) {
                        return left >= right;
                    } else if (boolNode.getOperator() == Type.LESS_EQUAL) {
                        return left <= right;
                    } else if (boolNode.getOperator() == Type.EQUAL) {
                        return left == right;
                    } else if (boolNode.getOperator() == Type.NOT_EQUAL) {
                        return left != right;
                    } else if (boolNode.getOperator() == Type.GREATER) {
                        return left > right;
                    }
                } catch (Exception f) {
                    try {
                        char left = ResolveChar(boolNode.getLeft());
                        char right = ResolveChar(boolNode.getRight());
                        if (boolNode.getOperator() == Type.EQUAL) {
                            return left == right;
                        } else if (boolNode.getOperator() == Type.NOT_EQUAL) {
                            return left != right;
                        }
                    } catch (Exception g) {
                        try {
                            String left = ResolveString(boolNode.getLeft());
                            String right = ResolveString(boolNode.getRight());
                            if (boolNode.getOperator() == Type.EQUAL) {
                                return left.equals(right);
                            } else if (boolNode.getOperator() == Type.NOT_EQUAL) {
                                return !left.equals(right);
                            }
                        } catch (Exception h) {
                            throw new RuntimeException("Error: Cannot resolve boolean expression " + boolNode);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error: Cannot resolve boolean expression " + boolNode);
        }
        return false;
    }

    public static int ResolveInt(Node nodey) {
        if (nodey instanceof FloatNode) {
            return Integer.parseInt(nodey.toString());
        } else if (nodey instanceof MathOpNode) {
            return switch (((MathOpNode) nodey).getOperator()) {
                case "+" -> ResolveInt(((MathOpNode) nodey).getLeft()) + ResolveInt(((MathOpNode) nodey).getRight());
                case "-" -> ResolveInt(((MathOpNode) nodey).getLeft()) - ResolveInt(((MathOpNode) nodey).getRight());
                case "*" -> ResolveInt(((MathOpNode) nodey).getLeft()) * ResolveInt(((MathOpNode) nodey).getRight());
                case "/" -> ResolveInt(((MathOpNode) nodey).getLeft()) / ResolveInt(((MathOpNode) nodey).getRight());
                case "%" -> ResolveInt(((MathOpNode) nodey).getLeft()) % ResolveInt(((MathOpNode) nodey).getRight());
                default -> throw new RuntimeException("Error: Invalid operator in math expression");
            };

        } else {
            throw new RuntimeException("Not an int or float");
        }
    }

    public static float ResolveFloat(Node nodey) {
        if (nodey instanceof FloatNode) {
            return Float.parseFloat(nodey.toString());
        } else {
            throw new RuntimeException("Not an int or float");
        }
    }

    public static char ResolveChar(Node nodey) {
        if (nodey instanceof CharNode) {
            return nodey.toString().toCharArray()[0];
        } else {
            throw new RuntimeException("Not a char");
        }
    }

    public static String ResolveString(Node nodey) {
        if (nodey instanceof StringNode) {
            return nodey.toString();
        } else {
            throw new RuntimeException("Not a string");
        }
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
