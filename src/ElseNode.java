import java.util.ArrayList;

public class ElseNode extends IfNode {

    public ElseNode(Node condition, ArrayList<StatementNode> statements) {
        super(condition, statements);
    }
}
