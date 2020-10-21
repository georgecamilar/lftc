import common.Lexer;
import common.LexicalAtom;

import java.util.Map;

public class Start {

    public static void printMap(Map<String, LexicalAtom> map) {
        for (String key : map.keySet()) {
            System.out.println("key : " + key + " value : " + map.get(key));
        }
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        lexer.parseSourceFile("source2.c");


        printMap(lexer.getAtoms());
        lexer.writeToFile("atoms.txt", lexer.getAtoms());

    }
}
