public class AssignmentNode extends StatementNode {
    // AssignmentNode should have a VariableReferenceNode (for the variable being assigned)
    // and an ASTNode for the expression that is being assigned. The tradition in compilers
    // is to use “lhs” and “rhs” (left-hand side and right-hand side) for these members.
    // This is not a good tradition. I used “target” and “expression”.

    private VariableReferenceNode target;
    private ASTNode expression;

    public AssignmentNode(VariableReferenceNode target, ASTNode expression) {
        super("Assignment");
        this.target = target;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return target.toString() + " = " + expression.toString();
    }
}
