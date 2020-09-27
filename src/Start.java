import classes.Lexer;

public class Start {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("source.c");
       lexer.tokenize();
    }
}
