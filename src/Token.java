/* This file must contain a Token class. The token class is made up of an instance of an enum and a value string. There must be a public accessor
* for both the enum and the value string; the underlying variables must be private. You may create whatever constructors you choose.
* The enum must be defined as containing values appropriate to what we will be processing. The definition of the enum should be public, but the instance
*  inside Token must be private. We will add to this enum in the next several assignments.  You will find it helpful to create an appropriate “ToString” overload. */
enum Type { // Keeps track of current state
    ONE, //1
    TWO, //2
    THREE, //3
    FOUR, //4
    FIVE, //5
    SIX, //6
    SEVEN, //7
}
enum Symbol{ // Token symbol types
    OP, //+-/*
    NEGPOS, //-+
    MULT, //*
    DIV, // /
    MOD, // %
    DECIMAL, // =
    SPACE,
    NUMBER,
    LPAREN, // (
    RPAREN, // )
}
public class Token {
    private Symbol type;
    private String value;
    public Token(Symbol a){
        type  = a;
        value = null;
    }
    public Token(Symbol a, String value){
        type =a;
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    public Symbol getSymbol() {
        return this.type;
    }
    public String toString() {
        if(value == null){
            return "Type: " + type;
        }
        else{
            return "Type: " + type + " Value: " + value+"\n";
        }
    }
}
