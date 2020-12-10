package common;

import finitestatemachine.FiniteStateMachine;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Files;

public class Parser {
    private final FiniteStateMachine stateMachine;

    /**
     * Creates an instance of the {@link Parser} class
     * Use the parse method to parse a file
     *
     * @param rules {@link String} containing the path to the rules of the state machine
     * @throws IOException if the file is not found and the state machine cannot initialise
     */
    public Parser(String rules) throws IOException {
        stateMachine = new FiniteStateMachine(rules);
    }


    /**
     * @param filename {@link String} representing the path of the file which needs to be evaluated
     *                 The path is relative to the src folder
     * @throws Exception from the state machine code or in case the file cannot be opened(IOException)
     */
    public void parse(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filename));

        int index = 1;
        for (String line : lines) {
            if (stateMachine.checkSequence(line).size() == line.length()) {
                System.out.println(line + " is accepted!");
            } else {
                System.err.println(line + " has errors");
                System.err.println("Check source file at line: " + index);
            }
            index++;
        }
    }


}