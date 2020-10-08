package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Lexer {

    private final Map<String, Integer> tokenValues;
    private final Map<String, Integer> atoms;
    private int counter;

    public Lexer() {
        tokenValues = Lexer.readFromFile("tokens.txt");
        atoms = new LinkedHashMap<>();
        counter = 1;
    }

    public void parseSourceFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(" ");
                for (String field : fields) {
                    for (String keyword : this.tokenValues.keySet()) {
                        if (field.equals(keyword)) {
                            if (this.atoms.get(field) != null) {
                                this.atoms.put(field, this.atoms.get(field));
                            } else {
                                atoms.put(field, counter);
                                counter++;
                            }
                        }
                    }
                }
            }
            System.out.println(this.atoms);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
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


}
