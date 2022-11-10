import java.util.ArrayList;

public class FunctionNode extends CallableNode {
    private String name;
    private ArrayList<ParameterNode> parameters;
    private ArrayList<VariableNode> variables;
    private ArrayList<StatementNode> body;
    private ArrayList<VariableNode> constant;

    public FunctionNode(String value, ArrayList<VariableNode> vars) {
        super(value, vars);
    }

    public FunctionNode(String value, ArrayList<VariableNode> vars, ArrayList<StatementNode> body) {
        super(value, vars);
        this.body = body;
    }

    public ArrayList<StatementNode> getBody() {// returns node with whole body of tokens
        return body;
    }

    public void setBody(ArrayList<StatementNode> body) {
        this.body = body;
    }


    public ArrayList<VariableNode> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<VariableNode> variables) {
        this.variables = variables;
    }

    public ArrayList<ParameterNode> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<ParameterNode> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "FunctionAST{" + "name='" + name + '\'' + ", parameters=" + parameters + ", variables=" + variables + ", body=" + body + ",  + '}'";
    }

    public ArrayList<VariableNode> getConstant() {
        return constant;
    }

    public void setConstant(ArrayList<VariableNode> constant) {
        this.constant = constant;
    }
}
