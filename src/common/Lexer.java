package common;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Lexer {
    private static final int TS_COUNTER = 50;

    private final Map<String, Integer> tokenValues;
    private final Map<String, LexicalAtom> atoms;
    private final Map<String, Integer> constantsTable;
    private final Map<String, Integer> identifiersTable;
    private int constantCounterIndex;
    private int identifierCounterIndex;
    private final List<String> errors;
    private int counter = 0;


    public Lexer() {
        tokenValues = Lexer.readFromFile("tokens.txt");
        atoms = new LinkedHashMap<>();
        constantsTable = new LinkedHashMap<>();
        identifiersTable = new LinkedHashMap<>();
        constantCounterIndex = 1;
        identifierCounterIndex = 1;
        errors = new ArrayList<>();
    }

    public void parseSourceFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean ok;
            while ((line = reader.readLine()) != null) {
                if (line.equals(""))
                    continue;

                evaluate(line);
                String[] chStrings = line.split(" ");

                List<String> fields = this.createAtomsListFromSource(chStrings);

                for (String field : fields) {
                    ok = false;
                    if (field.equals(""))
                        continue;
                    for (String keyword : this.tokenValues.keySet()) {
                        if (field.equals(keyword)) {
                            if (this.atoms.get(field) != null) {
                                this.atoms.put(field, this.atoms.get(field));
                            } else {
                                atoms.put(field, new LexicalAtom(this.tokenValues.get(field)));
                            }
                            ok = true;
                        }
                    }
                    if (ok) {
                        continue;
                    }
                    identifierOrConstant(field);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


    public void evaluate(String line) {
        String[] fields = line.split(" ");
        if (fields[0].equals("if") || fields[0].equals("while")) {
            boolean status = isCondition(
                    IntStream.range(1, fields.length - 1)
                            .mapToObj(i -> fields[i])
                            .toArray(String[]::new)
            );
        }
    }

    private boolean isCondition(String[] fields) {
        int i = 1;
        while (i < fields.length && !fields[i].equals(")")) {
            i++;
        }
        if (i == fields.length) {
            return false;
        }
        return true;
    }

    private void identifierOrConstant(String field) {
        if (field.charAt(0) == '"' && field.charAt(field.length() - 1) == '"') {
            //this is a string constant
            constantsTable.put(field, constantCounterIndex + TS_COUNTER);
            // 2 is the code for the constants
            atoms.put(field, new LexicalAtom(2, constantCounterIndex + TS_COUNTER, "CONSTANT"));
            constantCounterIndex++;
        } else {
            //this is a identifier
            identifiersTable.put(field, identifierCounterIndex + TS_COUNTER);
            atoms.put(field, new LexicalAtom(1, identifierCounterIndex + TS_COUNTER, "IDENTIFIER"));
            identifierCounterIndex++;
        }
    }

    private List<String> createAtomsListFromSource(String[] chStrings) {
        List<String> fields = new ArrayList<>();
        for (int i = 0; i < chStrings.length; i++) {
            if (chStrings[i].equals("")) {
                continue;
            }
            StringBuilder chField = new StringBuilder(chStrings[i]);
            if (chStrings[i].charAt(0) == '"' && chStrings[i].charAt(chField.length() - 1) != '"') {
                i++;
                while (!chStrings[i].contains("\"")) {
                    chField.append(chStrings[i]);
                    i++;
                }
                chField.append(chStrings[i]);
                i++;
            }
            fields.add(chField.toString());
        }
        return fields;
    }


    public void writeToFile(String filename, Map<String, LexicalAtom> map) {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            for (String key : map.keySet()) {
                writer.write(key + " -> " + map.get(key) + "\n");
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param filename the file with the default categories
     * @return Map with all the categories
     */
    public static Map<String, Integer> readFromFile(String filename) {
        Map<String, Integer> result = new LinkedHashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(":");
                result.put(fields[0], Integer.parseInt(fields[1]));
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    /***
     * Those are getters and setters
     */

    public Map<String, LexicalAtom> getAtoms() {
        return atoms;
    }

    public Map<String, Integer> getConstantsTable() {
        return constantsTable;
    }

    public Map<String, Integer> getTokenValues() {
        return tokenValues;
    }

    public Map<String, Integer> getIdentifiersTable() {
        return identifiersTable;
    }
}
