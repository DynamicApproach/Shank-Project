import java.util.ArrayList;
import java.util.HashMap;


/**
 * Lex method
 * The Lexer class must contain a lex method that accepts a single string and returns a collection (array or list) of Tokens.
 * The lex method must use one or more state machine(s) to iterate over the input string and create appropriate Tokens. Any character not allowed by your state machine(s)
 * should throw an exception. The lexer needs to accumulate characters for some types (consider 123 – we need to accumulate 1, then 2, then 3, then the state machine can
 * tell that the number is complete because the next character is not a number).
 */


public class Lexer {
    HashMap<String, Type> reservedWords = new HashMap<String, Type>();
    int index = 0;
    private ArrayList<Token> tokens = new ArrayList<>();
    private int state = 0;
    private StringBuilder builder = new StringBuilder();
    private String input;

    public ArrayList<Token> Lex(String input) {
        this.input = input;
        setupReservedWords();
        try {
            for (char c : input.toCharArray()) {
                if (reservedWords.containsKey(builder.toString().toUpperCase())) {
                    tokens.add(new Token(reservedWords.get(builder.toString().toUpperCase()), builder.toString()));
                    builder.replace(0, builder.length(), "");
                    state = 0;
                    numState(c);
                } else {
                    numState(c);
                }
                index++;
            }
            // add final token
            if (state == 0 || state == 2 || state == 3 || state == 4 && builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                // tokens.add(new Token(Type.NUMBER, builder.toString()));
                builder.replace(0, builder.length(), "");
                state = 0;
            } else {
                System.out.println("Error: Invalid character6 " + builder.toString());
                builder.replace(0, builder.length(), "");
                state = 0;
                throw new Exception("Invalid character6");
            }
            return tokens;
        } catch (Exception e) {
            System.out.println("Error: Invalid character: " + e + " CAUGHT AT  " + builder.toString());
        }
        state = 0;
        builder.replace(0, builder.length(), "");
        return tokens;
    }

    private void setupReservedWords() {
        // Identifier, define, leftParen, rightParen, integer, real, begin, end, semicolon, colon, equal, comma, variables, constants
        // integer, real, begin, end, variables, constants
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
    }

    private void wordState(char c) {
        state = 0;
        builder.replace(0, builder.length(), "");
        if (Character.isLetter(c)) {
            builder.append(c);
        } else if (Character.isDigit(c)) {
            builder.append(c);
        } else if (c == ' ') {
            if (reservedWords.containsKey(builder.toString())) {
                tokens.add(new Token(reservedWords.get(builder.toString()), builder.toString()));
                builder.replace(0, builder.length(), "");
            } else {
                tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                builder.replace(0, builder.length(), "");
            }
        } else {
            System.out.println("Error: Invalid character2 " + c);
            throw new RuntimeException("Invalid character2");
        }
    }

    private void numState(char c) throws Exception {
        switch (state) {
            case 0:
                switch (c) {
                    case '(' -> {
                        // check if next char in input is a *
                        if (input.toCharArray()[index + 1] == '*') {
                            state = 7;
                        } else {
                            builder.append(c);
                            tokens.add(new Token(Type.LPAREN, "("));
                            builder.replace(0, builder.length(), "");
                        }
                    }
                    case ')' -> {
                        if (input.toCharArray()[index + 1] == '*') {
                            state = 7;
                        } else {
                            builder.append(c);
                            tokens.add(new Token(Type.RPAREN, ")"));
                            builder.replace(0, builder.length(), "");
                        }
                    }
                    case '\n' -> {
                        builder.append(c);
                        tokens.add(new Token(Type.ENDLINE, ")"));
                        builder.replace(0, builder.length(), "");
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
            case 1:
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
                        System.out.println("Error: Invalid character1 " + c);
                        builder.replace(0, builder.length(), "");
                        state = 0;
                        throw new Exception("Invalid character1");
                    }
                }
                break;
            case 2:
                switch (c) {
                    case '\n' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.ENDLINE, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        state = 0;
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
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.ADD, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;

                    }
                    case '/' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.DIVIDE, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;

                    }
                    case '*' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.MULTIPLY, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;

                    }
                    case '-' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.MINUS, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;

                    }
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> builder.append(c);
                    default -> {
                        System.out.println("Error: Invalid character2 " + c);

                        builder.replace(0, builder.length(), "");
                        state = 0;
                        throw new Exception("Invalid character2");
                    }
                }
                break;
            case 3:
                switch (c) {
                    case '\n' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.ENDLINE, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        state = 0;
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
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.ADD, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '/' -> {

                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.DIVIDE, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '*' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.MULTIPLY, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '-' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.MINUS, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    default -> {
                        System.out.println("Error: Invalid character3 " + c);

                        builder.replace(0, builder.length(), "");
                        state = 0;
                        throw new Exception("Invalid character3");
                    }
                }
                break;
            case 4:
                switch (c) {

                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> builder.append(c);
                    case '\n' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.ENDLINE, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '-' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.MINUS, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '/' -> {
                        tokens.add(new Token(Type.NUMBER, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        builder.append(c);
                        tokens.add(new Token(Type.DIVIDE, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '*' -> {
                        tokens.add(new Token(Type.NUMBER, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        builder.append(c);
                        tokens.add(new Token(Type.MULTIPLY, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '+' -> {
                        tokens.add(new Token(Type.NUMBER, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        builder.append(c);
                        tokens.add(new Token(Type.ADD, builder.toString()));

                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }

                    default -> {
                        System.out.println("Error: Invalid character4 " + c);
                        builder.replace(0, builder.length(), "");
                        state = 0;
                        throw new Exception("Invalid character4");
                    }
                }
                break;
            case 5:
                switch (c) {
                    case '\n' -> {
                        if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                            tokens.add(new Token(Type.NUMBER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        builder.append(c);
                        tokens.add(new Token(Type.ENDLINE, builder.toString()));
                        builder.replace(0, builder.length(), "");
                        state = 0;
                    }
                    case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        state = 3;
                        builder.append(c);
                    }
                    case ' ' -> builder.append(c);
                    default -> {
                        System.out.println("Error: Invalid character5 " + c);
                        builder.replace(0, builder.length(), "");
                        state = 0;
                        throw new Exception("Invalid character5");
                    }
                }
            case 6:
                // if not space or newline, then add to builder
                if (c != ' ' && c != '\n') {
                    switch (c) {
                        case ',' -> {
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                                builder.replace(0, builder.length(), "");
                            }
                            builder.append(c);
                            tokens.add(new Token(Type.COMMA, builder.toString()));
                            builder.replace(0, builder.length(), "");
                            state = 0;
                        }
                        case ':' -> {
                            // if builder isnt empty
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                                builder.replace(0, builder.length(), "");
                            }
                            builder.append(c);
                            tokens.add(new Token(Type.COLON, builder.toString()));
                            builder.replace(0, builder.length(), "");
                            state = 0;
                        }
                        case '=' -> {
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                                builder.replace(0, builder.length(), "");
                            }
                            builder.append(c);
                            tokens.add(new Token(Type.EQUAL, builder.toString()));
                            builder.replace(0, builder.length(), "");
                            state = 0;
                        }
                        case ';' -> {
                            if (builder.length() > 0 && !" ".equals(builder.toString()) && !"\n".equals(builder.toString())) {
                                tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                                builder.replace(0, builder.length(), "");
                            }
                            builder.append(c);
                            tokens.add(new Token(Type.SEMICOLON, builder.toString()));
                            builder.replace(0, builder.length(), "");
                            state = 0;
                        }

                        default -> builder.append(c);

                    }

                } else {
                    // if space or newline, then end of word so add to tokens
                    switch (builder.toString()) {
                        case "," -> {

                            tokens.add(new Token(Type.COMMA, builder.toString()));
                            builder.replace(0, builder.length(), "");
                            state = 0;
                        }
                        case ":" -> {
                            // if  input index +1 is = then diff token
                            if (input.charAt(index + 1) == '=') {
                                builder.append(input.charAt(index + 1));
                                tokens.add(new Token(Type.ASSIGN, builder.toString()));
                                builder.replace(0, builder.length(), "");
                                state = 0;
                            } else {
                                tokens.add(new Token(Type.COLON, builder.toString()));
                                builder.replace(0, builder.length(), "");
                                state = 0;
                            }
                        }
                        case "=" -> {
                            if (builder.length() > 0 && ":".equals(builder.toString())) {
                                builder.append(c);
                                tokens.add(new Token(Type.ASSIGN, builder.toString()));
                                builder.replace(0, builder.length(), "");
                                state = 0;
                            } else {
                                tokens.add(new Token(Type.EQUAL, builder.toString()));
                                builder.replace(0, builder.length(), "");
                                state = 0;
                            }
                        }
                        case ";" -> {
                            tokens.add(new Token(Type.SEMICOLON, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        case "\n" -> {
                            tokens.add(new Token(Type.ENDLINE, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                        default -> {
                            tokens.add(new Token(Type.IDENTIFIER, builder.toString()));
                            builder.replace(0, builder.length(), "");
                        }
                    }
                    state = 0;
                }
            case 7:
                // do nothing until end of comment *)
                if (c == '*' && input.charAt(index + 1) == ')') {
                    state = 0;
                }
        }

    }


}
