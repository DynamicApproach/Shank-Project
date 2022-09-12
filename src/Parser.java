import java.util.ArrayList;

@SuppressWarnings("unused")
public class Parser {
    private ArrayList<Token> tokens;

    @SuppressWarnings("unused")
    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    // match and remove searchs for a token in the arraylist and removes it if it is found?

    // best way remove token from list? iterate? use remove? most efficient?

    public Token matchAndRemove(Token token) {
        Token temp = tokens.get(0);
        if (temp.getType() == token.getType()) {
            tokens.remove(0);
            return temp;
        } else {
            return null;
        }

    }

    @SuppressWarnings("unused")
    public Token itermatchAndRemove(Type search) {
        Token token = null;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getType() == search) {
                token = tokens.get(i);
                tokens.remove(i);
                break;
            }
        }
        return token;
    }

    // expression, term, factor methods
    public Node expression() {
        Node node = term();
        // TODO: Decide if while loop is right here?
        while (true) {
            Token token = tokens.get(0);
            if (token.getType() == Type.ADD) {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), Type.ADD);
            } else if (token.getType() == Type.MINUS) {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), Type.MINUS);
            } else {
                break;
            }
        }

        return term();
    }

    public Node term() {
        Node node = factor();
        Token token = tokens.get(0);
        if (token.getType() == Type.MULTIPLY) {
            matchAndRemove(token);
            node = new MathOpNode(node, term(), Type.MULTIPLY);
        } else if (token.getType() == Type.DIVIDE) {
            matchAndRemove(token);
            node = new MathOpNode(node, term(), Type.DIVIDE);
        }
        return node;
    }

    public Node factor() {
        // TODO: Attempt to turn into a int or float or return null
        Token numberToken = new Token(Type.NUMBER);
        if (matchAndRemove(numberToken) == null) return null; // no return means there is no match
        if (isInteger(tokens.get(0))) {
            IntegerNode intNode = new IntegerNode(Integer.parseInt(tokens.get(0).getValue()));
            tokens.remove(0);
            return intNode;
        }
        if (isFloat(tokens.get(0))) {
            FloatNode floatNode = new FloatNode(Float.parseFloat(tokens.get(0).getValue()));
            tokens.remove(0);
            return floatNode;
        }
        return null;
    }

    private boolean isFloat(Token token) {
        try {
            Float.parseFloat(token.getValue());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(Token token) {
        try {
            Integer.parseInt(token.getValue());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @SuppressWarnings("unused")

    public Node parse() {
        Node node = expression();       // matchAndRemove(Symbol.NEWLINE);
        if (node == null) {
            System.out.println("Node cannot be made.");
            return null;
        } else {
            return node;
        }
    }
}
