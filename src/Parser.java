import java.util.ArrayList;


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
        if (node == null) {
            return null;
        }
        while (a < tokens.size()) {
            Type token = peek(0).getType();
            if (token == Type.ADD) {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), Type.ADD);
            } else if (token == Type.MINUS) {
                matchAndRemove(token);
                node = new MathOpNode(node, term(), Type.MINUS);
            }
        }
        return node;
    }

    // Term is a factor followed by zero or more * or / operators followed by another factor.
    public Node term() {
        Node node = factor();
        if (node == null) {
            return null;
        }
        Type token = peek(0).getType();
        if (token == Type.MULTIPLY) {
            matchAndRemove(token);
            node = new MathOpNode(node, factor(), Type.MULTIPLY);
        } else if (token == Type.DIVIDE) {
            matchAndRemove(token);
            node = new MathOpNode(node, factor(), Type.DIVIDE);
        } else if (token == Type.MOD) {
            matchAndRemove(token);
            node = new MathOpNode(node, factor(), Type.MOD);
        } else if (token == Type.LESS) {
            node = new MathOpNode(node, factor(), Type.LESS);
        } else if (token == Type.GREATER) {
            node = new MathOpNode(node, factor(), Type.GREATER);
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
        // if identifier create variablerefnode
        Token temp = matchAndRemove(Type.IDENTIFIER);
        if (temp != null) {
            return new VariableReferenceNode(temp.toString());
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
            System.err.println("Node cannot be made.");
            return null;
        } else {
            return node;
        }
    }

    public ArrayList<StatementNode> Statements() {
        ArrayList<StatementNode> statements = new ArrayList<>();
        matchAndRemove(Type.BEGIN);
        matchAndRemove(Type.ENDLINE);
        while (peek(0).getType() != Type.END) {
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
        if (peek(1).getType() == Type.ASSIGN) {
            String name = matchAndRemove(Type.IDENTIFIER).getValue().trim();
            matchAndRemove(Type.ASSIGN);
            FunctionNode value = (FunctionNode) expression();
            matchAndRemove(Type.ENDLINE);
            return new AssignmentNode(new VariableReferenceNode(name), value);
        }
        return null;
    }

    public Token peek(int x) {
        return tokens.get(x);
    }

    // booleanExpression, booleanTerm, booleanFactor methods
    // booleanExpression is a list of booleanTerms separated by an operator
    public BooleanExpressionNode booleanExpression() {
        // check for expression operator expression and make a new booleanExpressionNode
        BooleanExpressionNode node = null;
        try {
            Type token = peek(0).getType();
            matchAndRemove(token);
            node = new BooleanExpressionNode(expression(), token, expression());
        } catch (Exception e) {
            System.out.println("Not an expression");
            throw new RuntimeException(e);
        } // TODO: CHECK WITH PROF
        return node;
    }

    //  Look for keywords to check if a while is possible
    // If not, make sure that we haven’t taken any tokens and return null.
    public WhileNode whileExpression() {
        // while booleanExpression do statements end
        if (peek(0).getType() == Type.WHILE) {
            matchAndRemove(Type.WHILE);
            BooleanExpressionNode condition = booleanExpression();
            matchAndRemove(Type.DO);
            ArrayList<StatementNode> statements = Statements();
            return new WhileNode(condition, statements);
        }
        return null;
    }

    // need to chain ifNode together -> ifNode(cond, statements, ifNode)
    public IfNode ifExpression() {
        // if booleanExpression then statements (if booleanExpression then statements)* end
        if (peek(0).getType() == Type.IF) {
            matchAndRemove(Type.IF);
            BooleanExpressionNode condition = booleanExpression();
            matchAndRemove(Type.THEN);
            ArrayList<StatementNode> statements = Statements();
            if ((matchAndRemove(Type.ELSE) != null) && (matchAndRemove(Type.IF) != null)) {
                return new IfNode(condition, statements, ifExpression());
            }
            return new IfNode(condition, statements); // TODO: WTF is this? Double check this.
            // Resulted when ifNode needed to be StatementNode
        }
        return null;
    }


    // for
    public ForNode forExpression() {
        // for identifier assignment expression to expression do statements end
        if (peek(0).getType() == Type.FOR) {
            matchAndRemove(Type.FOR);
            String name = matchAndRemove(Type.IDENTIFIER).getValue().trim();
            matchAndRemove(Type.ASSIGN);
            Node start = expression();
            matchAndRemove(Type.TO);
            Node end = expression();
            matchAndRemove(Type.DO);
            ArrayList<StatementNode> statements = Statements();
            return new ForNode(new VariableReferenceNode(name), start, end, statements);
        }
        return null;
    }


    // print

    public FunctionNode functionDefinition() {
        // TODO: double check changes
        /*
            It looks for “define”.
            If it finds that token, it starts building a functionAST node .
            It populates the name from the identifier, then looks for the left parenthesis.
            It then looks for variable declarations (see below).
            We then call the Constants, then Variables, then Body function from below.
        */

        try {
            if (matchAndRemove(Type.DEFINE) != null) {
                //The function declaration is the word “define”, then a name, left parenthesis,
                // then a list of variable declarations, separated by semi-colons and finally a
                // right parenthesis. The constants section has one (or more) name/value pairs.
                Token name = matchAndRemove(Type.IDENTIFIER);
                // name, constants, variables, body
                if (name.toString() != null) {
                    String nameStr = name.toString();
                }
                matchAndRemove(Type.LPAREN);
                ArrayList<VariableNode> vars = variables();
                matchAndRemove(Type.RPAREN);
                matchAndRemove(Type.ENDLINE);
                ArrayList<VariableNode> consta = constants();
                matchAndRemove(Type.ENDLINE);
                ArrayList<StatementNode> body = body();
                return new FunctionNode(name.toString(), vars, body);
            }
        } catch (Exception e) {
            System.err.println("Error in FunctionDefinition");
            throw new RuntimeException(e);
        }
        System.err.println("Error in FunctionDefinition - returned null?");
        return null;
    }


    private ArrayList<VariableNode> constants() {
        //looks for the constants token.
        // If it finds it, it calls a “processConstants” function that looks for token
        try {
            if (matchAndRemove(Type.CONSTANT) != null) {
                processConstants();
            }
        } catch (Exception e) {
            System.err.println("Error in Constants - Token expected but not found.");
            throw new RuntimeException(e);
        }
        return null;
    }

    private ArrayList<VariableNode> processConstants() {
       /*
        processConstants function that looks for tokens in the format:
        Identifier equals number endOfLine
        It should make a VariableNode for each of these – this
        should be a loop until it doesn’t find an identifier anymore.
        */
        int state = 0;
        Token x;
        ArrayList<VariableNode> constants = new ArrayList<>();
        try { // TODO: FIX constants
            // IDEN COMMA IDEN COMMA COLON TYPE ENDLINE
            // A,B,C:INT
            //STATES:
            // 0 - IDEN
            // 1 - COMMA
            // 2 - COLON
            // 3 - INT / REAL / STRING
            // 4 - ENDLINE
            ArrayList<Token> tokens = new ArrayList<>();
            switch (state) {
                case 0 -> {
                    x = matchAndRemove(Type.IDENTIFIER);
                    if (x != null) {
                        // add to list
                        tokens.add(x);
                        state = 1;
                    } else {
                        state = 5;
                    }
                }
                case 1 -> {
                    x = matchAndRemove(Type.COMMA);
                    if (x != null) {
                        state = 0;
                    } else {
                        state = 2;
                    }
                }
                case 2 -> {
                    x = matchAndRemove(Type.COLON);
                    if (x != null) {
                        state = 3;
                    }
                }
                case 3 -> {
                    x = matchAndRemove(Type.INTEGER);
                    if (x != null) {
                        for (Token t : tokens) {
                            constants.add(new VariableNode(t.getValue(), null, Type.INTEGER, true));
                        }
                        tokens.clear();
                        state = 4;
                    }
                    x = matchAndRemove(Type.REAL);
                    if (x != null) {
                        for (Token t : tokens) {
                            constants.add(new VariableNode(t.getValue(), null, Type.REAL, true));
                        }
                        // CLEAR LIST
                        tokens.clear();
                        state = 4;
                    }
                }
                    /*x = matchAndRemove(Type.STRING);
                    if (x != null) {
                        state = 4;
                    }*/
                case 4 -> {
                    x = matchAndRemove(Type.ENDLINE);
                    if (x != null) {
                        state = 0;
                    }
                }
                case 5 -> System.err.println("Error in processConstants - Invalid state");
                default -> System.err.println("Error in processConstants - Invalid state");
            }
            return constants;
        } catch (NumberFormatException e) {
            System.err.println("Error in processConstants - Token expected but not found.");
            throw new RuntimeException(e);
        }
    }

    public ArrayList<StatementNode> body() {
        ArrayList<StatementNode> bod = new ArrayList<>();
        try {
            while (matchAndRemove(Type.BEGIN) != null) {
                Token end = matchAndRemove(Type.ENDLINE);
                matchAndRemove(Type.END);
                matchAndRemove(Type.ENDLINE);
            }
        } catch (Exception e) {
            System.err.println("Error in body - Token expected but not found.");
            throw new RuntimeException(e);
        }

        return bod;
    }

    public ArrayList<VariableNode> variables() {
        // TODO: fix and make sure it outputs a list of variable nodes
       /*
        We then make a Variables function that looks for the variables token.
        If it finds it, it then looks for variable declarations and makes VariableNodes for each one.
        A variable declaration is a list of identifiers (separated by commas) followed by a colon,
        then the data type (integer or real, for now) followed by endOfLine (for variables section)
        or a semi-colon (for function definitions). For each variable, we make a VariableNode like we did for constants.
        IDEN     COMMA  COLON    DATA-TYPE   EOL OR ;
        For each variable, we make a VariableNode like we did for constants.*/
        // token equal match and remove
        ArrayList<VariableNode> variables = new ArrayList<>();
// STATES:
        // State 0 - Var -> 1
        // State 1 - IDEN -> 2, 3
        // State 2 - COMMA -> 0
        // State 3 - COLON -> 4
        // State 4 - INT OR REAL -> 0,5
        // State 5 - SEMI-COLON -> 0
        // State 6 - RPAREN/EOL - end
        try {

            int state = 0;
            boolean isVar = false;
            ArrayList<Token> idenList = new ArrayList<>();
            while (state != 6) {
                switch (state) {
                    case 0 -> {
                        if (matchAndRemove(Type.VAR) != null) {
                            state = 1;
                            isVar = true;
                        } else {
                            state = 1;
                            isVar = false;
                        }
                    }
                    case 1 -> {
                        Token x = matchAndRemove(Type.IDENTIFIER);
                        idenList.add(x);
                        if (x != null) {
                            state = 2;
                        } else {
                            state = 6;
                        }
                    }
                    case 2 -> {
                        Token y = matchAndRemove(Type.COMMA);
                        if (y != null) {
                            state = 0;
                        } else {
                            state = 3;
                        }
                    }
                    case 3 -> {
                        Token z = matchAndRemove(Type.COLON);
                        if (z != null) {
                            state = 4;
                        } else {
                            state = 6;
                        }
                    }
                    case 4 -> {
                        Token a = matchAndRemove(Type.INTEGER);
                        if (a != null) {
                            for (Token token : idenList) {
                                VariableNode var = new VariableNode(token.getValue().trim(), null, Type.INTEGER, isVar);
                                variables.add(var);
                                isVar = false;
                            }
                            idenList.clear();
                            state = 5;
                        } else {
                            Token b = matchAndRemove(Type.REAL);
                            if (b != null) {
                                // create a VariableNode for each identifier in the list
                                for (Token token : idenList) {
                                    VariableNode var = new VariableNode(token.getValue().trim(), null, Type.REAL, isVar);
                                    variables.add(var);
                                    isVar = false;
                                }
                                idenList.clear();
                                state = 5;
                            } else {
                                state = 6;
                            }
                        }
                    }
                    case 5 -> {
                        Token c = matchAndRemove(Type.SEMICOLON);
                        if (c != null) {
                            state = 0;
                        } else {
                            Token d = matchAndRemove(Type.ENDLINE);
                            if (d != null) {
                                state = 0;
                            } else {
                                state = 6;
                            }
                        }
                    }
                    default -> {
                        state = 6;
                    }
                }
            }
            return variables;
        } catch (Exception e) {
            System.err.println("Error in Variables - Token expected but not found.");
            throw new RuntimeException(e);
        }
    }


}
