package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final List<String> programTextLines;
    private final Map<TokenType, List<Token>> tokensMap;
    private final List<TokenType> tokenTypes;
    private final List<String> others;

    /**
     * @param filePath
     */
    public Lexer(String filePath) {
        this.programTextLines = fileReader(filePath);
        tokensMap = new LinkedHashMap<>();
        others = new ArrayList<>();


        // initialize token type list to iterate from
        // rather than if else structure
        tokenTypes = Arrays.asList(TokenType.values());
    }

    private List<String> fileReader(String filename) {
        List<String> fileLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                fileLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileLines;
    }


    public void tokenize() {
        //this should return the whole text in lines into a list without comments
        //and separated by fields

        for (String line : this.programTextLines) {
            //this removes all the separators
            String[] fields = line.split(TokenType.SEPARATOR.pattern);

            for (int i = 0; i < fields.length; ++i) {
                fields[i] = fields[i].trim();
                match(fields[i]);
            }
        }
        printTokenMap();
    }

    private void addToken(TokenType type, String tokenData) {
        tokensMap.computeIfAbsent(type, k -> new ArrayList<>());
        tokensMap.get(type).add(new Token(type, tokenData));
    }

    private void match(String field) {
        for (TokenType type : this.tokenTypes) {
            field = stringMatcher(type, field);
        }

        if (field.length() > 0)
            this.others.add(field);
    }

    /**
     * @param type Token type to identify from the line
     * @param line The line taken from the source file
     * @return The line without the tokens from the TokenType
     * Observation: The tokens found(if there are any) will be added to the tokensMap
     */
    private String stringMatcher(TokenType type, String line) {
        Pattern pattern = Pattern.compile(type.pattern);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            if (type.equals(TokenType.BRACKETS)) {

            } else {
                addToken(type, line.substring(matcher.start(), matcher.end()));
                return line.replace(line.substring(matcher.start(), matcher.end()), "");
            }
        } else {
            return line;
        }
        return line;
    }

    private void printTokenMap() {
        for (TokenType tokenType : tokensMap.keySet()) {
            System.out.printf("Token Type: %s -> %s\n", tokenType, tokensMap.get(tokenType));
        }
        System.out.printf("Non Tokens: %s", others);
    }
}
