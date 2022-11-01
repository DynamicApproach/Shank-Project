import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unused")
public class Interpreter {
    private static InterpreterDataType InterpretFunction(FunctionNode function, Collection<InterpreterDataType> parameters) {
        Map<String, InterpreterDataType> variables = new HashMap<>();
/*        // Add all of our parameters to the hashmap using the names that our function expects
        for (int i = 0; i < function.getParameters().size(); i++) {
            //variables.put(function.getParameters().get(i), parameters.toArray()[i]); -> TODO: Have to get the InterpreterDataType from the VariableNode
        }
        // Next add all of the local variables to the hashmap
        for (VariableNode localVariable : function.getLocals()) {
            // variables.put(localVariable.getName(), localVariable);
        }*/

        // return InterpretBlock(function.getStatements(), variables);
        return null;
    }

    //InterpretBlock should take the collection of statements and a hashmap of variables. We will loop over the collection of statements.
    //
    //For now, the only statement type that we will handle is function calls.
    //
    //If the statement is a function call, implement the process described in the background section, otherwise we will ignore the statement (for now).

    // private static InterpreterDataType InterpretBlock(ArrayList<Node> statements, Map<String, InterpreterDataType> variables) {
        /*for (Node statement : statements) {
            if (statement instanceof CallableNode callable) {*/
    // TODO: When a function is called:
    // Locate the function definition; this could be a built-in (like read or write) or it could be user-defined.
    // Make sure that the number of parameters matches OR that the function definition is variadic and built-in.
    // Make a collection of values (InterpreterDataType):
    //For every parameter in invocation:
    //  `Add the constant value or the current value of the variable in the invocation`
    //Now we call the function (either the interpreter or the “execute” of the built-in function), passing it our collection.
    //Finally, we loop over that set of values – the called function might have changed some!
    //a. For each value, if the called function is variadic or the called function is marked as VAR and the invocation is marked as VAR then
    //
    //b. Update the working variable value with the values “passed back” from the function.
    //}
    // }
    //   return null;
    //}

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
            float left = Resolve(mathOpNode.getLeft());
            float right = Resolve(mathOpNode.getRight());
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
        if (tre.toString() == null) {
        }
        System.out.println("End Tree---------------------");
    }
}
