package common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Lexer {
    private final Map<String, Integer> languageTokens;
    private final List<String> fip;
    private final Map<String, Token> atomsTokens;
    private final Map<String, Token> identifierTokens;
    private final Map<String, Token> constantTokens;


    public Lexer() {
        atomsTokens = new LinkedHashMap<>();
        identifierTokens = new TreeMap<>();
        constantTokens = new TreeMap<>();

        fip = new ArrayList<>();

        languageTokens = readTokens("tokens.txt");
    }

    public List<String> getFip() {
        return fip;
    }

    public void parse() throws Exception {
        List<String> sourceFileLines = Files.readAllLines(Paths.get("source2.c"));
        int lineCounter = 0;
        for (String line : sourceFileLines) {
            lineCounter++;
            if (line.equals(""))
                continue;

            String[] fields = line.split(" ");

            if (fields[0].equals("while") || fields[0].equals("if")) {
                if (!isConditionalStatement(fields)) {
                    throw new Exception("Error at line conditional statement " + lineCounter);
                }
            }


            for (int i = 0; i < fields.length; i++) {
                if (languageTokens.containsKey(fields[i])) {
                    atomsTokens.put(fields[i], new Token(languageTokens.get(fields[i])));
                    fip.add(fields[i] + " " + atomsTokens.get(fields[i]).toString());
                } else {
                    //identifier or constant
                    if (identifierTokens.containsKey(fields[i])) {
                        atomsTokens.put(fields[i], atomsTokens.get(fields[i]));
                        fip.add(fields[i] + " " + atomsTokens.get(fields[i]).toString());
                    } else if (constantTokens.containsKey(fields[i])) {
                        atomsTokens.put(fields[i], constantTokens.get(fields[i]));
                        fip.add(fields[i] + " " + atomsTokens.get(fields[i]).toString());
                    } else {
                        //not detected yet;
                        if (i > 0 && (fields[i - 1].equalsIgnoreCase("int") || fields[i - 1].equalsIgnoreCase("bool"))) {
                            if (fields[i].length() > 250) {
                                throw new Exception("Syntax error at line " + lineCounter);
                            }

                            if (!isAssignment(fields)) {
                                identifierTokens.put(fields[i], new Token(1, generateValue(identifierTokens)));
                            }
                            //sort it
                            fip.add(fields[i] + " " + atomsTokens.get(fields[i]).toString());
                        } else {
                            if (isConstant(fields[i])) {
                                int endOfStringIndex;
                                if ((endOfStringIndex = isStringConstant(fields, i)) != -1) {

                                    int currentFieldIndex = i;
                                    StringBuilder builder = new StringBuilder();

                                    while (currentFieldIndex <= endOfStringIndex) {
                                        builder.append(fields[currentFieldIndex]).append(" ");
                                        currentFieldIndex++;
                                    }

                                    String result = builder.toString();
                                    int index = generateValue(constantTokens);
                                    constantTokens.put(result, new Token(2, index));
                                    atomsTokens.put(result, new Token(2, index));

                                    i = currentFieldIndex;
                                    fip.add(result + " " + atomsTokens.get(result).toString());
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

    private boolean isAssignment(String[] fields) {
        int counter = 0;
        if (fields[0].equals("int") || fields[0].equals("bool")) {
            counter = 1;
            int index = generateValue(identifierTokens);
            identifierTokens.put(fields[1], new Token(1, index));
            atomsTokens.put(fields[1], new Token(1, index));
        }
        if (fields.length == 3) {
            return true;
        }

        return this.identifierExists(fields[counter]) && fields[counter + 1].equals("=") && isNumber(fields[counter + 2]) ||
                this.identifierExists(fields[counter]);
    }


    private boolean isNumber(String potentialNumber) {
        try {
            Double.parseDouble(potentialNumber);
            return true;
        } catch (Exception ex) {
            return false;
        }
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
        return languageTokens.containsKey(atom);
    }

    private boolean identifierExists(String variable) {
        return identifierTokens.containsKey(variable);
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
        map.keySet().stream().sorted();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String key : map.keySet()) {
                writer.write(key + " -> " + map.get(key).getLanguageTokenIndex() + " - " + map.get(key).getIdentifierOrConstantTableIndex());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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