public class BooleanNode extends Node {
    private boolean value;

    public BooleanNode(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return Boolean.toString(value);
    }

}
