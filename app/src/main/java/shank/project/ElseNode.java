import java.util.ArrayList;

public class ElseNode extends IfNode {
    public ElseNode(BooleanExpressionNode condition, ArrayList<StatementNode> statements) {
        super(condition, statements);
    }
}
