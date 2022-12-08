import java.util.ArrayList;
import java.util.HashMap;

public class SemanticAnalysis {
    private HashMap<String, CallableNode> functions;

    public SemanticAnalysis(HashMap<String, CallableNode> functions) {
        this.functions = functions;
    }

    public void analyze(ArrayList<StatementNode> statements) throws Exception {
        // traverse the AST and perform semantic checks
        for (StatementNode stmt : statements) {
            if (stmt instanceof FunctionCallNode funcCall) {
                CallableNode funcDef = functions.get(funcCall.getName());

                // check if the number of parameters matches
                if (funcDef.getArguments().size() != funcCall.getParameters().size()) {
                    throw new Exception("Incorrect number of parameters for function '" + funcCall.getName() + "'");
                }

                // check if the function is variadic
                if (!funcDef.isVaradic() && funcCall.getParameters().size() > funcDef.getArguments().size()) {
                    throw new Exception("Too many parameters for non-variadic function '" + funcCall.getName() + "'");
                }

                // check for undefined variables
                for (var param : funcCall.getParameters()) {
                    if (param != null) {
                        String varName = (param).getName();
                        if (!functions.containsKey(varName)) {
                            throw new Exception("Undefined variable '" + varName + "'");
                        }
                    }
                }
            }
        }
    }
}
