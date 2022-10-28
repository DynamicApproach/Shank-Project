import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FuctionNode extends Node {
    public String name;
    public List<VariableNode> parameters;
    public List<VariableNode> variables;
    public List<Node> body;
    public List<VariableNode> locals;
    // const
    public List<VariableNode> constant;
    // vars
    public VariableNode vars;

    public FuctionNode(String name) {
        this.name = name;
    }

    public void setBody(List<Node> body) {
        this.body = body;
    }

    public void setConstant(List<VariableNode> constant) {
        this.constant = constant;
    }

    public void setVariables(List<VariableNode> variables) {
        this.variables = variables;
    }


    public void setLocals(List<VariableNode> constant, List<VariableNode> variables) {

        this.variables = variables;
        this.constant = constant;
        locals = union(constant, variables);
    }

    @Override
    public String toString() {
        return "FunctionAST{" + "name='" + name + '\'' + ", parameters=" + parameters + ", variables=" + variables + ", body=" + body + ", locals=" + locals + '}';
    }

    public void setParameters(List<VariableNode> parameters) {
        this.parameters = parameters;
    }

    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<>();
        set.addAll(list1);
        set.addAll(list2);
        return new ArrayList<>(set);
    }
}
