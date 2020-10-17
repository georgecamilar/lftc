package common;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private final Map<String, Integer> tokenValues;
    private final Map<String, Integer> atoms;
    private final Map<String, Integer> constantsTable;
    private final Map<String, Integer> identifiersTable;
    private final List<String> fip;
    private int atomCounterIndex;
    private int constantCounterIndex;
    private int identifierCounterIndex;

    public Lexer() {
        tokenValues = Lexer.readFromFile("tokens.txt");
        atoms = new LinkedHashMap<>();
        constantsTable = new LinkedHashMap<>();
        fip = new ArrayList<>();
        identifiersTable = new LinkedHashMap<>();
        atomCounterIndex = 3; // 1 is for identifiers and 2 is for constants, so start from 3 and go up
        constantCounterIndex = 1;
        identifierCounterIndex = 1;
    }

    public void parseSourceFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean ok;
            while ((line = reader.readLine()) != null) {

                String[] chStrings = line.split(" ");
                List<String> fields = this.createAtomsListFromSource(chStrings);

                for (String field : fields) {
                    ok = false;
                    if (field.equals(""))
                        continue;
                    fip.add(field);
                    for (String keyword : this.tokenValues.keySet()) {
                        if (field.equals(keyword)) {
                            if (this.atoms.get(field) != null) {
                                this.atoms.put(field, this.atoms.get(field));
                            } else {
                                atoms.put(field, atomCounterIndex);
                                atomCounterIndex++;
                            }
                            ok = true;
                        }
                    }
                    if (ok) {
                        continue;
                    }
                    // check if something has been added
                    // if not, then it is a constant or a identifier
                    identifierOrConstant(field);
                }
            }
//            System.out.println(this.atoms);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void identifierOrConstant(String field) {
        if (field.charAt(0) == '"' && field.charAt(field.length() - 1) == '"') {
            //this is a string constant
            constantsTable.put(field, constantCounterIndex);
            constantCounterIndex++;
            atoms.put(field, 2); // 2 is the code for the constants
        } else {
            //this is a identifier
            identifiersTable.put(field, identifierCounterIndex);
            identifierCounterIndex++;
            atoms.put(field, 1);
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
//            System.out.println(fields);
        }
        return fields;
    }


    public void writeToFile(String filename, Map<String, Integer> map) {
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

    public Map<String, Integer> getAtoms() {
        return atoms;
    }

    public Map<String, Integer> getConstantsTable() {
        return constantsTable;
    }

    public List<String> getFip() {
        return fip;
    }

    public Map<String, Integer> getIdentifiersTable() {
        return identifiersTable;
    }
}
