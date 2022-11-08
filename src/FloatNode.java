public class FloatNode extends Node {
    private Float value;

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