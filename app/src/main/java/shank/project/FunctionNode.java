import java.util.ArrayList;

public class FunctionNode extends CallableNode {
    private String name;
    private ArrayList<VariableNode> parameters;
    private ArrayList<VariableNode> variables;
    private ArrayList<VariableNode> constant;
    private ArrayList<StatementNode> body;

    public FunctionNode(String name, ArrayList<VariableNode> vars) {
        super(name, vars);
        this.name = name;
        this.variables = vars;
    }

    public FunctionNode(String value, ArrayList<VariableNode> vars, ArrayList<StatementNode> body) {
        super(value, vars);
        this.name = value;
        this.body = body;
        this.variables = vars;
    }

    public FunctionNode(String value, ArrayList<VariableNode> params, ArrayList<VariableNode> vars, ArrayList<StatementNode> body) {
        super(value, vars);
        this.name = value;
        this.parameters = params;
        this.body = body;
        this.variables = vars;
    }

    public FunctionNode(String value, ArrayList<VariableNode> params, ArrayList<VariableNode> vars, ArrayList<VariableNode> constant, ArrayList<StatementNode> body) {
        super(value, vars);
        this.name = value;
        this.parameters = params;
        this.body = body;
        this.variables = vars;
        this.constant = constant;
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

    public ArrayList<VariableNode> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<VariableNode> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String out = "FunctionAST{" + "name='";
        String outname = name + "\n" + ", parameters=";
        String param = parameters + "\n";
        String vars = ", variables=" + variables + "\n" + "";
        String consts = ",  + constants: " + constant + "\n";
        String body = ", body=" + printBody() + "\n" + '}';
        String returnedStr = out;
        if (name != null) {
            returnedStr += outname;
        }
        if (parameters != null) {
            returnedStr += param;
        }
        if (variables != null) {
            returnedStr += vars;
        }
        if (constant != null) {
            returnedStr += consts;
        }
        if (printBody() != null) {
            returnedStr += body;
        }
        return returnedStr;

    }

    private String printBody() {
        ArrayList<String> toPrint = new ArrayList<>();
        if (body != null) {
            for (StatementNode statement : body) {
                toPrint.add(statement.toString());
            }
            return toPrint.toString();
        }
        return null;
    }

    public ArrayList<VariableNode> getConstant() {
        return constant;
    }

    public void setConstant(ArrayList<VariableNode> constant) {
        this.constant = constant;
    }

    public boolean getVariablesAvalible() {
        return variables != null;
    }
}
