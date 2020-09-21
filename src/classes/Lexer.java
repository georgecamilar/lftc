package classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private List<String> programTextLines;
    private Map<String, List<String>> tokensMap;

    public Lexer(String filePath) {
        this.programTextLines = fileReader(filePath);

        tokensMap = new LinkedHashMap<>();

        tokensMap.put("identifier", new ArrayList<>());
        tokensMap.put("keyword", new ArrayList<>());
        tokensMap.put("operator", new ArrayList<>());
    }

    private List<String> fileReader(String filename) {

        System.out.println("________________________________");
        System.out.println("________This is the file________");
        System.out.println("________________________________");
        List<String> fileLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                fileLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("________________________________");
        System.out.println("________________________________");
        return fileLines;
    }


    public void tokenize() {
        //this should return the whole text in lines into a list without comments
        //and separated by fields

        for (String line : this.programTextLines) {
            //this removes all the separators
            String[] fields = line.split(TokenType.SEPARATOR.pattern);
            //removing whitespaces and
            for (int i = 0; i < fields.length; ++i) {
                fields[i] = fields[i].trim();
            }

            //check for token types in the TokenType enum
            for (String field : fields) {
                if (field.matches(TokenType.IDENTIFIER.toString())) {
                    tokensMap.get("identifier").add(field);
                } else if (field.matches(TokenType.KEYWORDS.toString())) {
                    tokensMap.get("keyword").add(field);
                } else if (field.matches(TokenType.OPERATOR.toString())) {
                    tokensMap.get("operator").add(field);
                }
            }

            for (String word : fields) {
                System.out.println(word);
            }

        }
        System.out.println("[INFO]Token Output: " + tokensMap);
    }
}
