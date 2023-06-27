import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import java.util.Scanner;
/*
 * must ensure that there is one and only one argument (args). If there are none or more than 1,
 *  it must print an appropriate error message and exit. That one argument will be considered as a filename. Your main must then use File.ReadAllLines
 * to read all the lines from the file denoted by filename.  Your main must instantiate one instance of your Lexer class (to be defined below). You must
 *  parse all lines using the lex method of the Lexer class. If lex throws an exception, you must catch the exception, print that there was an exception. You must
 *  then print each token out (this is a temporary step to show that it works) once the lexing is complete.
 */

public class Shank {
    public static HashMap<String, CallableNode> functionNames = new HashMap<>();
    public static Scanner scanner;
    public static void main(String[] args) {
        // start time stamp
        long startTime = System.currentTimeMillis();
        // read can accept one argument
        functionNames.put("read", new Read("read", new ArrayList<VariableNode>(), true));
        functionNames.put("write", new Write("write", new ArrayList<VariableNode>(), true));
        functionNames.put("left", new Left("left",new ArrayList<VariableNode>(), false));
        functionNames.put("right", new Right("right", new ArrayList<VariableNode>(), false));
        functionNames.put("substring", new Substring("substring", new ArrayList<VariableNode>(), false));
        functionNames.put("squareroot", new SquareRoot("squareroot",new ArrayList<VariableNode>(), false));
        functionNames.put("getrandom", new GetRandom("getrandom", new ArrayList<VariableNode>(), false));
        functionNames.put("inttoreal", new IntegerToReal("inttoreal", new ArrayList<VariableNode>(), false));
        functionNames.put("realtoint", new RealToInteger("realtoint", new ArrayList<VariableNode>(), false));
        if(scanner == null)
            scanner = new Scanner(System.in);
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
                }
                //create new parser and parse tokens
                Parser parsed = new Parser(tokens);
                // for each line of tokens, parse it
                ArrayList<FunctionNode> node = parsed.functionDefinition();
                // add function node to hashmap
                for (FunctionNode functionNode : node) {
                    functionNames.put(functionNode.getName(), functionNode);
                    System.out.println(functionNode);
                }
                //functiondef interpret
                Interpreter interpreter = new Interpreter(functionNames);
                ArrayList<InterpreterDataType> dataTypes = new ArrayList<>();
                ArrayList<ParameterNode> parameterNodes = new ArrayList<>();
                // create new function call node for each funct
                ArrayList<FunctionCallNode> functionCallNodes = new ArrayList<>();
                for (FunctionNode functionNode : node) {
                    FunctionCallNode functionCallNode = new FunctionCallNode(functionNode.getName(), parameterNodes, false);
                    functionCallNodes.add(functionCallNode);
                }
                // find the start function node
                FunctionCallNode startFunction = null;
                for (FunctionCallNode functionCallNode : functionCallNodes) {
                    if ("start".equalsIgnoreCase(functionCallNode.getName())) {
                        startFunction = functionCallNode;
                    }
                }
                assert startFunction != null;
                Interpreter.InterpretFunction(startFunction, dataTypes);
                System.out.println("Interpreting complete");
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
        scanner.close();
    }
}