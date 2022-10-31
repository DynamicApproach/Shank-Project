public class RealToInteger extends InterpreterDataType {
    private int value;

    public RealToInteger(String line) {
        super(line);
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
