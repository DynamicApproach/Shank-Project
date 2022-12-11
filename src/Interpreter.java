import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


@SuppressWarnings("unused")
public class Interpreter {
    static HashMap<String, CallableNode> hashmapfuncts;
    static HashMap<String, InterpreterDataType> VariableHashMap = new HashMap<>();

    public Interpreter(HashMap<String, CallableNode> hashmapfuncts) {
        Interpreter.hashmapfuncts = hashmapfuncts;
    }

    // ======================================INTERPRET FUNCTION OR BODY ==================================================
    // gets handed a function node and a list of params
    // makes space for the local variables
    // match the parameters to names
    // e.g. Take a,b and 1,2 -> both are now local variables
    // once we have that done, the env is set up
    // now we can call interp block -> Which will be reused
    public static void InterpretFunction(FunctionCallNode function, ArrayList<InterpreterDataType> parameters) throws Exception {
        // function was just called and have to make the hash map for local variables and parameters.
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
                        System.out.println("Error: Variable type not found      Name:" + var.getName() + "    Type: " + var.getType() + "     Value: " + var.getValue());
                        throw new Exception("InterpretFunction: Variable type not found");
                    }
                }
            }
        }
        FunctionNode functionNode = (FunctionNode) hashmapfuncts.get(function.getName().toLowerCase());
        SemanticAnalysis analyzer = new SemanticAnalysis(hashmapfuncts);
        analyzer.analyze(functionNode.getBody());
        Interpreter.InterpretBlock(functionNode.getBody(), VariableHashMap);
    }


    // InterpretBlock should take the collection of statements and a hashmap of variables.
    // We will loop over the collection of statements.
    // In interpret block you are preparing the list of IDT’s. That will go to interpret function.
    // Locate the function definition; this could be a built-in (like read or write) or it could be user-defined.
    // Make sure that the number of parameters matches OR that the function definition is variadic and built-in.
    // Make a collection of values (InterpreterDataType):
    // if in hashmap of functions, get the built-in function
    // if not, get the user defined function
    private static InterpreterDataType InterpretBlock(ArrayList<StatementNode> statements, HashMap<String, InterpreterDataType> VariableHashMap) {
        // Passes a collection of statements and a hashmap of variables
        // walk the list of statements and do each one
        // check what type of statement it is
        try {
            for (StatementNode statement : statements) { // statement is instance of any node
                if (statement instanceof FunctionCallNode functionCall) { // else function call execute
                    String name = functionCall.getName();
                    if (hashmapfuncts.containsKey(name.toLowerCase())) { // is it built in? Is in hashmap?
                        FunctionCallNode current;
                        //builtin
                        // a, b, 2
                        //just pushing all params
                        // check param size of FunctionCall and FunctionNode
                        try {
                            if(hashmapfuncts.get(name).getArguments() != null && functionCall.getParameters() != null){
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
                                    if (hashmapfuncts.containsKey(name.toLowerCase())) {
                                        executeFunction(hashmapfuncts.get(name.toLowerCase()), parameters);
                                    } else {
                                        InterpretFunction(functionCall, parameters);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                throw new RuntimeException("\nError: Incorrect number of parameters for function\n " + functionCall.getName()  + " Expected: " + hashmapfuncts.get(name).getArguments().size() + " \nargs expected: " + hashmapfuncts.get(name).getArguments()  + " \nActual: " + functionCall.getParameters().size() + "\n Actual Params: " + functionCall.getParameters());
                            }}
                        } catch (RuntimeException e) {
                            throw new RuntimeException(e + "\n\nError: Incorrect number of parameters for function " + functionCall.getName());
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
                            if (startValue > endValue) {
                                for (int i = startValue; i >= endValue; i--) {
                                    VariableHashMap.put(variable.toString(), new IntDataType(i));
                                    InterpretBlock(body, VariableHashMap);
                                }
                            } else {
                                for (int i = startValue; i < endValue; i++) {
                                    VariableHashMap.put(variable.toString(), new IntDataType(i));
                                    InterpretBlock(body, VariableHashMap);
                                }
                            }
                        } else {
                            throw new RuntimeException("Error: Cannot iterate over " + value);
                        }
                    }
                } else if (statement instanceof WhileNode) {
                    // same as for loop
                    BooleanExpressionNode express = ((WhileNode) statement).getBooleanExpression();
                    ArrayList<StatementNode> block = ((WhileNode) statement).getBlock();
                    if ("true".equals(Objects.requireNonNull(resolve(express)).toString())) {
                        return InterpretBlock(block, VariableHashMap);
                    }

                } else if (statement instanceof RepeatNode) {
                    // we have a repeat node with an expression and a block
                    // we need to evaluate the expression
                    // if true, we need to interpret the block
                    // if false, we need to break out of the loop
                    BooleanExpressionNode express = (BooleanExpressionNode) ((RepeatNode) statement).getBooleanExpression();
                    ArrayList<StatementNode> block = ((RepeatNode) statement).getBlock();

                } else if (statement instanceof IfNode) {
                    // we have an ifnode with an expression and a block
                    // we need to evaluate the expression
                    // if true, we need to interpret the block
                    // if false, we need to break out of the loop
                    Node express = ((IfNode) statement).getBooleanExpression();
                    ArrayList<StatementNode> block = ((IfNode) statement).getStatementNodes();

                } else if (statement instanceof AssignmentNode curStatement) {
                    // cast to assignment node
                    if (VariableHashMap.containsKey((curStatement.getTarget().toString()))) {
                        // if it's not in the hashmap throw an error
                        System.out.println("Error: Cannot assign to constant " + statement);
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
        } catch (RuntimeException e) {

            throw new RuntimeException(e + "Error: Error interpreting -- FunctionDef " );
        }
        return null;
    }

    public static void executeFunction(CallableNode functionName, ArrayList<InterpreterDataType> parameters) throws Exception {
        BuiltInFunctionNode exe = (BuiltInFunctionNode) functionName;
        exe.execute(parameters);
    }

    // ====================================== MULTI RESOLVE ==================================================

    // (a> b) == (1<3)
    // resolve boolean
    // used same solver as mathopnode
    // if it is a bool type, return true or false


    // resolve
    // resolve checks the type, if its a math op node, it calls itself on the left and right
    // if it's a variable ref node, it checks the hashmap
    // if it's a float node, it returns a floatnode
    // if it's an int node, it returns an intnode
    // if it's a char node, it returns a charnode
    // if it's a string node, it returns a stringnode
    // if it's a boolean node, it returns a boolean LEFT and RIGHT

    public static Node resolve(Node nodey) {
        if (nodey instanceof IntegerNode) {
            return nodey;
        } else if (nodey instanceof FloatNode) {
            return nodey;
        } else if (nodey instanceof CharNode) {
            return nodey;
        } else if (nodey instanceof StringNode) {
            return nodey;
        } else if (nodey instanceof VariableReferenceNode) {
            if (VariableHashMap.containsKey(nodey.toString())) {
                // use the name to get the value from the hashmap
                // return the current value of that var ref node
                // get the value of the interpreter data type
                if (VariableHashMap.get(nodey.toString()) instanceof IntDataType) {
                    return new IntegerNode((Integer.parseInt(VariableHashMap.get(nodey.toString()).toString())));
                } else if (VariableHashMap.get(nodey.toString()) instanceof FloatDataType) {
                    return new FloatNode((Float.parseFloat(VariableHashMap.get(nodey.toString()).toString())));
                } else if (VariableHashMap.get(nodey.toString()) instanceof CharDataType) {
                    return new CharNode((VariableHashMap.get(nodey.toString()).toString().charAt(0)));
                } else if (VariableHashMap.get(nodey.toString()) instanceof StringDataType) {
                    return new StringNode((VariableHashMap.get(nodey.toString()).toString()));
                }
                InterpreterDataType vary = VariableHashMap.get(nodey.toString());
                System.out.println(VariableHashMap);
                System.out.println("VariableHashMap.get(nodey.toString()) = " + VariableHashMap.get(nodey.toString()));
            } else {
                System.out.println(VariableHashMap);
                System.out.println("VariableHashMap.get(nodey.toString()) = " + VariableHashMap.get(nodey.toString()));
                throw new RuntimeException("Error: Variable " + nodey + " is not defined");
            }
        } else if (nodey instanceof MathOpNode node) {
            Node left = node.getLeft();
            Node right = node.getRight();
            left = resolve(left);
            right = resolve(right);
            var op = node.getOp();
            switch (op) { // operator has to be the same type
                case ADD -> {
                    if (left instanceof IntegerNode && right instanceof IntegerNode) {
                        return new IntegerNode(((IntegerNode) left).getValue() + ((IntegerNode) right).getValue());
                    } else if (left instanceof FloatNode && right instanceof FloatNode) {
                        return new FloatNode(((FloatNode) left).getValue() + ((FloatNode) right).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot add " + left + " and " + right);
                    }
                }
                case MINUS -> {
                    if (left instanceof IntegerNode && right instanceof IntegerNode) {
                        return new IntegerNode(((IntegerNode) left).getValue() - ((IntegerNode) right).getValue());
                    } else if (left instanceof FloatNode && right instanceof FloatNode) {
                        return new FloatNode(((FloatNode) left).getValue() - ((FloatNode) right).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot subtract " + left + " and " + right);
                    }
                }
                case MULTIPLY -> {
                    if (left instanceof IntegerNode && right instanceof IntegerNode) {
                        return new IntegerNode(((IntegerNode) left).getValue() * ((IntegerNode) right).getValue());
                    } else if (left instanceof FloatNode && right instanceof FloatNode) {
                        return new FloatNode(((FloatNode) left).getValue() * ((FloatNode) right).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot multiply " + left + " and " + right);
                    }
                }
                case DIVIDE -> {
                    if (left instanceof IntegerNode && right instanceof IntegerNode) {
                        return new IntegerNode(((IntegerNode) left).getValue() / ((IntegerNode) right).getValue());
                    } else if (left instanceof FloatNode && right instanceof FloatNode) {
                        return new FloatNode(((FloatNode) left).getValue() / ((FloatNode) right).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot divide " + left + " and " + right);
                    }
                }
                case MOD -> {
                    if (left instanceof IntegerNode && right instanceof IntegerNode) {
                        return new IntegerNode(((IntegerNode) left).getValue() % ((IntegerNode) right).getValue());
                    } else if (left instanceof FloatNode && right instanceof FloatNode) {
                        return new FloatNode(((FloatNode) left).getValue() % ((FloatNode) right).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot mod " + left + " and " + right);
                    }
                }
            }
        } else if (nodey instanceof BooleanExpressionNode node) {
            Node nodeBoolL = (((BooleanExpressionNode) nodey).getLeft());
            Node nodeBoolR = (((BooleanExpressionNode) nodey).getRight());
            nodeBoolL = resolve(nodeBoolL);
            nodeBoolR = resolve(nodeBoolR);
            var op = node.getOperator();
            switch (op) {
                case GREATER -> {
                    if (nodeBoolL instanceof IntegerNode && nodeBoolR instanceof IntegerNode) {
                        return new BooleanNode(((IntegerNode) nodeBoolL).getValue() > ((IntegerNode) nodeBoolR).getValue());
                    } else if (nodeBoolL instanceof FloatNode && nodeBoolR instanceof FloatNode) {
                        return new BooleanNode(((FloatNode) nodeBoolL).getValue() > ((FloatNode) nodeBoolR).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot compare " + nodeBoolL + " and " + nodeBoolR);
                    }
                }
                case LESS -> {
                    if (nodeBoolL instanceof IntegerNode && nodeBoolR instanceof IntegerNode) {
                        return new BooleanNode(((IntegerNode) nodeBoolL).getValue() < ((IntegerNode) nodeBoolR).getValue());
                    } else if (nodeBoolL instanceof FloatNode && nodeBoolR instanceof FloatNode) {
                        return new BooleanNode(((FloatNode) nodeBoolL).getValue() < ((FloatNode) nodeBoolR).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot compare " + nodeBoolL + " and " + nodeBoolR);
                    }
                }
                case GREATER_EQUAL -> {
                    if (nodeBoolL instanceof IntegerNode && nodeBoolR instanceof IntegerNode) {
                        return new BooleanNode(((IntegerNode) nodeBoolL).getValue() >= ((IntegerNode) nodeBoolR).getValue());
                    } else if (nodeBoolL instanceof FloatNode && nodeBoolR instanceof FloatNode) {
                        return new BooleanNode(((FloatNode) nodeBoolL).getValue() >= ((FloatNode) nodeBoolR).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot compare " + nodeBoolL + " and " + nodeBoolR);
                    }
                }
                case LESS_EQUAL -> {
                    if (nodeBoolL instanceof IntegerNode && nodeBoolR instanceof IntegerNode) {
                        return new BooleanNode(((IntegerNode) nodeBoolL).getValue() <= ((IntegerNode) nodeBoolR).getValue());
                    } else if (nodeBoolL instanceof FloatNode && nodeBoolR instanceof FloatNode) {
                        return new BooleanNode(((FloatNode) nodeBoolL).getValue() <= ((FloatNode) nodeBoolR).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot compare " + nodeBoolL + " and " + nodeBoolR);
                    }
                }
                case EQUAL -> {
                    if (nodeBoolL instanceof IntegerNode && nodeBoolR instanceof IntegerNode) {
                        return new BooleanNode(((IntegerNode) nodeBoolL).getValue() == ((IntegerNode) nodeBoolR).getValue());
                    } else if (nodeBoolL instanceof FloatNode && nodeBoolR instanceof FloatNode) {
                        return new BooleanNode(((FloatNode) nodeBoolL).getValue() == ((FloatNode) nodeBoolR).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot compare " + nodeBoolL + " and " + nodeBoolR);
                    }
                }
                case NOT_EQUAL -> {
                    if (nodeBoolL instanceof IntegerNode && nodeBoolR instanceof IntegerNode) {
                        return new BooleanNode(((IntegerNode) nodeBoolL).getValue() != ((IntegerNode) nodeBoolR).getValue());
                    } else if (nodeBoolL instanceof FloatNode && nodeBoolR instanceof FloatNode) {
                        return new BooleanNode(((FloatNode) nodeBoolL).getValue() != ((FloatNode) nodeBoolR).getValue());
                    } else {
                        throw new RuntimeException("Error: Cannot compare " + nodeBoolL + " and " + nodeBoolR);
                    }
                }
            }

        }
        return null;
    }


    // ======================================SINGLE RESOLVE==================================================

    public static int ResolveInt(Node nodey) {
        if (nodey instanceof IntegerNode) {
            return Integer.parseInt(nodey.toString());
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

    // ====================================== PRINT ==================================================

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
