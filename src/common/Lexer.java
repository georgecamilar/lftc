package common;

import structure.BinarySearchTree;
import structure.Node;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final Map<String, Integer> languageTokens;
    private final List<String> fip;
    private final Map<String, Token> atomsTokens;
    BinarySearchTree identifiers;
    BinarySearchTree constants;
    private int idIndex = 100;
    private int constIndex = 100;

    public Lexer() {
        atomsTokens = new LinkedHashMap<>();
        identifiers = new BinarySearchTree();
        constants = new BinarySearchTree();
        fip = new ArrayList<>();

        languageTokens = readTokens("tokens.txt");
    }

    public List<String> getFip() {
        return fip;
    }

    public BinarySearchTree getIdentifiers() {
        return identifiers;
    }

    public BinarySearchTree getConstants() {
        return constants;
    }

    public void parse() throws Exception {
        List<String> sourceFileLines = Files.readAllLines(Paths.get("source2.c"));
        int lineCounter = 0;
        for (String line : sourceFileLines) {
            lineCounter++;
            if (line.equals(""))
                continue;

            String[] fields = line.split(" ");
            for (int i = 0; i < fields.length; i++) {
                Token token;
                if (languageTokens.containsKey(fields[i])) {
                    atomsTokens.put(fields[i], new Token(languageTokens.get(fields[i])));
                    fip.add(fields[i] + " - " + languageTokens.get(fields[i]));
                } else if ((token = this.fipContainsIdentifierOrConstant(fields[i])) != null) {
                    fip.add(fields[i] + " - " + token);
                } else if ((token = this.fipContainsIdentifierOrConstant(fields[i])) != null) {
                    fip.add(fields[i] + " - " + token);
                } else {
                    //constant or identifier not yet identified
                    if (isIdentifier(fields[i])) {
                        if (fields[i].length() > 250)
                            throw new Exception("Error at line: " + lineCounter + " identifier size is too big");
                        int index = idIndex;
                        idIndex++;
                        atomsTokens.put(fields[i], new Token(1, index));
                        identifiers.add(fields[i], new Token(1, index));
                        fip.add(fields[i] + " - " + this.fipContainsIdentifierOrConstant(fields[i]));
                    } else {
                        //constant token

                        if (isConstant(fields[i])) {
                            //only constants are string constants
                            int endOfStringIndex;
                            if ((endOfStringIndex = isStringConstant(fields, i)) != -1) {

                                int currentFieldIndex = i;
                                StringBuilder builder = new StringBuilder();

                                while (currentFieldIndex <= endOfStringIndex) {
                                    builder.append(fields[currentFieldIndex]).append(" ");
                                    currentFieldIndex++;
                                }

                                String result = builder.toString();
                                int index = constIndex;
                                constIndex++;
                                constants.add(result, new Token(2, index));
                                atomsTokens.put(result, new Token(2, index));

                                i = currentFieldIndex;
                                fip.add(result + " " + this.fipContainsIdentifierOrConstant(fields[i]));
                            }
                        } else {
                            throw new Exception("Error at line: " + lineCounter);
                        }
                    }
                }
            }

        }
    }

    public Token fipContainsIdentifierOrConstant(String value) {
        if (this.atomsTokens.containsKey(value)) {
            return atomsTokens.get(value);
        }
        return null;
    }

    private int isStringConstant(String[] fields, Integer i) {
        boolean stringStatus = fields[i].contains("\"");
        if (!stringStatus) {
            return -1;
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

    public void setIdIndex(int idIndex) {
        this.idIndex = idIndex;
    }

    public void setConstIndex(int constIndex) {
        this.constIndex = constIndex;
    }

    private boolean isIdentifier(String field) {
        return field.matches(TokenRegex.IDENTIFIER.getPattern());
    }

    private boolean isConstant(String constant) {
        return constant.contains("\"");
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


    public void writeFip(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String field : fip) {
                writer.write(field);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeBstToFile(BinarySearchTree tree, String filename) {
        tree.writeToFile(filename);
    }


}