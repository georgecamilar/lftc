package classes;

public enum TokenType {
    // Tokens cannot have underscores
    NUMBER("-?[0-9]+"),
    OPERATOR("[*|/|+|-|=|<|>|>=|<=|==|!=]"),
    WHITESPACE("[ \t\f\r\n]+"),
    IDENTIFIER("(?:int|float)"),
    SEPARATOR("[,| ]+"),
    KEYWORDS("(?:for|while|if)"),
    SPECIALCHARACTERS("(^[\\)][0-9a-zA-Z, ]*)\\)");

    public final String pattern;

    private TokenType(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return pattern;
    }
}
