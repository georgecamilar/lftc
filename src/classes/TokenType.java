package classes;

public enum TokenType {
    // Tokens cannot have underscores
    NUMBER("number", "-?[0-9]+"),
    OPERATOR("operator", "[*|/|+|-|=|<|>|>=|<=|==|!=]"),
    IDENTIFIER("identifier", "(?:int|float|char|double)"),
    SEPARATOR("separator", "[,| ]+"),
    BRACKETS("brackets", "[,| |\\(|\\)|\\]|\\[]+"),
    KEYWORDS("keywords", "(?:for|while|if)"),
    SPECIALCHARACTERS("special characters", "(^[\\)][0-9a-zA-Z, ]*)\\)");


    public final String pattern;
    public final String name;

    private TokenType(String name, String pattern) {
        this.pattern = pattern;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getPattern(){
        return pattern;
    }
}
