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
    private String buffer = "";
    // TODO: Change buffer to StringBuilder
    public ArrayList<Token> Lex(String input) {
        try {
            for (char c : input.toCharArray()) {
                switch (state) {
                    case 0:
                        switch (c) {
                            case '(' -> {
                                tokens.add(new Token(Type.LPAREN, "("));
                                buffer += c;
                            }
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                                state = 2;
                                buffer += c;
                            }
                            case ' ' -> buffer += c;
                            case '+', '-' -> {
                                state = 1;
                                buffer += c;
                            }
                            case '.' -> {
                                state = 5;
                                buffer += c;
                            }
                            default -> {
                                System.out.println("Error: Invalid character0: " + c);
                                state = 0;
                                buffer = "";
                                throw new Exception("Invalid character0");
                            }
                        }
                        break;
                    case 1:
                        switch (c) {
                            case ' ' -> buffer += c;
                            case '.' -> {
                                state = 5;
                                buffer += c;
                            }
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                                state = 2;
                                buffer += c;
                            }
                            default -> {
                                System.out.println("Error: Invalid character1 " + c);
                                buffer = "";
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
                                buffer += c;
                            }
                            case '.' -> {
                                state = 3;
                                buffer += c;
                            }
                            case '+' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = "";
                                buffer += c;
                                tokens.add(new Token(Type.PLUS, buffer));
                                buffer = "";
                                state = 0;

                            }
                            case '/' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = "";
                                buffer += c;
                                tokens.add(new Token(Type.DIV, buffer));
                                buffer = "";
                                state = 0;

                            }
                            case '*' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = "";
                                buffer += c;
                                tokens.add(new Token(Type.MULTI, buffer));
                                buffer = "";
                                state = 0;

                            }
                            case '-' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = "";
                                buffer += c;
                                tokens.add(new Token(Type.MINUS, buffer));
                                buffer = "";
                                state = 0;

                            }
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> buffer += c;
                            default -> {
                                System.out.println("Error: Invalid character2 " + c);
                                buffer = "";
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
                                buffer += c;
                            }
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> buffer += c;
                            case ' ' -> {
                                state = 4;
                                buffer += c;
                            }
                            case '+' -> {
                                System.out.println("Buffer:     " + buffer);
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.PLUS, buffer));
                                buffer = "";
                                state = 0;
                            }
                            case '/' -> {
                                System.out.println("Buffer:     " + buffer);
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.DIV, buffer));
                                buffer = "";
                                state = 0;
                            }
                            case '*' -> {
                                System.out.println("Buffer:     " + buffer);
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.MULTI, buffer));
                                buffer = "";
                                state = 0;
                            }
                            case '-' -> {
                                System.out.println("Buffer:     " + buffer);
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.MINUS, buffer));
                                buffer = "";
                                state = 0;
                            }
                            default -> {
                                System.out.println("Error: Invalid character3 " + c);
                                buffer = "";
                                state = 0;
                                throw new Exception("Invalid character3");
                            }
                        }
                        break;
                    case 4:
                        switch (c) {
                            case ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> buffer += c;
                            case '-' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.MINUS, buffer));
                                buffer = "";
                                state = 0;
                            }
                            case '/' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.DIV, buffer));
                                buffer = "";
                                state = 0;
                            }
                            case '*' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.MULTI, buffer));
                                buffer = "";
                                state = 0;
                            }
                            case '+' -> {
                                tokens.add(new Token(Type.NUMBER, buffer));
                                buffer = String.valueOf(c);
                                tokens.add(new Token(Type.PLUS, buffer));
                                buffer = "";
                                state = 0;
                            }

                            default -> {
                                System.out.println("Error: Invalid character4 " + c);
                                buffer = "";
                                state = 0;
                                throw new Exception("Invalid character4");
                            }
                        }
                        break;
                    case 5:
                        switch (c) {
                            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                                state = 3;
                                buffer += c;
                            }
                            case ' ' -> buffer += c;
                            default -> {
                                System.out.println("Error: Invalid character5 " + c);
                                buffer = "";
                                state = 0;
                                throw new Exception("Invalid character5");
                            }
                        }
                }
            }
            // add final token
            if (state == 2 || state == 3 || state == 4) {
                tokens.add(new Token(Type.NUMBER, buffer));
                buffer = "";
                state = 0;
            } else {
                System.out.println("Error: Invalid character6 " + buffer);
                buffer = "";
                state = 0;
                throw new Exception("Invalid character6");
            }
            return tokens;
        } catch (Exception e) {
            System.out.println("Error: Invalid character: " + e + " CAUGHT AT  " + buffer);
        }
        state = 0;
        buffer = "";
        return tokens;
    }


}
