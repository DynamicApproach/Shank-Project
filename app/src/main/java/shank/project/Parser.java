package shank.project;
import java.util.ArrayList;



@SuppressWarnings({"UnusedReturnValue", "unused"})
public class Parser {
    ArrayList<StatementNode> statements = new ArrayList<>();
    ArrayList<StatementNode> bod = new ArrayList<>();
    int curstate = 0;
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
        while (!tokens.isEmpty() && tokens.get(0).getType() == Type.ENDLINE) {
            tokens.remove(0);
        }
    }
    // if a = 5+3
    // a = b>3
    // < > = <= >= == <>
    // lower prio then + - * /
    // all the same prio
    // eval left to right with prio
    // don't have to chain like w +/-
    //BooleanExpressionNode node = null;
    //        try {
    //            Type token = peek(1).getType();
    //            node = new BooleanExpressionNode(expression(), token, expression());
    //        } catch (Exception e) {
    //            System.err.println("Not an expression");
    //            throw new RuntimeException(e); // less, greater, ect lowest priority
    //        }

    public BooleanExpressionNode booleanExpression() {
        // check for expression operator expression and make a new booleanExpressionNode
        Node curnode = null;
        try {
            removeEndlines();
            Node express = expression();
            Type token = peek(0).getType();
            if (token == Type.EQUAL || token == Type.NOT_EQUAL || token == Type.LESS || token == Type.GREATER || token == Type.LESS_EQUAL || token == Type.GREATER_EQUAL) {
                curnode = expression();
                return new BooleanExpressionNode(express, token, curnode);
            } else {
                return (BooleanExpressionNode) express;
            }
        } catch (Exception e) {
            System.err.println("Not an expression");
            throw new RuntimeException(e); // less, greater, ect lowest priority
        }
    }

    // expression, term, factor methods
    // Expression is the highest level of the grammar
    // Expression is a list of terms separated by + or -
    public Node expression() {
        // boolean-expression term factor
        removeEndlines();
        Node node = term();
        int a = 0;
        if (node == null) {
            return null;
        }
        try {
            if (quickPeek() == Type.ADD || quickPeek() == Type.MINUS) {
                node = addOrMinus(node, a);
            } else { // take care of boolean expression here
                // eg. a > b or 1 < 2
                // read a then get here and check for > or < then read b
                switch (quickPeek()) {
                    case EQUAL, LESS, LESS_EQUAL, GREATER_EQUAL, GREATER, NOT_EQUAL, EQUAL_EQUAL -> {
                        Token token = matchAndRemove(quickPeek());
                        node = new BooleanExpressionNode(node, token.getType(), expression());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return node;
    }

    private Node addOrMinus(Node node, int a) {
        while (quickPeek() == Type.ADD || quickPeek() == Type.MINUS) {
            Type token = quickPeek();
            // 5 < 3 < 7
            switch (token) {
                case ADD -> {
                    matchAndRemove(token);
                    node = new MathOpNode(node, factor(), Type.ADD);
                }
                case MINUS -> {
                    matchAndRemove(token);
                    node = new MathOpNode(node, factor(), Type.MINUS);
                }
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
        Type token = quickPeek();
        while (token == Type.MULTIPLY || token == Type.DIVIDE || token == Type.MOD) {
            switch (token) {
                case MULTIPLY -> {
                    matchAndRemove(token);
                    node = new MathOpNode(node, term(), Type.MULTIPLY);
                }
                case DIVIDE -> {
                    matchAndRemove(token);
                    node = new MathOpNode(node, term(), Type.DIVIDE);
                }
                case MOD -> {
                    matchAndRemove(token);
                    node = new MathOpNode(node, term(), Type.MOD);
                }
            }
            token = quickPeek();
        }
        return node;
    }

    // Factor is a number or a parenthesized expression.
    public Node factor() {
        removeEndlines();
        if (quickPeek() == Type.NUMBER) {
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
        } else if (matchAndRemove(Type.LPAREN) != null) {
            Node node = expression();
            matchAndRemove(Type.RPAREN);
            return node;
        } else if (quickPeek() == Type.TRUE) {
            return new BooleanNode(true);
        } else if (quickPeek() == Type.FALSE) {
            return new BooleanNode(false);
        } else if (quickPeek() == Type.CHAR) {
            return new CharNode(peek(0).getValue().charAt(0));
        } else if (quickPeek() == Type.STRING) {
            return new StringNode(peek(0).getValue());
        } else {
            // if identifier create variablerefnode/
            Token temp = matchAndRemove(Type.IDENTIFIER);
            return (temp != null) ? new VariableReferenceNode(temp.toString()) : null;
        }
        return null;
    }

    private Type quickPeek() {
        return peek(0).getType();
    }

    private Token quickPeekToken() {
        return peek(0);
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
        removeEndlines();
        if (node == null) {
            System.err.println("Node cannot be made.");
            return null;
        } else {
            return node;
        }
    }

    public ArrayList<StatementNode> statements() {
        ArrayList<StatementNode> statements = new ArrayList<>();
        removeEndlines();
        matchAndRemove(Type.BEGIN);
        removeEndlines();
        while (quickPeek() != Type.END) {
            statements.add(statement());
            removeEndlines();
        }
        matchAndRemove(Type.END);
        if (!tokens.isEmpty())
            removeEndlines();
        return statements;
    }

    public StatementNode statement() {
        AssignmentNode as = assignment();
        if (as != null)
            return as;
        WhileNode wh = whileExpression();
        if (wh != null)
            return wh;
        ForNode forNode = forExpression();
        if (forNode != null)
            return forNode;
        RepeatNode repeatNode = repeatExpression();
        if (repeatNode != null)
            return repeatNode;
        IfNode ifNode = ifExpression();
        if (ifNode != null)
            return ifNode;
        StatementNode functionCallNode = functionCall();
        if (functionCallNode != null)
            return functionCallNode;

        return ifExpression();
    }

    private StatementNode functionCall() {
        // f1 var inputnum, var res
        // A function call has a name (of the function) and a list of parameters.
        // A parameter needs to be its own ASTNode because a parameter can be a variable (VariableReferenceNode) or a constant value (an ASTNode).
        // IDEN VAR PARAM COMMA VAR PARAM COMMA
        // State 0 : var -> 1
        // State 1 : Number -> State 3 , state 8
        // State 1 : String -> State 3 , State 9
        // State 2 : Comma -> 1/2/4 , 7
        // State 3 : Variable -> State 4
        // State 4 : Iden -> State 5
        // State 5 : Comma -> State 1/2/4
        // State 7 : EndLine -> Return
        // State 8 : Error -> Return

        String name = null;
        ArrayList<ParameterNode> params = new ArrayList<>();
        while (peek(0).getType() != Type.ENDLINE) {
            if (quickPeek() == Type.IDENTIFIER)
                name = matchAndRemove(Type.IDENTIFIER).getValue();
            else return null;
            switch (curstate) {
                case 0 -> {
                    while (peek(0).getType() != Type.ENDLINE) {
                        if (peek(0).getType() == Type.VAR) {
                            matchAndRemove(Type.VAR);// loop over identifiers after a var till EOL or comma
                            if (peek(0).getType() == Type.IDENTIFIER) {

                                params.add(new ParameterNode(matchAndRemove(Type.IDENTIFIER).getValue(), true));
                                while (matchAndRemove(Type.COMMA) != null && quickPeek() != Type.VAR && quickPeek() != Type.ENDLINE) {
                                    params.add(new ParameterNode(matchAndRemove(Type.IDENTIFIER).getValue(), true));
                                }
                                if (quickPeek() == Type.VAR)
                                    curstate = 0;
                                else if (quickPeek() == Type.ENDLINE)
                                    return new FunctionCallNode(name, params, false);
                            }

                        } else {
                            if (peek(0).getType() == Type.IDENTIFIER) {

                                params.add(new ParameterNode(matchAndRemove(Type.IDENTIFIER).getValue(), false));
                                while (matchAndRemove(Type.COMMA) != null && quickPeek() != Type.VAR && quickPeek() != Type.ENDLINE) {
                                    params.add(new ParameterNode(matchAndRemove(Type.IDENTIFIER).getValue(), false));
                                }
                                if (quickPeek() == Type.VAR)
                                    curstate = 0;
                                else if (quickPeek() == Type.ENDLINE)
                                    return new FunctionCallNode(name, params, false);
                            }
                            // no var check for string or number first, if not is an unmodifiable variable
                        }
                    }
                }
                case 1 -> {
                    // check for  number or string
                    // only if no var token
                    // if number or string, add to params
                    // check for comma, if comma go to 0 else go to 10 (make a new param node with the num or string)
                    if (peek(0).getType() == Type.NUMBER) {
                        // params.add(new IntegerNode(Integer.parseInt(matchAndRemove(Type.NUMBER).getValue())));
                        curstate = 3;
                    } else if (peek(0).getType() == Type.STRING) {
                        // params.add(new ParameterNode( new StringNode(matchAndRemove(Type.STRING).toString())));
                        curstate = 3;
                    } else {
                        // if no var and no number or string, then it is an unmodifiable variable
                        curstate = 2;
                    }
                }
                case 2 -> {
                    // check for unmodifiable variable
                    // if unmodifiable variable, add to params
                    if (peek(0).getType() == Type.IDENTIFIER) {
                        params.add(new ParameterNode(matchAndRemove(Type.IDENTIFIER).getValue(), false));
                        curstate = 3;
                    } else {
                        curstate = 5; // error
                    }
                }
                case 3 -> {
                    // check for comma, if so then go to 0
                    if (peek(0).getType() == Type.COMMA) {
                        matchAndRemove(Type.COMMA);
                        curstate = 0;
                    } else {
                        curstate = 4; // finish
                    }
                }
                case 4 -> {
                    // is good to go can return a function call node
                    return new FunctionCallNode(name, params, false);
                }
                case 5 -> {
                    // has an error
                    System.err.println("Error in function call");
                }

            }
            return new FunctionCallNode(name, params, false);
        }
        return null;
    }

    public AssignmentNode assignment() {
        // identifier assignment expression endofline
        if (peek(1).getType() == Type.ASSIGN && quickPeek() == Type.IDENTIFIER) {
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
        if (matchAndRemove(Type.WHILE) != null) {
            removeEndlines();
            BooleanExpressionNode condition = booleanExpression();
            removeEndlines();
            matchAndRemove(Type.BEGIN);
            removeEndlines();
            ArrayList<StatementNode> statements = statements();
            return new WhileNode(condition, statements);
        }
        return null;
    }

    public RepeatNode repeatExpression() {
        // while booleanExpression do statements end
        if (matchAndRemove(Type.REPEAT) != null) {
            removeEndlines();
            BooleanExpressionNode condition = booleanExpression();
            removeEndlines();
            matchAndRemove(Type.BEGIN);
            removeEndlines();
            ArrayList<StatementNode> statements = statements();
            return new RepeatNode(condition, statements);
        }
        return null;
    }

    // need to chain ifNode together -> ifNode(cond, statements, ifNode)
    public IfNode ifExpression() {
        if (matchAndRemove(Type.IF) != null) {
            BooleanExpressionNode condition = booleanExpression();
            removeEndlines();
            matchAndRemove(Type.THEN);
            removeEndlines();
            ArrayList<StatementNode> statements = statements();

            // Create the initial IfNode with the first condition and statements
            IfNode ifNode = new IfNode(condition, statements);
            removeEndlines();

            // Check for additional elsif statements
            while (matchAndRemove(Type.ELSIF) != null) {
                // Parse the next condition and statements for the elsif
                BooleanExpressionNode childCondition = booleanExpression();
                matchAndRemove(Type.THEN);
                ArrayList<StatementNode> childStatements = statements();

                // Create a new IfNode for the elsif and set it as the next if node
                // in the current IfNode
                IfNode nestedIf = new IfNode(childCondition, childStatements);
                ifNode.setIfNode(nestedIf);

                // Set the current IfNode to the new nested IfNode, so that it
                // can be used as the else branch for the next elsif statement
                ifNode = nestedIf;

                removeEndlines();
            }

            // Check for an else statement
            if (matchAndRemove(Type.ELSE) != null) {
                ifNode.setElseStatements(statements());
            }

            return ifNode;
        }
        return null;
    }

    // for
    public ForNode forExpression() {
        // for identifier assignment expression to expression do statements end
        if (matchAndRemove(Type.FOR) != null) {
            String name = matchAndRemove(Type.IDENTIFIER).getValue().trim();
            matchAndRemove(Type.FROM);
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
        ArrayList<FunctionNode> functions = new ArrayList<>();
        try {
            removeEndlines();
            while (matchAndRemove(Type.DEFINE) != null && !tokens.isEmpty()) {
                Token name = matchAndRemove(Type.IDENTIFIER);
                String namestr = "";
                if (name != null) {
                    namestr = name.getValue();
                }
                matchAndRemove(Type.LPAREN);
                ArrayList<VariableNode> params = parameters();
                matchAndRemove(Type.RPAREN);
                removeEndlines();
                ArrayList<VariableNode> consta = null;
                removeEndlines();
                if (matchAndRemove(Type.CONSTANT) != null) {
                    removeEndlines();
                    consta = constants();
                }
                removeEndlines();
                ArrayList<VariableNode> vars = null;
                if (matchAndRemove(Type.VARIABLES) != null) {
                    removeEndlines();
                    vars = variables();
                }

                removeEndlines();
                ArrayList<StatementNode> body = body();
                if (body == null && consta == null && vars == null) {
                    functions.add(new FunctionNode(namestr, params, null, null, null));
                } else if (consta == null && vars == null) {
                    functions.add(new FunctionNode(namestr, params, null, null, body));
                } else if (body == null && consta == null) {
                    functions.add(new FunctionNode(namestr, params, vars, null, null));
                } else if (body == null && vars == null) {
                    functions.add(new FunctionNode(namestr, params, null, consta, null));
                } else if (vars == null) {
                    functions.add(new FunctionNode(namestr, params, null, consta, body));
                } else if (body == null) {
                    functions.add(new FunctionNode(namestr, params, vars, consta, null));
                } else if (consta == null) {
                    functions.add(new FunctionNode(namestr, params, vars, null, body));
                } else {
                    functions.add(new FunctionNode(namestr, params, vars, consta, body));
                }
                if (tokens.isEmpty()) {
                    break;
                } else {
                    removeEndlines();
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
        or a semicolon (for function definitions). For each variable, we make a VariableNode like we did for constants.
        IDEN     COMMA/COLON    DATA-TYPE   EOL OR ; IDEN    COMMA/COLON    DATA-TYPE     )/EOL
        */// STATES:
        // State 0 - Var -> 1
        // State 1 - IDEN -> 2 or  3
        // State 2 - COMMA -> 0 or 3
        // State 3 - COLON -> 4 or 6
        // State 4 - INT OR REAL -> 0,5 or 6
        // State 5 - SEMI-COLON -> 0 or 6
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
                                VariableNode var = new VariableNode(token.getValue().trim(), new IntegerNode(0), Type.INTEGER, !(isVar));
                                paramets.add(var);
                            }
                            isVar = false;
                            idenList.clear();
                            state = 5;
                        } else {
                            Token floaty = matchAndRemove(Type.FLOAT);
                            if (floaty != null) {
                                // create a VariableNode for each identifier in the list
                                for (Token token : idenList) {
                                    VariableNode var = new VariableNode(token.getValue().trim(), new FloatNode(0), Type.FLOAT, !(isVar));
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
                        Token semi = matchAndRemove(Type.SEMICOLON);
                        if (semi != null) {
                            state = 0;
                        } else {
                            state = 6;
                        }
                    }
                    case 6 -> {
                        throw new RuntimeException("Error in Parameters");
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
        // If it finds it, it calls a processConstants function that looks for token
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
                                if (number.getValue() != null) {
                                    VariableNode var = new VariableNode(token.getValue().trim(), new IntegerNode(Integer.parseInt(number.getValue())), Type.INTEGER, false);
                                    consties.add(var);
                                } else {
                                    VariableNode var = new VariableNode(token.getValue().trim(), new IntegerNode(0), Type.INTEGER, false);
                                    consties.add(var);
                                }
                            }
                            idenList.clear();
                            state = 3;
                        } else if (isFloat(number)) {
                            for (Token token : idenList) {
                                if (number.getValue() != null) {
                                    VariableNode var = new VariableNode(token.getValue().trim(), new FloatNode(Float.parseFloat(number.getValue())), Type.FLOAT, false);
                                    consties.add(var);
                                } else {
                                    VariableNode var = new VariableNode(token.getValue().trim(), new FloatNode(0.0f), Type.FLOAT, false);
                                    consties.add(var);
                                }
                            }
                            idenList.clear();
                            state = 0;
                        } else {
                            state = 4;
                        }

                    }
                    case 3 -> {
                        if (quickPeek() == Type.ENDLINE || quickPeek() == Type.IDENTIFIER) {
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
        StatementNode statement = null;
        ArrayList<StatementNode> bod = new ArrayList<>();
        removeEndlines();
        if (matchAndRemove(Type.BEGIN) != null) {
            removeEndlines();
            // while not at the end token, keep adding statements
            try {
                while (!tokens.isEmpty() && matchAndRemove(Type.END) == null && quickPeek() != Type.DEFINE) {
                    removeEndlines();
                    if (quickPeek() == Type.BEGIN) {
                        bod.addAll(body());
                    } else {
                        bod.addAll(statements());
                    }
                }
            } catch (Exception ep) {
                System.err.println("Error in Body - Token expected but not found.");
                throw new RuntimeException(ep);
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
        or a semicolon (for function definitions). For each variable, we make a VariableNode like we did for constants.
        IDEN     COMMA  COLON    DATA-TYPE   EOL OR ;
        For each variable, we make a VariableNode like we did for constants.*/
        // IDEN COMMA IDEN COMMA COLON TYPE ENDLINE
        // A,B,C:INT
        // D:REAL
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
            while (curState != 5) {
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
                        }
                        // else if(matchAndRemove(Type.ENDLINE)!=null){
                        //     curState = 1;
                        // } else if (this.tokens.get(0).getType().equals(Type.IDENTIFIER)) {
                        //     curState = 0;
                        // } else if (this.tokens.get(0).getType().equals(Type.BEGIN)) {
                        //     curState = 3;
                        // }
                        else {
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
                                constants.add(new VariableNode(t.getValue(), new IntegerNode(0), Type.INTEGER, isVar));
                            }
                            tokens.clear();
                            curState = 4;
                        }
                        x = matchAndRemove(Type.FLOAT);
                        if (x != null) {
                            for (Token t : tokens) {
                                constants.add(new VariableNode(t.getValue(), new FloatNode(0), Type.FLOAT, isVar));
                            }
                            tokens.clear();
                            curState = 4;
                        }
                    }
                    case 4 -> {
                        x = matchAndRemove(Type.ENDLINE);
                        if (x != null) {
                            if (quickPeek().equals(Type.BEGIN)) {
                                return constants;
                            } else if (quickPeek().equals(Type.IDENTIFIER)) {
                                curState = 0;
                            } else {
                            }
                        }
                    }
                    case 5 -> {
                        //do nothing
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in processConstants");
            throw new RuntimeException(e);
        }
        return constants;
    }
}