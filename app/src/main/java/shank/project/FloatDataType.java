package shank.project;
//IntDataType
//This class is used to create a data type for integers
public class FloatDataType extends InterpreterDataType {
    //The int and float versions have a Value (of the appropriate type) and should implement FromString() and ToString() – we will use these in our read and write functions.
    private float value;

    public FloatDataType(String value) {
        this.value = Float.parseFloat(value);
    }

    public FloatDataType(float val) {
        this.value = val;
    }

    public String toString() {
        return (Float.toString(value));
    }

    @Override
    public void fromString(String input) {
        value = Float.parseFloat(input);
    }

}