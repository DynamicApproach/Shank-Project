import java.util.ArrayList;
import java.util.List;


@SuppressWarnings({"UnusedReturnValue", "unused"})
public class Parser {
    private ArrayList<Token> tokens;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    /**
     * @return the tokens
     */
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    /**
     * @param tokens the tokens to set
     */
    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    // match and remove searches for a token in the arraylist and removes it if it is found?
    // best way remove token from list? iterate? use remove? most efficient?
    public Token matchAndRemove(Type token) {
        Token temp = tokens.get(0);
        if (temp.getType() == token) {
            tokens.remove(0);
            return temp;
        } else {
            return null;
        }
    }

    // expression, term, factor methods
    // Expression is the highest level of the grammar
    // Expression is a list of terms separated by + or -
    public Node expression() {
        Node node = term();
        int a = 0;
        while (a < tokens.size()) {
            Type token = tokens.get(0).getType();
            if (token == Type.ADD) {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), Type.ADD);
            } else if (token == Type.MINUS) {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), Type.MINUS);
            } else {
                break;
            }
            a++;
        }
        return node;
    }

    // Term is a factor followed by zero or more * or / operators followed by another factor.
    public Node term() {
        Node node = factor();
        Type token = tokens.get(0).getType();
        if (token == Type.MULTIPLY) {
            matchAndRemove(token);
            node = new MathOpNode(node, factor(), Type.MULTIPLY);
        } else if (token == Type.DIVIDE) {
            matchAndRemove(token);
            node = new MathOpNode(node, factor(), Type.DIVIDE);
        }
        return node;
    }

    // Factor is a number or a parenthesized expression.
    public Node factor() {
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
        Node node = expression();
        matchAndRemove(Type.ENDLINE);
        if (node == null) {
            System.out.println("Node cannot be made.");
            return null;
        } else {
            return node;
        }
    }

    public List<StatementNode> Statements() {
        List<StatementNode> statements = new ArrayList<>();
        matchAndRemove(Type.BEGIN);
        matchAndRemove(Type.ENDLINE);
        while (tokens.get(0).getType() != Type.END) {
            statements.add(Statement());
        }
        matchAndRemove(Type.END);
        matchAndRemove(Type.ENDLINE);
        return statements;
    }

    public StatementNode Statement() {
        return Assignment();
    }

    public AssignmentNode Assignment() {
        // identifier assignment expression endofline
        if (peekTwice().getType() == Type.ASSIGN) {
            String name = matchAndRemove(Type.IDENTIFIER).getValue();
            matchAndRemove(Type.ASSIGN);
            ASTNode value = (ASTNode) expression();
            matchAndRemove(Type.ENDLINE);
            return new AssignmentNode(new VariableReferenceNode(name), value);
        }
        return null;
    }

    public Token peek() {
        return tokens.get(0);
    }

    public Token peekTwice() {
        return tokens.get(1);
    }

    public ASTNode FunctionDefinition() {
        /*
            It looks for “define”.
            If it finds that token, it starts building a functionAST node .
            It populates the name from the identifier, then looks for the left parenthesis.
            It then looks for variable declarations (see below).
            We then call the Constants, then Variables, then Body function from below.
        */

        try {
            if (matchAndRemove(Type.DEFINE) != null) {
                Token name = matchAndRemove(Type.IDENTIFIER);
                ASTNode f = new ASTNode(name.getValue());
                matchAndRemove(Type.LPAREN);
                f.setParameters(params()); // constants, variables, body
                matchAndRemove(Type.RPAREN);
                matchAndRemove(Type.ENDLINE);
                f.constant = constants();
                f.variables = variables();
                f.body = bodyFunction();
                return f;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<VariableNode> params() {

        return null;
    }

    private List<VariableNode> constants() {
        //looks for the constants token.
        // If it finds it, it calls a “processConstants” function that looks for token
        try {
            if (matchAndRemove(Type.CONSTANT) != null) {
                processConstants();

            }
        } catch (Exception e) {
            System.out.println("Error in Constants - Token expected but not found.");
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<VariableNode> processConstants() {
       /*
        processConstants function that looks for tokens in the format:
        Identifier equals number endOfLine
        It should make a VariableNode for each of these – this
        should be a loop until it doesn’t find an identifier anymore.
        */

        Token x;
        List<VariableNode> constants = new ArrayList<>();
        try {
            while ((x = matchAndRemove(Type.IDENTIFIER)) != null) {
                Token y = matchAndRemove(Type.EQUAL);
                Token z = matchAndRemove(Type.NUMBER);
                Token a = matchAndRemove(Type.ENDLINE);
                if ((y != null)) {
                    if (isInteger(tokens.get(0))) {
                        IntegerNode intNode = new IntegerNode(Integer.parseInt(tokens.get(0).getValue()));
                        tokens.remove(0);
                        if (a != null) {
                            // make a VariableNode
                            VariableNode var = new VariableNode(x.getValue(), intNode, Type.CONSTANT, true);
                            constants.add(var);
                        }
                    } else if (isFloat(tokens.get(0))) {
                        FloatNode floatNode = new FloatNode(Float.parseFloat(tokens.get(0).getValue()));
                        tokens.remove(0);
                        if (a != null) {
                            // make a VariableNode
                            VariableNode var = new VariableNode(x.getValue(), floatNode, Type.CONSTANT, true);
                            constants.add(var);
                        }
                    }
                }

            }
            return constants;
        } catch (NumberFormatException e) {
            System.out.println("Error in processConstants - Token expected but not found.");
            throw new RuntimeException(e);
        }


    }

    public List<Node> bodyFunction() {
        List<Node> body = new ArrayList<>();
        while (matchAndRemove(Type.BEGIN) != null) {
            Token end = matchAndRemove(Type.ENDLINE);
            matchAndRemove(Type.END);
            matchAndRemove(Type.ENDLINE);
        }

        return body;
    }

    public List<VariableNode> variables() {

       /*
        We then make a Variables function that looks for the variables token.
        If it finds it, it then looks for variable declarations and makes VariableNodes for each one.
        A variable declaration is a list of identifiers (separated by commas) followed by a colon,
        then the data type (integer or real, for now) followed by endOfLine (for variables section)
        or a semi-colon (for function definitions). For each variable, we make a VariableNode like we did for constants.
        IDEN     COMMA  COLON    DATA-TYPE   EOL OR ;
        For each variable, we make a VariableNode like we did for constants.*/
        // token equal match and remove
        List<VariableNode> variables = new ArrayList<>();

        // alternate method? :  all identifiers into a list and then parse the list and make a VariableNode for each one
        try {
            if (matchAndRemove(Type.VARIABLES) != null) {
                Token currenttoken = null;
                while ((currenttoken = matchAndRemove(Type.IDENTIFIER)) != null) { // a,b,c
                    Token comma = matchAndRemove(Type.COMMA);
                    Token colon = matchAndRemove(Type.COLON);
                    Token isint = matchAndRemove(Type.INTEGER);
                    Token isreal = matchAndRemove(Type.REAL);
                    Token endl = matchAndRemove(Type.ENDLINE);
                    if (comma != null || colon != null) {
                        if (isint != null || isreal != null) {
                            if (endl != null) {
                                // make a VariableNode
                                VariableNode var = new VariableNode(currenttoken.getValue(), null, Type.VARIABLES, false);
                                variables.add(var);
                            }
                        }
                    }
                }
            }
            return variables;
        } catch (Exception e) {
            System.out.println("Error in Variables - Token expected but not found.");
            throw new RuntimeException(e);
        }
    }


}
