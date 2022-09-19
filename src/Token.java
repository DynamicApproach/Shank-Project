enum Type { // Token symbol types
    ADD {
        public String toString() {
            return "+";
        }
    } // +
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
            return ".";
        }
    } // .
    , DEFINE {
        public String toString() {
            return "DEFINE";
        }
    }, DIVIDE {
        public String toString() {
            return "/";
        }
    } // /
    , END {
        public String toString() {
            return "END";
        }
    }, EQUAL {
        public String toString() {
            return "=";
        }
    } // =
    , IDENTIFIER {
        public String toString() {
            return "IDENTIFIER";
        }
    }, INTEGER {
        public String toString() {
            return "INT";
        }
    }, LBRACKET {
        public String toString() {
            return "[";
        }
    } // [
    , LPAREN {
        public String toString() {
            return "(";
        }
    } // (
    , MINUS {
        public String toString() {
            return "-";
        }
    } // -
    , MOD {
        public String toString() {
            return "%";
        }
    } // %
    , MULTIPLY {
        public String toString() {
            return "*";
        }
    } //*
    , NEWLINE {
        public String toString() {
            return "\n";
        }
    } // \n
    , NUMBER {
        public String toString() {
            return "NUMBER";
        }
    } // 0-9
    , RBRACKET {
        public String toString() {
            return "]";
        }
    } // ]
    , REAL {
        public String toString() {
            return "REAL";
        }
    }, RPAREN {
        public String toString() {
            return ")";
        }
    } // )
    , SEMICOLON {
        public String toString() {
            return "SEMICOLON";
        }
    },

    SPACE {
        public String toString() {
            return " ";
        }
    } //
    , VARIABLES {
        public String toString() {
            return "CONST";
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
            return "Type:   " + type + " Value:   " + value;
        }
    }
}