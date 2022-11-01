public class SquareRoot extends InterpreterDataType {
    private Float value;

    public SquareRoot(String line) {
        try {
            value = (float) Math.sqrt(Float.parseFloat(line));
        } catch (Exception e) {
            System.out.println("Cannot get square root from input: " + line + "\n " + e + "\n");
        }
    }

    public String toString(String input) throws Exception {
        // check if input is able to be square rooted if it can return else exception
        try {
            return Float.toString((float) Math.sqrt(Double.parseDouble(input)));
        } catch (Exception e) {
            throw new Exception("Cannot square root input:" + input + "\n " + e + "\n");
        }
    }

    public void fromString(String input) throws Exception {
        // try to square root if it can return else exception
        try {
            this.value = (float) Math.sqrt(Float.parseFloat(input));
        } catch (Exception e) {
            throw new Exception("Cannot square root input:" + input + "\n " + e + "\n");
        }
    }
}

