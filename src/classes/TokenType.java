package classes;

public enum TokenType {
    // Tokens cannot have underscores
    NUMBER("-?[0-9]+"),
    BINARYOP("[*|/|+|-]"),
    WHITESPACE("[ \t\f\r\n]+"),
    DATATYPE("[int|float]"),
    SEPARATOR("[,| ]+");
    public final String pattern;

    private TokenType(String pattern) {
        this.pattern = pattern;
    }
}
