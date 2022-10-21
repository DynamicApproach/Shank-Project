import java.util.ArrayList;

public class ElseNode extends IfNode {
    public ElseNode(Node booleanExpression, ArrayList<Node> statementNodes, IfNode ifNode) {
        super(booleanExpression, statementNodes, ifNode);
    }
}
