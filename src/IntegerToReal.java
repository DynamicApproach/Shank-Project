public class IntegerToReal extends InterpreterDataType {
    private Float value;

    public IntegerToReal(String line) {
        super(line);
    }

    public String toString(String input) throws Exception {
        // try to convert string to real if it can return else exception
        try {
            return Float.toString(Float.parseFloat(input));
        } catch (Exception e) {
            throw new Exception("Cannot convert to real from input: " + input + "\n " + e + "\n");
        }
    }

    public void fromString(String input) throws Exception {
        try {
            this.value = Float.parseFloat(input);
        } catch (Exception e) {
            throw new Exception("Cannot convert to real from input: " + input + "\n " + e + "\n");
        }
    }
}

