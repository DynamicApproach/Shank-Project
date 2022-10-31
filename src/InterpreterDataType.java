public abstract class InterpreterDataType {
    private String input;

    public InterpreterDataType(String line) {
        this.input = line;
    }

    public abstract String toString(String input);

    public abstract void fromString(String input);

}
