import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
* must ensure that there is one and only one argument (args). If there are none or more than 1,
*  it must print an appropriate error message and exit. That one argument will be considered as a filename. Your main must then use File.ReadAllLines
* to read all of the lines from the file denoted by filename.  Your main must instantiate one instance of your Lexer class (to be defined below). You must
*  parse all lines using the lex method of the Lexer class. If lex throws an exception, you must catch the exception, print that there was an exception. You must
*  then print each token out (this is a temporary step to show that it works) once the lexing is complete.
*/
public class Shank {
    public static void main(String[] args) {
        // main only takes 1 arg
        // arg is filename

        ArrayList<Token> tokens = new ArrayList<>(1000);
        if (args.length == 1) {
               try {
                   Lexer lexer = new Lexer();
                   List<String> text = Files.readAllLines(Paths.get(args[0]));
                   for (String line : text) {
                       System.out.println(line);
                       tokens = lexer.Lex(line);
                       System.out.println(tokens);
                   }
               } catch (Exception e) {
                   System.out.println("Error: " + e);
               }
        }
        else{
               System.out.println("Usage: java Shank <filename>");
               System.exit(1);
        }
        // reads all lines
        // sends to lex method
    }


}