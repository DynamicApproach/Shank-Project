package shank.project;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Lex method
 * The Lexer class must contain a lex method that accepts a single string and returns a collection (array or list) of Tokens.
 * The lex method must use one or more state machine(s) to iterate over the input string and create appropriate Tokens. Any character not allowed by your state machine(s)
 * should throw an exception. The lexer needs to accumulate characters for some types (consider 123 – we need to accumulate 1, then 2, then 3, then the state machine can
 * tell that the number is complete because the next character is not a number).
 */

// once in state 5 just take the token anyway
@SuppressWarnings("SpellCheckingInspection")
public class Lexer {
    public HashMap<String, Type> reservedWords = new HashMap<>();
    private int index = 0;
    private ArrayList<Token> tokens = new ArrayList<>();
    private int state = 0;
    private StringBuilder builder = new StringBuilder();
    private String input;
    private boolean skip = false;

    public ArrayList<Token> Lex(String input) {
        this.input = input;
        try {
            for (char c : input.toCharArray()) {
                // if skip is true, skip the current character
                if (skip) {
                    skip = false;
                    index++;
                    continue;
                }
                index++;
                numState(c);
            }
            // add final token
            if (state == 0 || state == 2 || state == 3 || state == 4 && builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                builder.setLength(0);
                state = 0;
            } else {
                System.err.println("Error: Invalid character6 " + builder);
                System.err.println("\ninput: " + input + "\n");
                reportErrorAndClear("Invalid character6");
            }
            this.index = 0;
            return tokens;
        } catch (Exception e) {
            System.err.println("Error: Invalid character: " + e + " CAUGHT AT  " + builder);
        }
        state = 0;
        builder.setLength(0);
        return tokens;
    }

    public void setupReservedWords() {
        // Identifier, define, leftParen, rightParen, integer, real, begin, end, semicolon, colon, equal, comma, variables, constants
        // integer, real, begin, end, variables, constants, if, then, else, elsif, for, from, to, while, repeat, until, mod
        reservedWords.put("IDENTIFIER", Type.IDENTIFIER);
        reservedWords.put("DEFINE", Type.DEFINE);
        reservedWords.put("LEFTPAREN", Type.LPAREN);
        reservedWords.put("RIGHTPAREN", Type.RPAREN);
        reservedWords.put("INTEGER", Type.INTEGER);
        reservedWords.put("BEGIN", Type.BEGIN);
        reservedWords.put("END", Type.END);
        reservedWords.put("SEMICOLON", Type.SEMICOLON);
        reservedWords.put("COLON", Type.COLON);
        reservedWords.put("EQUAL", Type.EQUAL);
        reservedWords.put("COMMA", Type.COMMA);
        reservedWords.put("VARIABLES", Type.VARIABLES);
        reservedWords.put("CONSTANTS", Type.CONSTANT);
        reservedWords.put("PLUS", Type.BEGIN);
        reservedWords.put("MINUS", Type.END);
        reservedWords.put("IF", Type.IF);
        reservedWords.put("THEN", Type.THEN);
        reservedWords.put("ELSE", Type.ELSE);
        reservedWords.put("ELSIF", Type.ELSIF);
        reservedWords.put("FOR", Type.FOR);
        reservedWords.put("FROM", Type.FROM);
        reservedWords.put("TO", Type.TO);
        reservedWords.put("WHILE", Type.WHILE);
        reservedWords.put("REPEAT", Type.REPEAT);
        reservedWords.put("REAL", Type.FLOAT);
        reservedWords.put("UNTIL", Type.UNTIL);
        reservedWords.put("MOD", Type.MOD);
        reservedWords.put("VAR", Type.VAR);
        reservedWords.put("DO", Type.DO);
    }

    @SuppressWarnings("unused")
    private void wordState(char c) {
        state = 0;
        builder.setLength(0);
        if (Character.isLetter(c) || Character.isDigit(c)) {
            builder.append(c);
        } else if (c == ' ') {
            if (reservedWords.containsKey(builder.toString())) {
                tokens.add(new Token(reservedWords.get(builder.toString()), builder.toString()));
                builder.setLength(0);
            } else {
                tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                builder.setLength(0);
            }
        } else {
            System.err.println("Error: Invalid character2 " + c);
            throw new RuntimeException("Invalid character2");
        }
    }

    private void numState(char c) throws Exception {
        switch (state) {
            case 0: // initial state - loop with space
                switch (c) {
                    case '(' -> {
                        // check if next char in input is a *
                        if (input.toCharArray().length > index && input.charAt(index) == '*') {
                            state = 7;
                        } else {
                            foundTokState(Type.LPAREN, "(");
                        }
                    }
                    case ')' -> {
                        if (input.toCharArray().length > index && input.charAt(index) == '*') {
                            state = 7;
                        } else {
                            foundTok(Type.RPAREN, ")");
                        }
                    }
                    case '\n' -> foundTok(Type.ENDLINE, ")");
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        state = 2;
                        builder.append(c);
                    }
                    case ' ' -> builder.append(c);
                    case '+', '-' -> {
                        state = 1;
                        builder.append(c);
                    }
                    case '.' -> {
                        state = 5;
                        builder.append(c);
                    }
                    default -> {
                        state = 6;
                        builder.append(c);
                    }
                }
                break;
            case 1: // + or -
                switch (c) {
                    case ' ' -> {
                        if ("+".equals(builder.toString().trim())) {
                            foundTokState(Type.ADD, "+");
                        } else if ("-".equals(builder.toString().trim())) {
                            foundTokState(Type.MINUS, "-");
                        } else {
                            System.err.println("Error: Invalid character3 " + c);
                            throw new RuntimeException("Invalid character3");
                        }
                    }
                    case '.' -> {
                        state = 5;
                        builder.append(c);
                    }
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        state = 2;
                        if ("-".equals(builder.toString().trim())) {
                            foundTok(Type.MINUS, "-");
                            builder.append(c);
                        } else if ("+".equals(builder.toString().trim())) {
                            foundTok(Type.ADD, "+");
                            builder.append(c);
                        } else {
                            builder.append(c);
                        }
                    }
                    default -> {
                        // EG IDEN + IDEN
                        switch (c) {
                            case '+' -> foundTok(Type.ADD, "+");
                            case '-' -> foundTok(Type.MINUS, "-");
                            case '*' -> foundTok(Type.MULTIPLY, "*");
                            case '/' -> foundTok(Type.DIVIDE, "/");
                        }
                        tokens.add(new Token(reservedWords.getOrDefault(builder.toString(), Type.IDENTIFIER), builder.toString()));
                        builder.setLength(0);
                    }
                }
                break;
            case 2: // number
                switch (c) {
                    case '\n' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTokState(Type.NUMBER, builder.toString());
                            foundTokState(Type.ENDLINE, builder.toString());
                        } else {
                            builder.append(c);
                            foundTokState(Type.ENDLINE, builder.toString());
                        }
                    }
                    case ' ' -> {
                        state = 4;
                        builder.append(c);
                    }
                    case '.' -> {
                        state = 3;
                        builder.append(c);
                    }
                    case '+' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ADD, builder.toString());
                    }
                    case '/' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);

                        foundTokState(Type.DIVIDE, builder.toString());

                    }
                    case '*' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.MULTIPLY, builder.toString());
                    }
                    case '-' -> negat(c);
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> builder.append(c);
                    default -> {
                        System.err.println("Error: Invalid character2 " + c);
                        reportErrorAndClear("Invalid character2");
                    }
                }
                break;
            case 3: // number with decimal
                switch (c) {
                    case '\n' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ENDLINE, builder.toString());
                    }
                    case '.' -> {
                        state = 2;
                        builder.append(c);
                    }
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> builder.append(c);
                    case ' ' -> {
                        state = 4;
                        builder.append(c);
                    }
                    case '+' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ADD, builder.toString());
                    }
                    case '/' -> {

                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.DIVIDE, builder.toString());
                    }
                    case '*' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.MULTIPLY, builder.toString());
                    }
                    case '-' -> negat(c);
                    default -> {
                        System.err.println("Error: Invalid character3 " + c);
                        reportErrorAndClear("Invalid character3");
                    }
                }
                break;
            case 4: // space after number
                switch (c) {
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> builder.append(c);
                    case '\n' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ENDLINE, builder.toString());
                    }
                    case '-' -> negat(c);
                    case '/' -> {
                        foundTok(Type.NUMBER, builder.toString());
                        builder.append(c);
                        foundTokState(Type.DIVIDE, builder.toString());
                    }
                    case '*' -> {
                        foundTok(Type.NUMBER, builder.toString());
                        builder.append(c);
                        foundTokState(Type.MULTIPLY, builder.toString());
                    }
                    case '+' -> {
                        foundTok(Type.NUMBER, builder.toString());
                        builder.append(c);
                        foundTokState(Type.ADD, builder.toString());
                    }
                    default -> {
                        foundTokState(Type.NUMBER, builder.toString());
                        builder.append(c);
                    }
                }
                break;
            case 5: // decimal
                switch (c) {
                    case '\n' -> {
                        if (notSpaceOrNewLineAndHasLength()) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ENDLINE, builder.toString());
                    }
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        state = 3;
                        builder.append(c);
                    }
                    case ' ' -> builder.append(c);
                    default -> foundTokState(Type.NUMBER, builder.toString());
                }
            case 6:
                // if not space or newline, then add to builder
                if (c != ' ' && c != '\n') {
                    switch (c) {
                        case ',' -> { // if comma, then foundTok for an identifier and then a comma e.g. "idenNAME, "
                            if (notSpaceOrNewLineAndHasLength()) {
                                checkReservedVsIden();
                            }
                            builder.append(c);
                            foundTokState(Type.COMMA, builder.toString());
                        }
                        case '<' -> {
                            if (input.charAt(index) == '=') {
                                // less than or equal
                                builder.append(c);
                                builder.append('=');
                                skip = true;
                                foundTokState(Type.LESS_EQUAL, builder.toString());
                            } else if (input.charAt(index) == '>') {
                                // not
                                builder.append(c);
                                builder.append('>');
                                skip = true;
                                foundTokState(Type.NOT_EQUAL, builder.toString());
                            }
                            // less
                            builder.append(c);
                            foundTokState(Type.LESS, builder.toString());
                        }
                        case '>' -> {
                            if (input.charAt(index) == '=') {
                                // less than or equal
                                builder.append(c);
                                builder.append('=');
                                skip = true;
                                foundTokState(Type.GREATER_EQUAL, builder.toString());
                            } else {
                                // less
                                builder.append(c);
                                foundTokState(Type.LESS, builder.toString());
                            }
                        }

                        case ':' -> { // if colon, then foundTok for an identifier and then a colon e.g. "idenNAME: "
                            // if  input index +1 is = then diff token
                            if (input.toCharArray().length > index && input.charAt(index) == '=' && notSpaceOrNewLineAndHasLength()) {
                                // eg. "idenNAME:="
                                foundTok(Type.IDENTIFIER, builder.toString());
                                builder.append(c);
                                builder.append(input.charAt(index));
                                foundTokState(Type.ASSIGN, builder.toString());
                                skip = true;
                            } else if (input.toCharArray().length > index && input.charAt(index) == '=' && builder.toString().trim().isEmpty()) {
                                // eg. " :="
                                builder.append(c);
                                builder.append(input.charAt(index));
                                foundTokState(Type.ASSIGN, builder.toString());
                                skip = true;
                            } else notEmpty(c);
                        }
                        case '=' -> {
                            if (notSpaceOrNewLineAndHasLength()) {
                                // if equals, with a : before it, then foundTok for an identifier and then A assign e.g. "idenNAME:= "
                                //if builder has colon then assign
                                if (":".equals(builder.toString())) {
                                    builder.append(c);
                                    foundTokState(Type.ASSIGN, builder.toString());
                                    skip = true;
                                } else { // else foundTok for an identifier and then A equals e.g. "idenNAME= "
                                    foundTok(Type.IDENTIFIER, builder.toString());
                                    builder.append(c);
                                    foundTokState(Type.EQUAL, builder.toString());
                                }
                            }
                            builder.setLength(0);
                            state = 0;
                        }
                        // if semicolon, then foundTok for an identifier and then a semicolon e.g. "idenNAME; "
                        case ';' -> checkReservedAndState(c, Type.SEMICOLON);
                        // if plus, then foundTok for an identifier and then a plus e.g. "idenNAME+ "
                        case '+' -> checkReservedAndState(c, Type.ADD);
                        // if right bracket, then foundTok for an identifier and then a right bracket e.g. "idenNAME) "
                        case ')' -> checkReservedAndState(c, Type.RPAREN);
                        case '(' -> { // if left bracket, then foundTok for an identifier and then a left bracket e.g. "functionNAME( "
                            if (notSpaceOrNewLineAndHasLength()) {
                                foundTok(Type.IDENTIFIER, builder.toString());
                            }
                            if (input.toCharArray().length > index && input.charAt(index) == '*') {
                                state = 7;
                            } else {
                                foundTokState(Type.LPAREN, "(");
                            }
                        }
                        default -> builder.append(c);
                    }

                } else {
                    // check for keyword before token is output
                    if (reservedWords.containsKey(builder.toString().trim().toUpperCase())) { // if reserved word, then foundTok for a reserved word e.g. "BEGIN "
                        reservedWordFromBuilder(reservedWords.get(builder.toString().trim().toUpperCase()), builder.toString().trim().toUpperCase());
                        EOLfound(c);
                    } else {
                        // if space or newline, then end of word so add to tokens
                        // c : int
                        switch (builder.toString()) {
                            case "," ->
                                    foundTokState(Type.COMMA, builder.toString()); // if comma, then foundTok for a comma e.g. ", "
                            case "+" ->
                                    foundTokState(Type.ADD, builder.toString()); // if plus, then foundTok for a plus e.g. "+ "
                            case ":" -> { // if colon, then foundTok for an identifier and then a colon e.g. "idenNAME: "
                                // if  input index +1 is = then diff token
                                // iden : iden
                                if (input.toCharArray().length > index && input.charAt(index) == '=' && notSpaceOrNewLineAndHasLength()) {
                                    foundTok(Type.IDENTIFIER, builder.toString());
                                    builder.append(c);
                                    builder.append(input.charAt(index));
                                    foundTokState(Type.ASSIGN, builder.toString());
                                    skip = true;
                                } else if (input.toCharArray().length > index && input.charAt(index) == '=') {
                                    builder.append(c);
                                    builder.append(input.charAt(index));
                                    foundTokState(Type.ASSIGN, builder.toString());
                                    skip = true;
                                } else {
                                    notEmpty(c);
                                }
                            }
                            // '< '
                            case "<" -> foundTokState(Type.LESS, builder.toString());
                            case "<>" -> foundTokState(Type.NOT_EQUAL, builder.toString());
                            case "<=" -> foundTokState(Type.LESS_EQUAL, builder.toString());
                            case ">=" -> foundTokState(Type.GREATER_EQUAL, builder.toString());
                            case "=" -> { // if equals, then foundTok for a equals eg. "= "
                                if (builder.length() > 0 && ":".equals(builder.toString())) {
                                    builder.append(c);
                                    foundTokState(Type.ASSIGN, builder.toString());
                                    skip = true;
                                } else foundTokState(Type.EQUAL, builder.toString());
                            }
                            case ";" -> foundTok(Type.SEMICOLON, builder.toString());
                            case "\n" -> EOLfound(c);
                            case ")" -> { // if right bracket, then foundTok for an identifier and then a right bracket e.g. "idenNAME) "
                                checkReservedVsIden();
                                foundTokState(Type.RPAREN, builder.toString());
                                EOLfound(c);
                            }
                            case ">" -> foundTokState(Type.GREATER, builder.toString());
                            default -> {
                                foundTokState(Type.IDENTIFIER, builder.toString());
                                EOLfound(c);
                            }
                        }
                    }
                    state = 0;
                }
            case 7:
                // do nothing until end of comment *)
                if (input.toCharArray().length > index && c == '*' && input.charAt(index) == ')') {
                    skip = true;
                    state = 0;
                }
        }

    }

    private void checkReservedAndState(char c, Type type) {
        checkReservedVsIden();
        builder.append(c);
        foundTokState(type, builder.toString());
    }

    private void checkReservedVsIden() {
        if (reservedWords.containsKey(builder.toString().trim().toUpperCase())) { // if reserved word, then foundTok for a reserved word e.g. "BEGIN "
            reservedWordFromBuilder(reservedWords.get(builder.toString().trim().toUpperCase()), builder.toString().trim().toUpperCase());
        } else if (notSpaceOrNewLineAndHasLength()) {
            foundTok(Type.IDENTIFIER, builder.toString());
        }
    }

    private void reservedWordFromBuilder(Type reservedWords, String builder) {
        foundTokState(reservedWords, builder);
    }

    private void EOLfound(char c) {
        if (c == '\n') {
            builder.append(c);
            foundTok(Type.ENDLINE, builder.toString());
        }
    }

    private void notEmpty(char c) {
        if (!builder.toString().trim().isEmpty() || c != ':') {
            if (!":".equals(builder.toString())) {
                checkReservedVsIden();
            }
        }
        builder.append(c);
        foundTokState(Type.COLON, builder.toString());
    }

    private boolean notSpaceOrNewLineAndHasLength() {
        return !builder.toString().trim().isEmpty() && !"\n".equals(builder.toString().trim());
    }

    private void reportErrorAndClear(String Invalid_character1) throws Exception {
        builder.setLength(0);
        state = 0;
        throw new Exception(Invalid_character1);
    }

    private void negat(char c) {
        if (notSpaceOrNewLineAndHasLength()) {
            foundTok(Type.NUMBER, builder.toString());
        }
        builder.append(c);
        foundTokState(Type.MINUS, builder.toString());
    }

    // found a token, so add it to the list and reset builder
    protected void foundTok(Type ttype, String str) {
        tokens.add(new Token(ttype, str.trim()));
        builder.setLength(0);
    }

    // found a token that ends curstate, so add it to the list and reset builder and state
    private void foundTokState(Type ttype, String newitem) {
        tokens.add(new Token(ttype, newitem.trim()));
        builder.setLength(0);
        state = 0;
    }

}
