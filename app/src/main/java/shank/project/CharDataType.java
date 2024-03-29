package shank.project;
public class CharDataType extends InterpreterDataType {
    private char value;

    public CharDataType(char value) {
        this.value = value;
    }

    public String toString() {
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
