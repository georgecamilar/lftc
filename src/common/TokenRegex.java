package common;

public enum TokenRegex {
    //type of instructions
    IDENTIFIER("^([_a-zA-Z])([_a-zA-Z0-9])*")
    ;

    private final String pattern;

    TokenRegex(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
