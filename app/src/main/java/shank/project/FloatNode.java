package shank.project;
public class FloatNode extends Node {
    private float value;

    public FloatNode(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public String toString() {
        return Float.toString(value);
    }
}