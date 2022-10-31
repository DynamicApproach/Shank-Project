//IntDataType
//This class is used to create a data type for integers
public class IntDataType extends InterpreterDataType {
    //The int and float versions have a Value (of the appropriate type) and should implement FromString() and ToString() – we will use these in our read and write functions.
    private int value;


    @Override
    public String toString(String input) {
        return (Integer.toString(value));
    }

    @Override
    public void fromString(String input) {
        value = Integer.parseInt(input);
    }

}