package common;

public class Token {
    private Integer languageTokenIndex;
    private Integer identifierOrConstantTableIndex;

    public Token(Integer languageTokenIndex, Integer identifierOrConstantTableIndex) {
        this.languageTokenIndex = languageTokenIndex;
        this.identifierOrConstantTableIndex = identifierOrConstantTableIndex;
    }

    public Integer getLanguageTokenIndex() {
        return languageTokenIndex;
    }

    public void setLanguageTokenIndex(Integer languageTokenIndex) {
        this.languageTokenIndex = languageTokenIndex;
    }

    public Integer getIdentifierOrConstantTableIndex() {
        return identifierOrConstantTableIndex;
    }

    public void setIdentifierOrConstantTableIndex(Integer identifierOrConstantTableIndex) {
        this.identifierOrConstantTableIndex = identifierOrConstantTableIndex;
    }

    @Override
    public String toString() {
        if(this.identifierOrConstantTableIndex == -1){
            return this.languageTokenIndex + " - none";
        }
        return this.languageTokenIndex + " - " + this.identifierOrConstantTableIndex;
    }

    public Token(Integer languageTokenIndex) {
        this.identifierOrConstantTableIndex = -1;
        this.languageTokenIndex = languageTokenIndex;
    }
}
