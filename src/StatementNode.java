import java.util.ArrayList;

public class StatementNode extends Node {
    private ArrayList<StatementNode> children;
    private String statement;

    public StatementNode(String statement, ArrayList<StatementNode> children) {
        this.statement = statement;
        this.children = children;
    }

    public StatementNode(VariableReferenceNode statement, Node expression) {
        super();
    }


    @Override
    public String toString() {
        return "Statement:" + statement + " Children: " + children;
    }
}
