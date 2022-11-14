public abstract class StatementNode extends Node {
    private String statement;


    public String toString() {
        return (statement != null) ? "Statement:" + statement : "Statement";
    }
}
