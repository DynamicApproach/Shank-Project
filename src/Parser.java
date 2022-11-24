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

    public void removeEndlines() {
        while (tokens.get(0).getType() == Type.ENDLINE) {
            tokens.remove(0);
        }
    }


    public BooleanExpressionNode booleanExpression() {
        // check for expression operator expression and make a new booleanExpressionNode
        BooleanExpressionNode node = null;
        try {
            Type token = peek(1).getType();
            node = new BooleanExpressionNode(expression(), token, expression());
        } catch (Exception e) {
            System.out.println("Not an expression");
            throw new RuntimeException(e); // less, greater, ect lowest priority
        } // TODO: Ask about moving into expression method

        return node;
    }


    // expression, term, factor methods
    // Expression is the highest level of the grammar
    // Expression is a list of terms separated by + or -
    public Node expression() {
        // boolean-expression term factor
        // TODO: EXPRESSION MERGE
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
        // merge boolean into regular expression
        // if a = 5+3
        // a = b>3
        // < > = <= >= == <>
        // lower prio then + - * /
        // all the same prio
        // eval left to right with prio
        // dont have to chain like w +/-

        //BooleanExpressionNode node = null;
        //        try {
        //            Type token = peek(1).getType();
        //            node = new BooleanExpressionNode(expression(), token, expression());
        //        } catch (Exception e) {
        //            System.out.println("Not an expression");
        //            throw new RuntimeException(e); // less, greater, ect lowest priority
        //        }

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
        if (peek(0).getType() == Type.TRUE) {
            return new BooleanNode(true);
        } else if (peek(0).getType() == Type.FALSE) {
            return new BooleanNode(false);
        }
        if (peek(0).getType() == Type.CHAR) {
            return new CharNode(peek(0).getValue().charAt(0));
        }
        if (peek(0).getType() == Type.STRING) {
            return new StringNode(peek(0).getValue());
        }
        // if identifier create variablerefnode/
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
        removeEndlines();
        while (peek(0).getType() != Type.END) {
            statements.add(statement());
        }
        matchAndRemove(Type.END);
        removeEndlines();
        return statements;
    }

    public StatementNode statement() {
        // TODO: Call assignment if null call while if null call if
        if (assignment() != null) {
            return assignment();
        } else if (whileExpression() != null) {
            return whileExpression();
        } else if (ifExpression() != null) {
            return ifExpression();
        } else if (forExpression() != null) {
            return forExpression();
        }
        return null;
    }

    public AssignmentNode assignment() {
        // identifier assignment expression endofline
        if (peek(1).getType() == Type.ASSIGN || peek(0).getType() == Type.IDENTIFIER) {
            String name = matchAndRemove(Type.IDENTIFIER).getValue().trim();
            matchAndRemove(Type.ASSIGN);
            Node value = expression();
            removeEndlines();
            return new AssignmentNode(new VariableReferenceNode(name), value);
        }
        return null;
    }

    public Token peek(int x) {
        return tokens.get(x);
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
        // check for if or else if or else
        if (matchAndRemove(Type.IF) != null) {
            BooleanExpressionNode condition = booleanExpression();
            matchAndRemove(Type.THEN);
            ArrayList<StatementNode> statements = statements();
            IfNode ifNode = new IfNode(condition, statements);
            if (peek(0).getType() == Type.ELSE) {
                matchAndRemove(Type.ELSE);
                ifNode.setElseStatements(statements());
            }
            return ifNode;
        } else if (matchAndRemove(Type.ELSIF) != null) {
            matchAndRemove(Type.ELSIF);
            BooleanExpressionNode condition = booleanExpression();
            matchAndRemove(Type.THEN);
            ArrayList<StatementNode> statements = statements();
            IfNode ifNode = new IfNode(condition, statements);
            if (peek(0).getType() == Type.ELSE) {
                matchAndRemove(Type.ELSE);
                ifNode.setElseStatements(statements());
            }
            return ifNode;
        } else if (matchAndRemove(Type.ELSE) != null) {
            matchAndRemove(Type.THEN);
            ArrayList<StatementNode> statements = statements();
            return new IfNode(null, statements);
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

    public ArrayList<FunctionNode> functionDefinition() {
        /*
            It looks for “define”.
            If it finds that token, it starts building a functionAST node .
            It populates the name from the identifier, then looks for the left parenthesis.
            It then looks for variable declarations (see below).
            We then call the Constants, then Variables, then Body function from below.
            name, (, params, ), constants, variables, body
        */
        ArrayList<FunctionNode> functions = new ArrayList<>();
        try {
            while (matchAndRemove(Type.DEFINE) != null) {
                Token name = matchAndRemove(Type.IDENTIFIER);
                if (name.toString() != null) {
                    String nameStr = name.toString();
                }
                matchAndRemove(Type.LPAREN);
                ArrayList<VariableNode> params = parameters();
                matchAndRemove(Type.RPAREN);
                removeEndlines();
                ArrayList<VariableNode> vars = null;
                if (matchAndRemove(Type.VARIABLES) != null) {
                    removeEndlines();
                    vars = variables();
                }
                ArrayList<VariableNode> consta = null;
                matchAndRemove(Type.ENDLINE);
                if (matchAndRemove(Type.CONSTANT) != null) {
                    removeEndlines();
                    consta = constants();
                }
                removeEndlines();
                ArrayList<StatementNode> body = body();
                if (body == null && consta == null && vars == null) {
                    functions.add(new FunctionNode(name.toString(), params, null, null, null));
                } else if (consta == null && vars == null) {
                    functions.add(new FunctionNode(name.toString(), params, null, null, body));
                } else if (body == null && consta == null) {
                    functions.add(new FunctionNode(name.toString(), params, vars, null, null));
                } else if (body == null && vars == null) {
                    functions.add(new FunctionNode(name.toString(), params, null, consta, null));
                } else if (vars == null) {
                    functions.add(new FunctionNode(name.toString(), params, null, consta, body));
                } else if (body == null) {
                    functions.add(new FunctionNode(name.toString(), params, vars, consta, null));
                } else if (consta == null) {
                    functions.add(new FunctionNode(name.toString(), params, vars, null, body));
                } else {
                    functions.add(new FunctionNode(name.toString(), params, vars, consta, body));
                }
            }
            return functions;
        } catch (Exception e) {
            System.err.println("Error in FunctionDefinition");
            throw new RuntimeException(e);
        }
    }

    private ArrayList<VariableNode> parameters() {
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
        ArrayList<VariableNode> paramets = new ArrayList<>();
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
                                VariableNode var = new VariableNode(token.getValue().trim(), null, Type.INTEGER, !(isVar));
                                paramets.add(var);
                            }
                            isVar = false;
                            idenList.clear();
                            state = 5;
                        } else {
                            Token b = matchAndRemove(Type.FLOAT);
                            if (b != null) {
                                // create a VariableNode for each identifier in the list
                                for (Token token : idenList) {
                                    VariableNode var = new VariableNode(token.getValue().trim(), null, Type.FLOAT, !(isVar));
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
                        removeEndlines();
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
                                VariableNode var = new VariableNode(token.getValue().trim(), new FloatNode(Float.parseFloat(number.getValue())), Type.FLOAT, false);
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
                if (statement.toString() != null) {
                    matchAndRemove(Type.ENDLINE);
                    bod.add(statement);
                }
            }
            return bod;
        }

        return bod;
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
                        } else {
                            curState = 4;
                        }
                    }
                    case 3 -> {
                        x = matchAndRemove(Type.INTEGER);
                        if (x != null) {
                            for (Token t : tokens) {
                                constants.add(new VariableNode(t.getValue(), null, Type.INTEGER, isVar));
                            }
                            tokens.clear();
                            curState = 1;
                        }
                        x = matchAndRemove(Type.FLOAT);
                        if (x != null) {
                            for (Token t : tokens) {
                                constants.add(new VariableNode(t.getValue(), null, Type.FLOAT, isVar));
                            }
                            // CLEAR LIST
                            tokens.clear();
                            curState = 1;
                        }
                    }
                    case 4 -> {
                        x = matchAndRemove(Type.COMMA);
                        if (x != null) {
                            curState = 1;
                        }
                    }
                    case 5 -> System.err.println("Error in processConstants - Invalid state");
                    default -> curState = 4;
                }
            }
        } catch (Exception e) {
            System.err.println("Error in processConstants");
            throw new RuntimeException(e);
        }
        return constants;
    }


}
