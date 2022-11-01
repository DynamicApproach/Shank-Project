public class RealToInteger extends InterpreterDataType {
    private int value;

    public RealToInteger(String line) throws Exception {
        try {
            value = Integer.parseInt(line);
        } catch (Exception e) {
            throw new Exception("Cannot convert to integer from input: " + line + "\n " + e + "\n");
        }
    }

    public String toString(String input) {
        try {
            return Integer.toString(Integer.parseInt(input));
        } catch (Exception e) {
            return "Could not convert to integer from input:" + input + "\n " + e + "\n";
        }
    }

    public void fromString(String input) {
        this.value = Integer.parseInt(input);
    }
}
