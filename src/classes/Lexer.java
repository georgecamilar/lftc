package classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Lexer {

    List<String> programTextLines;

    public Lexer(String filePath) {
        this.programTextLines = fileReader(filePath);
    }

    private List<String> fileReader(String filename) {
        List<String> fileLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            for (String word : fields) {
                System.out.println(word);
            }
        }
    }
}
