package shank.project;
public class IntegerNode extends Node {
    private int value;

    public IntegerNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return Integer.toString(value);
        // if (!"null".equals(Integer.toString(value)))  ____  else return "0";
    }
}