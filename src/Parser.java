import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokens;


    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
    // match and remove searchs for a token in the arraylist and removes it if it is found?
    // how else to parse if not passing in list? - we are to constructor
    // best way remove token from list? iterate? use remove? most efficient?

    public Token matchAndRemove(Token token) {
        if (tokens.get(0).getType() == token.getType()) {
            tokens.remove(0);
            return token;
        } else {
            return null;
        }

    }

    // expression, term, factor methods
    // expression -> term { (+|-) term }
    // term -> factor { (*|/) factor }
    // factor -> number | ( expression )
    public Node expression() {
        // expression -> term { (+|-) term }

        return term();
    }

    public Node term() {
        // term -> factor { (*|/) factor }
        Node node = factor();

        return node;
    }

    public Node factor() {
        // TODO: Figure out Factor properly, what should it return?
        // TODO: Does this just check if there is paren? Or check for int?
        // factor -> number | ( expression )
        //        if (matchAndRemove(Symbol.LPAREN)){
        //            Node node = expression();
        //            matchAndRemove(Symbol.RPAREN);
        //            return node;
        //        }
        //        else if (matchAndRemove(Symbol.NUMBER)){
        //            return new NumberNode();
        //        }
        //        else{
        //            System.out.println("Error: Invalid character");
        //            return null;
        //        }
        Token numberToken = new Token(Type.NUMBER);
        if (matchAndRemove(numberToken) == null) return null;
        //                else {
        //                    if(isInteger(token.get(0).getString())) {// this for Integer
        //                        IntegerNode intNode= new IntegerNode(Integer.parseInt(token.get(0).getString()));
        //                        token.remove(0);
        //                        return intNode;
        //                    }
        //                    if(isFloat(token.get(0).getString())) {
        //                        FloatNode floatNode= new FloatNode(Float.parseFloat(token.get(0).getString()));
        //                        token.remove(0);
        //                        return floatNode;
        //                    }
        return null;
    }
    // What should this output? 1+2*3? Should it just be looking for a number or 'if', ect.? Include things btw paren?


    public Node parse() {
        // returns empty node???
        // what should the tostring be? Just node value?
        Node node = expression();       // matchAndRemove(Symbol.NEWLINE);
        if (node == null) {
            System.out.println("Node cannot be made.");
            return null;
        } else {
            return node;
        }
    }
}
