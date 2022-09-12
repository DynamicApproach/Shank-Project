import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * must ensure that there is one and only one argument (args). If there are none or more than 1,
 *  it must print an appropriate error message and exit. That one argument will be considered as a filename. Your main must then use File.ReadAllLines
 * to read all the lines from the file denoted by filename.  Your main must instantiate one instance of your Lexer class (to be defined below). You must
 *  parse all lines using the lex method of the Lexer class. If lex throws an exception, you must catch the exception, print that there was an exception. You must
 *  then print each token out (this is a temporary step to show that it works) once the lexing is complete.
 */
public class Shank {
    public static void main(String[] args) {
        ArrayList<Token> tokens = new ArrayList<>(1000);
        if (args.length == 1) {
            try {
                Lexer lexer = new Lexer();
                // TODO: Implement Parser in Shank.java
                /*String strings = Files.readAllLines(Paths.get(args[0])).toString();
                strings = strings.substring(1, strings.length() - 1);*/
                // Currently used option of list
                List<String> text = Files.readAllLines(Paths.get(args[0]));
                text.replaceAll(s -> s + "\n"); // Add a line break to the end of each line

                for (String line : text) {
                    // System.out.println(line); // FOR DEBUG OF INPUTS
                    tokens = (lexer.Lex(line));
                }
                for (Token token : tokens) {
                    if (token != null) {
                        System.out.println(token);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        } else {
            System.out.println("Usage: java Shank <filename>");
            System.exit(1);
        }
    }
}