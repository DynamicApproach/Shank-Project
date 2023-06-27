package shank.project;
// AssignmentNode should have a VariableReferenceNode (for the variable being assigned)
// and an ASTNode for the expression that is being assigned. The tradition in compilers
// is to use lhs and rhs (left-hand side and right-hand side) for these members.
// This is not a good tradition. I used target and expression.
public class AssignmentNode extends StatementNode {
    private VariableReferenceNode target;
    private Node expression;

    // target = expression
    public AssignmentNode(VariableReferenceNode statement, Node expression) {
        this.target = statement;
        this.expression = expression;
    }

    public VariableReferenceNode getTarget() {
        return target;
    }

    public Node getExpression() {
        return expression;
    }

    public String toString() {
        return (target != null ? target : "") + " = " + (expression != null ? expression : "") + "   ";
    }

}
