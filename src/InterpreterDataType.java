public abstract class InterpreterDataType {

    public abstract String toString(String input) throws Exception;

    public abstract void fromString(String input) throws Exception;
    // TODO: add type to send to floatdatatype/intdata?

}
