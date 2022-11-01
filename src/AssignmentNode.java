// AssignmentNode should have a VariableReferenceNode (for the variable being assigned)
// and an ASTNode for the expression that is being assigned. The tradition in compilers
// is to use “lhs” and “rhs” (left-hand side and right-hand side) for these members.
// This is not a good tradition. I used “target” and “expression”.
public class AssignmentNode extends StatementNode {

    private VariableReferenceNode target;
    private FunctionNode expression;

    public AssignmentNode(VariableReferenceNode target, FunctionNode expression) {
        super("Assignment");
        this.target = target;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return target.toString() + " = " + expression.toString();
    }
}
