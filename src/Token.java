/* This file must contain a Token class. The token class is made up of an instance of an enum and a value string. There must be a public accessor
 * for both the enum and the value string; the underlying variables must be private. You may create whatever constructors you choose.
 * The enum must be defined as containing values appropriate to what we will be processing. The definition of the enum should be public, but the instance
 *  inside Token must be private. We will add to this enum in the next several assignments.  You will find it helpful to create an appropriate “ToString” overload. */

enum Type { // Token symbol types
    MULTI, //*
    DIV, // /
    PLUS, //+
    MINUS, //-
    POSNEG, //-+ TODO: Should this be a separate type? Or with the number? - With num will be less parsing total
    MOD, // %
    DECIMAL, // .
    EQUAL, // =
    SPACE, NUMBER, LPAREN, // (
    RPAREN, // )
    LCURLY, // {}
    RCURLY, // {}
    LBRACKET, // []
    RBRACKET, // []
}

public class Token {
    private Type type;
    private String value;

    public Token(Type a) {
        type = a;
        value = null;
    }

    public Token(Type a, String value) {
        type = a;
        this.value = value;
    }

    public boolean sameType(Token a) {
        return type == a.type;
    }


    public Type getType() {
        return this.type;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        if (value == null) {
            return "Type: " + type;
        } else {
            return "Type: " + type + " Value: " + value + "\n";
        }
    }
}
