public class GetRandom extends InterpreterDataType {
    private Float value;

    public GetRandom(String line) throws Exception {
        try {
            value = (float) (Math.random() * Float.parseFloat(line));
        } catch (Exception e) {
            throw new Exception("Cannot get random from input: " + line + "\n " + e + "\n");
        }
    }

    public String toString(String input) throws Exception {
        // if cant parse then throw exception
        try {
            return Integer.toString((int) (Math.random() * Integer.parseInt(input)));
        } catch (Exception e) {
            throw new Exception("Cannot get random number from input:" + input + "\n " + e + "\n");
        }
    }

    public void fromString(String input) throws Exception {
        try {
            this.value = (float) (Math.random() * Float.parseFloat(input));
        } catch (Exception e) {
            throw new Exception("Cannot get random number from input:" + input + "\n " + e + "\n");
        }
    }

}
