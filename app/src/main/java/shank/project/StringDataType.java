public class StringDataType extends InterpreterDataType {
    private String value;

    public StringDataType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public void fromString(String input) {
        this.value = input;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
