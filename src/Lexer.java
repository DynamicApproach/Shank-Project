import java.util.ArrayList;


public class Lexer {
    private ArrayList<Token> tokens = new ArrayList<>();
    /**
     * Lex method
     * The Lexer class must contain a lex method that accepts a single string and returns a collection (array or list) of Tokens.
     * The lex method must use one or more state machine(s) to iterate over the input string and create appropriate Tokens. Any character not allowed by your state machine(s)
     * should throw an exception. The lexer needs to accumulate characters for some types (consider 123 – we need to accumulate 1, then 2, then 3, then the state machine can
     * tell that the number is complete because the next character is not a number).
     */

    private int state = 0;
    private StringBuilder builder = new StringBuilder();

    public ArrayList<Token> Lex(String input) {
        try {
            for (char c : input.toCharArray()) {
                //TODO: check if the character is a number or letter
                // if a letter, start in the 'letter' state machine
                //  if it's a spcae, end the word and check it against the map
                // if it's a number, start in the 'number' state machine
                // TODO: Add a hashmap to check if the word is a keyword
// hashmap words:
// identifier, define, leftParen, rightParen, integer, real, begin, end, semicolon, colon, equal, comma, variables, constants, integer, real, begin, end, variables, constants
                // TODO: move this to it's own file
                switch (state) {
                    case 0:
                        switch (c) {
                            case '(' -> {
                                builder.append(c);
                                tokens.add(new Token(Type.LPAREN, "("));
                                builder.replace(0, builder.length(), "");
                            }
                            case ')' -> {
                                builder.append(c);
                                tokens.add(new Token(Type.RPAREN, ")"));
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
                                System.out.println("Error: Invalid character0: " + c);
                                state = 0;
                                builder.replace(0, builder.length(), "");
                                throw new Exception("Invalid character0");
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
                            // case '\n' -> { tokens.add(new Token(Symbol.ENDL, buffer)); buffer= ""; state = 0; buffer += "ENDL"; }
                            case ' ' -> {
                                state = 4;
                                builder.append(c);
                            }
                            case '.' -> {
                                state = 3;
                                builder.append(c);
                            }
                            case '+' -> {
                                tokens.add(new Token(Type.NUMBER, builder.toString()));
                                builder.replace(0, builder.length(), "");
                                builder.append(c);
                                tokens.add(new Token(Type.ADD, builder.toString()));

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
                            case '-' -> {
                                tokens.add(new Token(Type.NUMBER, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                builder.append(c);
                                tokens.add(new Token(Type.MINUS, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                state = 0;

                            }
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                                    builder.append(c);
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
                            //  case '\n' -> {tokens.add(new Token(Symbol.ENDL, buffer)); buffer= ""; state = 0; buffer += "ENDL"; }
                            case '.' -> {
                                state = 2;
                                builder.append(c);
                            }
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                                    builder.append(c);
                            case ' ' -> {
                                state = 4;
                                builder.append(c);
                            }
                            case '+' -> {
                                System.out.println("Buffer:     " + builder.toString());
                                tokens.add(new Token(Type.NUMBER, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                builder.append(c);
                                tokens.add(new Token(Type.ADD, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                state = 0;
                            }
                            case '/' -> {
                                System.out.println("Buffer:     " + builder.toString());
                                tokens.add(new Token(Type.NUMBER, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                builder.append(c);
                                tokens.add(new Token(Type.DIVIDE, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                state = 0;
                            }
                            case '*' -> {
                                System.out.println("Buffer:     " + builder.toString());
                                tokens.add(new Token(Type.NUMBER, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                builder.append(c);
                                tokens.add(new Token(Type.MULTIPLY, builder.toString()));

                                builder.replace(0, builder.length(), "");
                                state = 0;
                            }
                            case '-' -> {
                                System.out.println("Buffer:     " + builder.toString());
                                tokens.add(new Token(Type.NUMBER, builder.toString()));

                                builder.replace(0, builder.length(), "");
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
                            case ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ->
                                    builder.append(c);
                            case '-' -> {
                                tokens.add(new Token(Type.NUMBER, builder.toString()));

                                builder.replace(0, builder.length(), "");
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
                }
            }
            // add final token
            if (state == 2 || state == 3 || state == 4) {
                tokens.add(new Token(Type.NUMBER, builder.toString()));
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


}
