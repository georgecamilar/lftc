import common.Lexer;
import common.LexicalAtom;
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

        System.out.println("-----Atoms------");
        printMap(lexer.getAtomsTokens());
        System.out.println("-----Constants------");
        printMap(lexer.getConstantTokens());
        System.out.println("-----Identifiers------");
        printMap(lexer.getIdentifierTokens());
    }
}
