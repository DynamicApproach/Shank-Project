package shank.project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import shank.project.Shank;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.nio.file.Path;

class AppTest {

    @Test
    void testWithFileFail() {
        String expectedOutput = "FunctionAST{name='add\n, parameters=[\nx: = 0\n of type: INTEGER\n is constant: true\n, \ny: = 0\n of type: INTEGER\n is constant: true\n, \nsum: = 0\n of type: INTEGER\n is constant: false\n]\n, variables=[\nyMinusOne: = 0\n of type: INTEGER\n is constant: false\n, \nxPlusOne: = 0\n of type: INTEGER\n is constant: false\n, \nnewSum: = 0\n of type: INTEGER\n is constant: false\n]\n, body=[y EQUAL 0 ASSIGN: xPlusOne Then: (x ADD 1)ASSIGN: yMinusOne Then: (y MINUS 1)FUNCTIONCALL: add Then: [const: xPlusOne : null, const: yMinusOne : null, var: newSum : null]ASSIGN: sum Then: newSum]\n}";

        Path filePath = Paths.get("src", "test", "resources", "test4.shank");
        String actualOutput = runShankProgram(filePath.toString(), "4\n10\n");
        assertEquals(expectedOutput, actualOutput);
    }

    // A helper method that runs your programming language application and captures the output.
    private String runShankProgram(String filePath, String input) {
    // Setup streams to capture output and provide input
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream oldOut = System.out;
    InputStream oldIn = System.in;
    
    // Prepare input stream
    InputStream is = new ByteArrayInputStream(input.getBytes());

    // Redirect System.out and System.in
    System.setOut(ps);
    System.setIn(is);
    
    // Prepare args and call Shank.main()
    String[] args = { filePath };
    Shank.main(args);

    // Restore original System.out and System.in
    System.out.flush();
    System.setOut(oldOut);
    System.setIn(oldIn);

    return baos.toString();  // Return the captured output.
}

}
