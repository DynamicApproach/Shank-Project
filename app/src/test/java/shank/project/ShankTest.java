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

class ShankTest {


    @Test
    public void testWithFile6() {
        String expectedOutput = "2"; // update this with the expected output 
        Path filePath = Paths.get("src", "test", "resources", "test6.shank");
        String actualOutput = runShankProgram(filePath.toString(), "1\n1\n");
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
