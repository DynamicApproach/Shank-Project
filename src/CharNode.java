public class CharNode extends Node {
    private char value;

    public CharNode(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public String toString() {
        return Character.toString(value);
    }

}
