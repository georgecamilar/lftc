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


        lexer.writeToFile(lexer.getIdentifierTokens(), "output/ts_identifier.txt");
        lexer.writeToFile(lexer.getConstantTokens(), "output/ts_constants.txt");

        lexer.writeFip("output/fip.txt");
    }
}
