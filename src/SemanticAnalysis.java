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
                if (funcDef == null) {
                    throw new Exception("Function " + funcCall.getName() + " is not defined");
                }
                // String name = funcCall.getName();
                // check if the number of parameters matches
                if(!"write".equalsIgnoreCase(funcCall.getName()) && !"read".equalsIgnoreCase(funcCall.getName()))
                {
                    CallableNode calledNode = functions.get(funcCall.getName());
                    String name = calledNode.getName();
                    System.out.println(name);
                    int size1 = ((FunctionNode) functions.get(funcCall.getName())).getParameters().size();
                    int size2 = funcCall.getParameters().size();
                    System.out.println(size1);
                    System.out.println(size2);
                    if (!(((FunctionNode) functions.get(funcCall.getName())).getParameters().size() == funcCall.getParameters().size()))
                    {
                        throw new Exception("Unmatched Parameters.");
                    }
                }
            }
        }
    }
}