import classes.Lexer;

public class Start {
    public static void main(String[] args) {
        /*System.out.println("--------Lexer----------");
        String a = "Sentence is cool, but not , , , , that cool";

        for (String word : a.split("[,| ]+")) {
            System.out.print(word + " ");
        }*/

        Lexer lexer = new Lexer("source.c");

        //lexer.tokenize();

    }
}
