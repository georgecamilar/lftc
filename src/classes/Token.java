package classes;

public class Token {
    public TokenType type;
    public String data;



    public Token(TokenType tokenType, String data) {
        this.data = data;
        this.type = tokenType;
    }
}
