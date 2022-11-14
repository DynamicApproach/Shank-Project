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
        while (peek(0).getType() == Type.ADD || peek(0).getType() == Type.MINUS) {
            Type token = peek(0).getType();
            if (token == Type.ADD) {
                matchAndRemove(token);
                node = new MathOpNode(node, factor(), Type.ADD);
            } else if (token == Type.MINUS) {
                matchAndRemove(token);
                node = new MathOpNode(node, factor(), Type.MINUS);
            }
            a++;
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
        return (temp != null) ? new VariableReferenceNode(temp.toString()) : null;
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

    public ArrayList<StatementNode> statements() {
        ArrayList<StatementNode> statements = new ArrayList<>();
        matchAndRemove(Type.BEGIN);
        matchAndRemove(Type.ENDLINE);
        while (peek(0).getType() != Type.END) {
            statements.add(statement());
        }
        matchAndRemove(Type.END);
        matchAndRemove(Type.ENDLINE);
        return statements;
    }

    public StatementNode statement() {
        return assignment();
    }

    public AssignmentNode assignment() {
        // identifier assignment expression endofline
        if (peek(1).getType() == Type.ASSIGN) {
            String name = matchAndRemove(Type.IDENTIFIER).getValue().trim();
            matchAndRemove(Type.ASSIGN);
            MathOpNode value = (MathOpNode) expression();
            matchAndRemove(Type.ENDLINE);
            return new AssignmentNode(new VariableReferenceNode(name), value);
        }
        return null;
    }

    public Token peek(int x) {
        return tokens.get(x);
    }

    public BooleanExpressionNode booleanExpression() {
        // check for expression operator expression and make a new booleanExpressionNode
        BooleanExpressionNode node;
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
            ArrayList<StatementNode> statements = statements();
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
            ArrayList<StatementNode> statements = statements();
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
            ArrayList<StatementNode> statements = statements();
            return new ForNode(new VariableReferenceNode(name), start, end, statements);
        }
        return null;
    }

    public FunctionNode functionDefinition() {
        /*
            It looks for “define”.
            If it finds that token, it starts building a functionAST node .
            It populates the name from the identifier, then looks for the left parenthesis.
            It then looks for variable declarations (see below).
            We then call the Constants, then Variables, then Body function from below.
            name, (, params, ), constants, variables, body
        */
        try {
            if (matchAndRemove(Type.DEFINE) != null) {
                Token name = matchAndRemove(Type.IDENTIFIER);
                if (name.toString() != null) {
                    String nameStr = name.toString();
                }
                matchAndRemove(Type.LPAREN);
                ArrayList<ParameterNode> params = parameters();
                matchAndRemove(Type.RPAREN);
                matchAndRemove(Type.ENDLINE);
                ArrayList<VariableNode> vars = null;
                if (matchAndRemove(Type.VARIABLES) != null) {
                    matchAndRemove(Type.ENDLINE);
                    vars = variables();
                }
                ArrayList<VariableNode> consta = null;
                matchAndRemove(Type.ENDLINE);
                if (matchAndRemove(Type.CONSTANT) != null) {
                    matchAndRemove(Type.ENDLINE);
                    consta = constants();
                }
                matchAndRemove(Type.ENDLINE);
                ArrayList<StatementNode> body = body();
                return new FunctionNode(name.toString(), params, vars, consta, body);
            }
        } catch (Exception e) {
            System.err.println("Error in FunctionDefinition");
            throw new RuntimeException(e);
        }
        System.err.println("Error in FunctionDefinition - returned null?");
        return null;
    }

    private ArrayList<ParameterNode> parameters() {
       /*
        We then make a Variables function that looks for the variables token.
        If it finds it, it then looks for variable declarations and makes VariableNodes for each one.
        A variable declaration is a list of identifiers (separated by commas) followed by a colon,
        then the data type (integer or real, for now) followed by endOfLine (for variables section)
        or a semi-colon (for function definitions). For each variable, we make a VariableNode like we did for constants.
        IDEN     COMMA/COLON    DATA-TYPE   EOL OR ; IDEN    COMMA/COLON    DATA-TYPE     )/EOL
        */// STATES:
        // State 0 - Var -> 1
        // State 1 - IDEN -> 2, 3
        // State 2 - COMMA -> 0
        // State 3 - COLON -> 4
        // State 4 - INT OR REAL -> 0,5
        // State 5 - SEMI-COLON -> 0
        // State 6 - RPAREN/EOL - end
        ArrayList<ParameterNode> paramets = new ArrayList<>();
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
                        Token ID = matchAndRemove(Type.IDENTIFIER);
                        if (ID != null) {
                            idenList.add(ID);
                            state = 2;
                        } else {
                            state = 6;
                        }
                    }
                    case 2 -> {
                        Token COMMA = matchAndRemove(Type.COMMA);
                        if (COMMA != null) {
                            state = 0;
                        } else {
                            state = 3;
                        }
                    }
                    case 3 -> {
                        Token COLON = matchAndRemove(Type.COLON);
                        if (COLON != null) {
                            state = 4;
                        } else {
                            state = 6;
                        }
                    }
                    case 4 -> {
                        Token isInt = matchAndRemove(Type.INTEGER);
                        if (isInt != null) {
                            for (Token token : idenList) {
                                ParameterNode var = new ParameterNode(token.getValue().trim(), Type.INTEGER, null, !(isVar));
                                paramets.add(var);
                            }
                            isVar = false;
                            idenList.clear();
                            state = 5;
                        } else {
                            Token b = matchAndRemove(Type.REAL);
                            if (b != null) {
                                // create a VariableNode for each identifier in the list
                                for (Token token : idenList) {
                                    ParameterNode var = new ParameterNode(token.getValue().trim(), Type.REAL, null, !(isVar));
                                    paramets.add(var);
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
                            state = 6;
                        }
                    }
                    default -> state = 6;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in Variables - Token expected but not found.");
            throw new RuntimeException(e);
        }
        return paramets;
    }


    private ArrayList<VariableNode> constants() {
        //looks for the constants token.
        // If it finds it, it calls a “processConstants” function that looks for token
        try {
            return processConstants();

        } catch (Exception e) {
            System.err.println("Error in Constants - Token expected but not found.");
            throw new RuntimeException(e);
        }
    }

    private ArrayList<VariableNode> processConstants() {
       /*
        processConstants function that looks for tokens in the format:
        Identifier equals number endOfLine
        It should make a VariableNode for each of these – this
        should be a loop until it doesn’t find an identifier anymore.
        */
        ArrayList<VariableNode> consties = new ArrayList<>();
        try {
            // STATES:
            // Identifier equals number endOfLine
            // 0 - IDEN
            // 1 - EQUALS
            // 2 - NUMBER
            // 3 - ENDLINE
            int state = 0;
            boolean isVar = false;
            Token name;

            Token number;
            ArrayList<Token> idenList = new ArrayList<>();
            while (state != 4) {
                switch (state) {
                    case 0 -> {
                        matchAndRemove(Type.ENDLINE);
                        name = matchAndRemove(Type.IDENTIFIER);
                        if (name != null) {
                            idenList.add(name);
                            state = 1;
                        } else {
                            state = 3;
                        }
                    }
                    case 1 -> {
                        if (matchAndRemove(Type.EQUAL) != null) {
                            state = 2;
                        } else {
                            state = 4;
                        }
                    }
                    case 2 -> {
                        // check if real or int
                        number = matchAndRemove(Type.NUMBER);
                        if (isInteger(number)) {
                            for (Token token : idenList) {
                                VariableNode var = new VariableNode(token.getValue().trim(), new IntegerNode(Integer.parseInt(number.getValue())), Type.INTEGER, false);
                                consties.add(var);
                            }
                            idenList.clear();
                            state = 3;
                        } else if (isFloat(number)) {
                            for (Token token : idenList) {
                                VariableNode var = new VariableNode(token.getValue().trim(), new RealNode(Float.parseFloat(number.getValue())), Type.REAL, false);
                                consties.add(var);
                            }
                            idenList.clear();
                            state = 0;
                        } else {
                            state = 4;
                        }

                    }
                    case 3 -> {
                        if (peek(0).getType() == Type.ENDLINE || peek(0).getType() == Type.IDENTIFIER) {
                            state = 0;
                        } else {
                            return consties;
                        }
                    }
                    default -> {
                        return consties;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error in Variables - Token expected but not found.");
            throw new RuntimeException(e);
        }
        return (consties.isEmpty() ? consties : null);
    }

    public ArrayList<StatementNode> body() {
        ArrayList<StatementNode> bod = new ArrayList<>();

        if (matchAndRemove(Type.BEGIN) != null) {
            // while not at the end token, keep adding statements
            while (matchAndRemove(Type.END) == null) {
                matchAndRemove(Type.ENDLINE);
                StatementNode statement = statement();
                if (statement != null) {
                    matchAndRemove(Type.ENDLINE);
                    bod.add(statement);
                }
            }
            return bod;
        }

        return null;
    }

    public ArrayList<VariableNode> variables() {
       /*
        We then make a Variables function that looks for the variables token.
        If it finds it, it then looks for variable declarations and makes VariableNodes for each one.
        A variable declaration is a list of identifiers (separated by commas) followed by a colon,
        then the data type (integer or real, for now) followed by endOfLine (for variables section)
        or a semi-colon (for function definitions). For each variable, we make a VariableNode like we did for constants.
        IDEN     COMMA  COLON    DATA-TYPE   EOL OR ;
        For each variable, we make a VariableNode like we did for constants.*/
        // IDEN COMMA IDEN COMMA COLON TYPE ENDLINE
        // A,B,C:INT
        //STATES:
        // 0 - IDEN
        // 1 - COMMA
        // 2 - COLON
        // 3 - INT / REAL / STRING
        // 4 - ENDLINE
        ArrayList<VariableNode> variables = new ArrayList<>();
        int state = 0;
        Token x;
        ArrayList<VariableNode> constants = new ArrayList<>();

        ArrayList<Token> tokens = new ArrayList<>();
        boolean isVar = false;
        int curState = 0;
        try {
            while (curState != 4) {
                switch (curState) {
                    case 0 -> {
                        isVar = (matchAndRemove(Type.VAR) != null);
                        x = matchAndRemove(Type.IDENTIFIER);
                        if (x != null) {
                            curState = 1;
                            tokens.add(x);
                        } else {
                            curState = 5;
                        }
                    }
                    case 1 -> {
                        x = matchAndRemove(Type.COMMA);
                        if (x != null) {
                            curState = 0;
                        } else {
                            curState = 2;
                        }
                    }
                    case 2 -> {
                        x = matchAndRemove(Type.COLON);
                        if (x != null) {
                            curState = 3;
                        }
                    }
                    case 3 -> {
                        x = matchAndRemove(Type.INTEGER);
                        if (x != null) {
                            for (Token t : tokens) {
                                constants.add(new VariableNode(t.getValue(), null, Type.INTEGER, isVar));
                            }
                            tokens.clear();
                            curState = 4;
                        }
                        x = matchAndRemove(Type.REAL);
                        if (x != null) {
                            for (Token t : tokens) {
                                constants.add(new VariableNode(t.getValue(), null, Type.REAL, isVar));
                            }
                            // CLEAR LIST
                            tokens.clear();
                            curState = 4;
                        }
                    }
                    case 4 -> {
                        x = matchAndRemove(Type.ENDLINE);
                        if (x != null) {
                            curState = 0;
                        }
                    }
                    case 5 -> System.err.println("Error in processConstants - Invalid state");
                    default -> curState = 5;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in processConstants");
            throw new RuntimeException(e);
        }
        return constants;
    }


}
