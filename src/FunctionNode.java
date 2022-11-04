import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FunctionNode extends Node {
    private String name;
    private ArrayList<VariableNode> parameters;
    private ArrayList<VariableNode> variables;
    private ArrayList<Node> body;
    // const
    private ArrayList<VariableNode> constant;
    // vars
    private VariableNode vars;

    public FunctionNode(String name) {
        this.name = name;
    }

    public ArrayList<Node> getBody() {
        return body;
    }

    public void setBody(ArrayList<Node> body) {
        this.body = body;
    }

    public void setConstant(ArrayList<VariableNode> constant) {
        this.constant = constant;
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

    // params size


    @Override
    public String toString() {
        return "FunctionAST{" + "name='" + name + '\'' + ", parameters=" + parameters + ", variables=" + variables + ", body=" + body + ",  + '}'";
    }

    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }
}
