enum Type { // Token symbol types
    identifier, define, integer, real, begin, end, semicolon, colon, comma, variables, constants,
    MULTIPLY {//*

        public String toString() {
            return "*";
        }
    },
    NEWLINE {// \n

        public String toString() {
            return "\n";
        }
    },
    DIVIDE {// /

        public String toString() {
            return "/";
        }
    },
    ADD {// +

        public String toString() {
            return "+";
        }
    },
    MINUS {// -

        public String toString() {
            return "-";
        }
    },
    POSNEG,
    MOD {// %

        public String toString() {
            return "%";
        }
    },
    DECIMAL {// .

        public String toString() {
            return ".";
        }
    },
    EQUAL {// =

        public String toString() {
            return "=";
        }
    },
    SPACE {//

        public String toString() {
            return " ";
        }
    },
    NUMBER {// 0-9

        public String toString() {
            return "NUMBER";
        }
    }, LPAREN {// (

        public String toString() {
            return "(";
        }
    },
    RPAREN {// )

        public String toString() {
            return ")";
        }
    },

    LBRACKET {// [

        public String toString() {
            return "[";
        }
    },
    RBRACKET {// ]

        public String toString() {
            return "]";
        }
    }
}

/* This file must contain a Token class. The token class is made up of an instance of an enum and a value string. There must be a public accessor
 * for both the enum and the value string; the underlying variables must be private. You may create whatever constructors you choose.
 * The enum must be defined as containing values appropriate to what we will be processing. The definition of the enum should be public, but the instance
 *  inside Token must be private. We will add to this enum in the next several assignments.  You will find it helpful to create an appropriate “ToString” overload. */

public class Token {
    private Type type;
    private String value;

    @SuppressWarnings("unused")
    public Token(Type a) {
        type = a;
        value = null;
    }

    public Token(Type a, String value) {
        type = a;
        this.value = value;
    }

    @SuppressWarnings("unused")
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