import common.Lexer;
import common.Token;

import java.util.Map;

public class Start {

    public static void printMap(Map<String, Token> map) {
        for (String key : map.keySet()) {
            System.out.println("key : " + key + " value : " + map.get(key));
        }
    }

    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer();
        lexer.parse();

        lexer.writeFip("output/fip.txt");

        System.out.println("-------Identifiers-------");
        lexer.getIdentifiers().leftRootRightPrint();

        System.out.println("-------Constants-------");
        lexer.getConstants().leftRootRightPrint();
    }
}
