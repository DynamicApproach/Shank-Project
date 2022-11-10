import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class FunctionNode extends CallableNode {
    private String name;
    private ArrayList<VariableNode> parameters;
    private ArrayList<VariableNode> variables;
    private ArrayList<StatementNode> body;
    // const
    private ArrayList<VariableNode> constant;
    // vars

    public FunctionNode(String value, ArrayList<VariableNode> vars) {
        super(value, vars);
    }

    public ArrayList<StatementNode> getBody() {// returns node with whole body of tokens
        return body;
    }

    public void setBody(ArrayList<StatementNode> body) {
        this.body = body;
    }

    // vars
    public ArrayList<VariableNode> getArgs() {
        return unionArray(parameters, variables);
    }

    public ArrayList<VariableNode> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<VariableNode> variables) {
        this.variables = variables;
    }

    public ArrayList<VariableNode> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<VariableNode> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "FunctionAST{" + "name='" + name + '\'' + ", parameters=" + parameters + ", variables=" + variables + ", body=" + body + ",  + '}'";
    }

    // params size

    public ArrayList<VariableNode> unionArray(ArrayList<VariableNode> a, ArrayList<VariableNode> b) {
        Set<VariableNode> set = new HashSet<VariableNode>();
        set.addAll(a);
        set.addAll(b);
        return new ArrayList<VariableNode>(set);
    }

    public ArrayList<VariableNode> getConstant() {
        return constant;
    }

    public void setConstant(ArrayList<VariableNode> constant) {
        this.constant = constant;
    }
}
