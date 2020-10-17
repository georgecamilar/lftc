import common.Lexer;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Start {

    public static void printMap(Map<String, Integer> map) {
        for (String key : map.keySet()) {
            if (map.get(key) == 1) {
                System.out.println("key : " + key + " value : identifier");
            } else if (map.get(key) == 2) {
                System.out.println("key : " + key + " value : constant");
            } else {
                System.out.println("key : " + key + " value : " + map.get(key));
            }
        }
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        lexer.parseSourceFile("source2.c");

        for(String field: lexer.getFip()){
            System.out.println(field + "  |  "  + lexer.getAtoms().get(field));
        }

        /*System.out.println("Atoms: ");
        printMap(lexer.getAtoms());
        System.out.println("\n\nIdentifiers: \n\n");
        printMap(lexer.getIdentifiersTable());
        System.out.println("\n\nConstants: \n\n");
        printMap(lexer.getConstantsTable());
        lexer.writeToFile("atoms.txt", lexer.getAtoms());
        lexer.writeToFile("identifiers.txt", lexer.getIdentifiersTable());
        lexer.writeToFile("constants.txt", lexer.getConstantsTable());*/
    }
}
