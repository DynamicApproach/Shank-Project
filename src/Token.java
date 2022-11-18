enum Type { // Token symbol types
    ADD {
        public String toString() {
            return "ADD";
        }
    }, // +
    ASSIGN {
        public String toString() {
            return "ASSIGN";
        }
    } // :=
    , BEGIN {
        public String toString() {
            return "BEGIN";
        }
    }, COLON {
        public String toString() {
            return "COLON";
        }
    }, COMMA {
        public String toString() {
            return "COMMA";
        }
    }, CONSTANT {
        public String toString() {
            return "CONST";
        }
    }, DECIMAL {
        public String toString() {
            return "DECIMAL";
        }
    } // .
    , DEFINE {
        public String toString() {
            return "DEFINE";
        }
    }, DIVIDE {
        public String toString() {
            return "DIVIDE";
        }
    } // /
    , END {
        public String toString() {
            return "END";
        }
    }, ENDLINE {
        public String toString() {
            return "ENDLINE";
        }
    } // \n
    , EQUAL {
        public String toString() {
            return "EQUAL";
        }
    } // =
    , IDENTIFIER {
        public String toString() {
            return "IDENTIFIER";
        }
    }, INTEGER {
        public String toString() {
            return "INTEGER";
        }
    }, LBRACKET {
        public String toString() {
            return "LBRACKET";
        }
    } // [
    , LPAREN {
        public String toString() {
            return "LPAREN";
        }
    } // (
    , MINUS {
        public String toString() {
            return "MINUS";
        }
    } // -
    , MOD {
        public String toString() {
            return "MOD";
        }
    } // %
    , MULTIPLY {
        public String toString() {
            return "MULTIPLY";
        }
    } //*
    , NUMBER {
        public String toString() {
            return "NUMBER";
        }
    } // 0-9
    , RBRACKET {
        public String toString() {
            return "RBRACKET";
        }
    } // ]
    , FLOAT {
        public String toString() {
            return "FLOAT";
        }
    }, RPAREN {
        public String toString() {
            return "RPAREN";
        }
    } // )
    , SEMICOLON {
        public String toString() {
            return "SEMICOLON";
        }
    },

    SPACE {
        public String toString() {
            return "SPACE";
        }
    } //
    , VARIABLES {
        public String toString() {
            return "CONST";
        }
    }, IF {
        public String toString() {
            return "IF";
        }
    }, THEN {
        public String toString() {
            return "THEN";
        }
    }, ELSE {
        public String toString() {
            return "ELSE";
        }
    }, ELSIF {
        public String toString() {
            return "ELSIF";
        }
    }, FOR {
        public String toString() {
            return "FOR";
        }
    }, FROM {
        public String toString() {
            return "FROM";
        }
    }, TO {
        public String toString() {
            return "TO";
        }
    }, WHILE {
        public String toString() {
            return "WHILE";
        }
    }, REPEAT {
        public String toString() {
            return "REPEAT";
        }
    }, UNTIL {
        public String toString() {
            return "UNTIL";
        }
    }, //>, <, >=, <=, =, <>
    GREATER {
        public String toString() {
            return "GREATER";
        }
    }, LESS {
        public String toString() {
            return "LESS";
        }
    }, GREATER_EQUAL {
        public String toString() {
            return "GREATER_EQUAL";
        }
    }, LESS_EQUAL {
        public String toString() {
            return "LESS_EQUAL";
        }
    }, EQUAL_EQUAL {
        public String toString() {
            return "EQUAL_EQUAL";
        }
    }, NOT_EQUAL {
        public String toString() {
            return "NOT_EQUAL";
        }
    }, OR {
        public String toString() {
            return "OR";
        }
    }, VAR {
        public String toString() {
            return "VAR";
        }
    }, DO {
        public String toString() {
            return "DO";
        }
    },
}

/* This file must contain a Token class. The token class is made up of an instance of an enum and a value string. There must be a public accessor
 * for both the enum and the value string; the underlying variables must be private. You may create whatever constructors you choose.
 * The enum must be defined as containing values appropriate to what we will be processing. The definition of the enum should be public, but the instance
 *  inside Token must be private. We will add to this enum in the next several assignments.  You will find it helpful to create an appropriate “ToString” overload. */
public class Token {
    private Type type;
    private String value;

    public Token(Type a, String value) {
        type = a;
        this.value = value;
    }

    public Type getType() {
        return this.type;
    }

    public String getValue() {
        return value.trim();
    }

    public String toString() {
        if (type == null) {
            return "";
        }
        if (value == null) {
            return type + "(" + ")";
        } else if (type == Type.ENDLINE) {
            return type + "(" + value.replace("\n", "\\n") + ")";
        } else {
            return type + "(" + value + ")";
        }
    }
}