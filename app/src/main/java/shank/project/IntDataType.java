//IntDataType
//This class is used to create a data type for integers
package shank.project;

public class IntDataType extends InterpreterDataType {
    //The int and float versions have a Value (of the appropriate type) and should implement FromString() and ToString() – we will use these in our read and write functions.
    private int value;

    public IntDataType(String value) {
        this.value = Integer.parseInt(value);
    }

    public IntDataType(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public void fromString(String input) {
        value = Integer.parseInt(input);
    }

    public int getValue() {
        return value;
    }
}

// go to   function hashmap and cast as a function node - get its name and parameters and if the amount of parameters in the functioncall match the function def then it's okay