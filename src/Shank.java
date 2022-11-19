import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/*
 * must ensure that there is one and only one argument (args). If there are none or more than 1,
 *  it must print an appropriate error message and exit. That one argument will be considered as a filename. Your main must then use File.ReadAllLines
 * to read all the lines from the file denoted by filename.  Your main must instantiate one instance of your Lexer class (to be defined below). You must
 *  parse all lines using the lex method of the Lexer class. If lex throws an exception, you must catch the exception, print that there was an exception. You must
 *  then print each token out (this is a temporary step to show that it works) once the lexing is complete.
 */

public class Shank {
    public static HashMap<String, CallableNode> functionNames = new HashMap<>();

    public static void main(String[] args) {
        // start time stamp
        long startTime = System.currentTimeMillis();
        // TODO: add string functions

        functionNames.put("Read", new Read("Read", new ArrayList<>(), false));
        functionNames.put("Write", new Write("Write", new ArrayList<>(), false));
        functionNames.put("SquareRoot", new SquareRoot("SquareRoot", new ArrayList<>(), false));
        functionNames.put("GetRandom", new GetRandom("GetRandom", new ArrayList<>(), false));
        functionNames.put("InttoReal", new IntegerToReal("InttoReal", new ArrayList<>(), false));
        functionNames.put("RealtoInt", new RealToInteger("RealtoInt", new ArrayList<>(), false));
        ArrayList<Token> tokens = new ArrayList<>(10000);
        if (args.length == 1) {
            try {
                Lexer lexer = new Lexer();
                lexer.setupReservedWords();
                List<String> text = Files.readAllLines(Paths.get(args[0]));
                text.replaceAll(s -> s + "\n");
                for (String line : text) {
                    // System.out.println(line); // FOR DEBUG OF INPUTS
                    tokens = (lexer.Lex(line));
                    /*
                    // add newline token for the end of each line
                    tokens.add(new Token(Type.ENDLINE, "\n"));
                    */

                }
                for (Token token : tokens) {
                    if (token != null) {
                        System.out.print(token);
                        if (token.getType() == Type.ENDLINE) {
                            System.out.println("\n");
                        }
                    } else {
                        System.err.println("null token");
                    }
                }
                System.out.println("\n \n \n Parsing:\n ");

                //create new parser and parse tokens

                Parser parsed = new Parser(tokens);

                // for each line of tokens, parse it
                ArrayList<FunctionNode> node = parsed.functionDefinition();
                // add function node to hashmap
                for (FunctionNode functionNode : node) {
                    functionNames.put(functionNode.getName(), functionNode);
                }


                // print out the function
                System.out.println(node);
                System.out.println("Parsing complete");

                // print the tree
                //functiondef interpret

                System.out.println("Interpreting: ");
                Interpreter interpreter = new Interpreter(functionNames);
                ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                ArrayList<ParameterNode> parameterNodes = new ArrayList<>();
                Interpreter.InterpretFunction(new FunctionCallNode("start", parameterNodes, false), dataTypes);
                //interpreter.printTree(tree);


                //float fintree = interpreter.Resolve(tree);
                System.out.println("Interpreting complete");

                //System.out.println("Result: " + fintree);
                //functiondef parse


            } catch (Exception e) {
                System.err.println("Error: " + e);
            }
        } else {
            // print args
            System.out.println(Arrays.toString(args));
            System.out.println("Usage: java Shank <filename>");
            System.exit(1);
        }
        // end time stamp
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("Time: " + duration + " milliseconds");
    }
}