package common;

public class LexicalAtom {
    private Integer atomCode;
    private Integer symbolTableCode;
    private String status;


    public LexicalAtom(Integer atomCode) {
        this.atomCode = atomCode;
        this.symbolTableCode = 0;
    }

    public LexicalAtom(Integer atomCode, Integer symbolTableCode, String status) {
        this.atomCode = atomCode;
        this.symbolTableCode = symbolTableCode;
        this.status = status;
    }

    @Override
    public String toString() {
        if (symbolTableCode == 0)
            return this.atomCode + " | - ";
        return this.atomCode + " | " + status + " : " + this.symbolTableCode;
    }
}
