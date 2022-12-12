public class BooleanDataType extends InterpreterDataType {
    private boolean value;

    public BooleanDataType(boolean value) {
        this.value = value;
    }

    public BooleanDataType() {
        this.value = false;
    }

    public BooleanDataType(String readLine) {
        this.value = Boolean.parseBoolean(readLine);
    }

    public String toString() {
        return Boolean.toString(value);
    }

    public String toString(String input) throws Exception {
        return Boolean.toString(value);
    }


    public void fromString(String input) {
        this.value = Boolean.parseBoolean(input);
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
