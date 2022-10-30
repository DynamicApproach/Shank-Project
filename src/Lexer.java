import java.util.ArrayList;
import java.util.HashMap;


/**
 * Lex method
 * The Lexer class must contain a lex method that accepts a single string and returns a collection (array or list) of Tokens.
 * The lex method must use one or more state machine(s) to iterate over the input string and create appropriate Tokens. Any character not allowed by your state machine(s)
 * should throw an exception. The lexer needs to accumulate characters for some types (consider 123 – we need to accumulate 1, then 2, then 3, then the state machine can
 * tell that the number is complete because the next character is not a number).
 */


@SuppressWarnings("SpellCheckingInspection")
public class Lexer {
    HashMap<String, Type> reservedWords = new HashMap<>();
    int index = 0;
    private ArrayList<Token> tokens = new ArrayList<>();
    private int state = 0;
    private StringBuilder builder = new StringBuilder();
    private String input;

    public ArrayList<Token> Lex(String input) {
        this.input = input;
        try {
            for (char c : input.toCharArray()) {
                index++;
                if (reservedWords.containsKey(builder.toString().toUpperCase())) {
                    foundTokState(reservedWords.get(builder.toString().toUpperCase()), builder.toString());
                    numState(c);
                } else {
                    numState(c);
                }

            }
            // add final token
            if (state == 0 || state == 2 || state == 3 || state == 4 && builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                builder.setLength(0);
                state = 0;
            } else {
                System.err.println("Error: Invalid character6 " + builder.toString());
                reportErrorAndClear("Invalid character6");
            }
            return tokens;
        } catch (Exception e) {
            System.err.println("Error: Invalid character: " + e + " CAUGHT AT  " + builder.toString());
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
        reservedWords.put("REAL", Type.REAL);
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
        reservedWords.put("UNTIL", Type.UNTIL);
        reservedWords.put("MOD", Type.MOD);
        reservedWords.put("VAR", Type.VAR);
        reservedWords.put("DO", Type.DO);
    }

    @SuppressWarnings("unused")
    private void wordState(char c) {
        state = 0;
        builder.setLength(0);
        if (Character.isLetter(c)) {
            builder.append(c);
        } else if (Character.isDigit(c)) {
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
                        if (input.toCharArray().length > index + 1 && input.toCharArray()[index + 1] == '*') {
                            state = 7;
                        } else {
                            builder.append(c);
                            tokens.add(new Token(Type.LPAREN, "("));
                            builder.setLength(0);
                        }
                    }
                    case ')' -> {
                        if (input.toCharArray().length > index + 1 && input.toCharArray()[index + 1] == '*') {
                            state = 7;
                        } else {
                            builder.append(c);
                            foundTok(Type.RPAREN, ")");
                        }
                    }
                    case '\n' -> {
                        builder.append(c);
                        foundTok(Type.ENDLINE, ")");
                    }
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
                    case ' ' -> builder.append(c);
                    case '.' -> {
                        state = 5;
                        builder.append(c);
                    }
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        state = 2;
                        builder.append(c);
                    }
                    default -> {
                        System.err.println("Error: Invalid character1 " + c);
                        reportErrorAndClear("Invalid character1");
                    }
                }
                break;
            case 2: // number
                switch (c) {
                    case '\n' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.setLength(0);
                        }
                        builder.append(c);
                        foundTokState(Type.ENDLINE, builder.toString());
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
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ADD, builder.toString());
                    }
                    case '/' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);

                        foundTokState(Type.DIVIDE, builder.toString());

                    }
                    case '*' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
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
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
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
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.ADD, builder.toString());
                    }
                    case '/' -> {

                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            foundTok(Type.NUMBER, builder.toString());
                        }
                        builder.append(c);
                        foundTokState(Type.DIVIDE, builder.toString());
                    }
                    case '*' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
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
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
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
                        System.err.println("Error: Invalid character4 " + c);
                        reportErrorAndClear("Invalid character4");
                    }
                }
                break;
            case 5: // decimal
                switch (c) {
                    case '\n' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
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
                    default -> {
                        System.err.println("Error: Invalid character5 " + c);
                        reportErrorAndClear("Invalid character5");
                    }
                }
            case 6:
                // if not space or newline, then add to builder
                if (c != ' ' && c != '\n') {
                    switch (c) {
                        case ',' -> {
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                foundTok(Type.IDENTIFIER, builder.toString());
                            }
                            builder.append(c);
                            foundTokState(Type.COMMA, builder.toString());
                        }
                        case ':' -> {
                            // if  input index +1 is = then diff token
                            if (input.toCharArray().length < index + 1 && input.charAt(index + 1) == '=') {
                                builder.append(input.charAt(index + 1));
                                foundTokState(Type.ASSIGN, builder.toString());
                            } else {
                                foundTokState(Type.COLON, builder.toString());
                            }
                        }
                        case '=' -> {
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                //if builder has colon then assign
                                if (":".equals(builder.toString())) {
                                    builder.append(c);
                                    foundTokState(Type.ASSIGN, builder.toString());
                                } else {
                                    foundTok(Type.IDENTIFIER, builder.toString());
                                    builder.append(c);
                                    foundTokState(Type.EQUAL, builder.toString());
                                }
                            }
                            builder.setLength(0);
                            state = 0;
                        }
                        case ';' -> {
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                foundTok(Type.IDENTIFIER, builder.toString());
                            }
                            builder.append(c);
                            foundTokState(Type.SEMICOLON, builder.toString());
                        }
                        default -> builder.append(c);
                    }

                } else {
                    // if space or newline, then end of word so add to tokens -- add word state here?
                    switch (builder.toString()) {
                        case "," -> foundTokState(Type.COMMA, builder.toString());
                        case ":" -> {
                            // if  input index +1 is = then diff token
                            if (input.toCharArray().length < index + 1 && input.charAt(index + 1) == '=') {
                                builder.append(input.charAt(index + 1));
                                foundTokState(Type.ASSIGN, builder.toString());
                            } else {
                                foundTokState(Type.COLON, String.valueOf(builder));
                            }
                        }
                        case "=" -> {
                            if (builder.length() > 0 && ":".equals(builder.toString())) {
                                builder.append(c);
                                foundTokState(Type.ASSIGN, builder.toString());
                            } else {
                                foundTokState(Type.EQUAL, builder.toString());
                            }
                        }
                        case ";" -> foundTok(Type.SEMICOLON, builder.toString());
                        case "\n" -> foundTok(Type.ENDLINE, builder.toString());
                        default -> foundTok(Type.IDENTIFIER, builder.toString());
                    }
                    state = 0;
                }
            case 7:
                // do nothing until end of comment *)
                if (input.toCharArray().length < index + 1 && c == '*' && input.charAt(index + 1) == ')') {
                    state = 0;
                }
        }

    }

    private void reportErrorAndClear(String Invalid_character1) throws Exception {
        builder.setLength(0);
        state = 0;
        throw new Exception(Invalid_character1);
    }

    private void negat(char c) {
        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
            foundTok(Type.NUMBER, builder.toString());
        }
        builder.append(c);
        foundTokState(Type.MINUS, builder.toString());
    }

    // found a token, so add it to the list and reset builder
    private void foundTok(Type ttype, String c) {
        tokens.add(new Token(ttype, c));
        builder.setLength(0);
    }

    // found a token that ends curstate, so add it to the list and reset builder and state
    private void foundTokState(Type ttype, String c) {
        tokens.add(new Token(ttype, c));
        builder.setLength(0);
        state = 0;
    }

}
