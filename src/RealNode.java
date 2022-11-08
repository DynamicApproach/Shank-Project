public class RealNode extends Node {
    private Float value;

    public RealNode(Float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public String toString() {
        return Float.toString(value);
    }

}
