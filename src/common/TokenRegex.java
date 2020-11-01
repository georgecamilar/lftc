package common;

public enum TokenRegex {
    //type of instructions
    ARRAY("int[]"),
    EXPRESSION("ID | CONSTANT | EXPRESSION OPERATOR EXPRESSION"),
    RELATION("(?:(\\!\\=|\\=\\=|\\>|\\<))"),
    OPERATION("\\+|\\-|\\/|\\%"),
    CONDITION(EXPRESSION.getPattern()),
    IF("?:if( " + CONDITION.getPattern() + " )"),
    
    ;

    private final String pattern;

    TokenRegex(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
