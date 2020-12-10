import common.Lexer;


public class Start {

    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer();
        lexer.parse();

        lexer.writeFip("output/fip.txt");

        System.out.println("-------Identifiers-------");
        lexer.getIdentifiers().leftRootRightPrint();
        lexer.writeBstToFile(lexer.getIdentifiers(), "output/ts_identifier.txt");

        System.out.println("-------Constants-------");
        lexer.getConstants().leftRootRightPrint();
        lexer.writeBstToFile(lexer.getConstants(), "output/ts_constants.txt");
    }
}
