public class CharDataType extends InterpreterDataType {
    private char value;

    public CharDataType(char value) {
        this.value = value;
    }

    public CharDataType() {
        this.value = ' ';
    }

    public String toString() {
        return Character.toString(value);
    }

    public String toString(String input) throws Exception {
        return Character.toString(value);
    }

    public void fromString(String input) {
        this.value = input.charAt(0);
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
}
