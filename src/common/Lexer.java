package common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class Lexer {
    private final Map<String, Integer> languageTokens;
    private final Map<String, Token> atomsTokens;
    private final Map<String, Token> identifierTokens;
    private final Map<String, Token> constantTokens;


    public Lexer() {
        atomsTokens = new LinkedHashMap<>();
        identifierTokens = new LinkedHashMap<>();
        constantTokens = new LinkedHashMap<>();


        languageTokens = readTokens("tokens.txt");
    }

    public void parse() throws Exception {
        List<String> sourceFileLines = Files.readAllLines(Paths.get("source2.c"));
        int lineCounter = 0;
        for (String line : sourceFileLines) {
            if (line.equals(""))
                continue;

            String[] fields = line.split(" ");
            lineCounter++;
            for (int i = 0; i < fields.length; i++) {
                if (languageTokens.containsKey(fields[i])) {
                    atomsTokens.put(fields[i], new Token(languageTokens.get(fields[i])));
                } else {
                    //identifier or constant
                    if (identifierTokens.containsKey(fields[i])) {
                        atomsTokens.put(fields[i], new Token(1, generateValue(identifierTokens)));
                    } else if (constantTokens.containsKey(fields[i])) {
                        atomsTokens.put(fields[i], constantTokens.get(fields[i]));
                    } else {
                        //not detected yet;

                        if (i > 0 && (fields[i - 1].equalsIgnoreCase("int") || fields[i - 1].equalsIgnoreCase("bool"))) {
                            if (fields[i].length() > 256) {
                                throw new Exception("Syntax error at line " + lineCounter);
                            }

                            identifierTokens.put(fields[i], new Token(1, generateValue(identifierTokens)));
                            //sort it

                        } else {
                            if (isConstant(fields[i])) {
                                int endOfStringIndex;
                                if ((endOfStringIndex = isStringConstant(fields, i)) != i) {

                                    int currentFieldIndex = i;
                                    StringBuilder builder = new StringBuilder();

                                    while (currentFieldIndex <= endOfStringIndex) {
                                        builder.append(fields[currentFieldIndex]).append(" ");
                                        currentFieldIndex++;
                                    }

                                    String result = builder.toString();
                                    constantTokens.put(result, new Token(2, generateValue(constantTokens)));

                                    i = currentFieldIndex;
                                }
                            } else {
                                throw new Exception("Syntax error at line " + lineCounter);
                            }
                        }
                    }
                }
            }
        }

        this.writeToFile(this.atomsTokens, "output/atoms.txt");
    }


    private boolean isConditionalStatement(String[] fields) {
        return (fields[0].equals("while") || fields[0].equals("if")) &&
                fields[1].equals("(") &&
                identifierExists(fields[2]) &&
                identifierExists(fields[4]) &&
                isSpecialSign(fields[3]) &&
                fields[5].equals(")");
    }

    private boolean isSpecialSign(String atom) {
        return identifierTokens.containsKey(atom);
    }

    private boolean identifierExists(String variable) {
        return identifierTokens.containsKey(variable);
    }

    private int isStringConstant(String[] fields, Integer i) {
        boolean stringStatus = fields[i].contains("\"");
        if (!stringStatus) {
            return i;
        }
        int counter = i;
        if (fields[counter].lastIndexOf("\"") == fields[counter].length() - 1) {
            return counter;
        }

        while (stringStatus) {
            if (fields[counter].lastIndexOf("\"") == fields[counter].length() - 1) {
                stringStatus = false;
            } else {
                counter++;
            }
        }

        return counter;
    }

    private boolean isConstant(String constant) {
        return constant.contains("\"");
    }

    private Integer generateValue(Map<String, Token> map) {
        int number = new Random().nextInt(100);
        while (map.containsValue(number)) {
            number = new Random().nextInt();
        }

        return number;
    }

    private Map<String, Integer> readTokens(String filename) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(""))
                    continue;
                String[] fields = line.split(":");
                result.put(fields[0], Integer.parseInt(fields[1]));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public void writeToFile(Map<String, Token> map, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String key : map.keySet()) {
                writer.write(key + " -> " + map.get(key).getLanguageTokenIndex() + " - " + map.get(key).getIdentifierOrConstantTableIndex());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Token> getAtomsTokens() {
        return atomsTokens;
    }

    public Map<String, Token> getIdentifierTokens() {
        return identifierTokens;
    }

    public Map<String, Token> getConstantTokens() {
        return constantTokens;
    }
}