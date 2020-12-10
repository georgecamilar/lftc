package finitestatemachine;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FiniteStateMachine {
    private final Map<State, List<Transition>> states;
    private final List<State> finalStates;
    private State initialState;
    private final List<String> transitionValues;

    public FiniteStateMachine(String filename) throws IOException {
        states = new LinkedHashMap<>();
        finalStates = new ArrayList<>();
        transitionValues = new ArrayList<>();
        initialize(filename);
    }


    public FiniteStateMachine(BufferedReader reader) throws IOException {
        states = new LinkedHashMap<>();
        finalStates = new ArrayList<>();
        transitionValues = new ArrayList<>();
        initializeKeyboard(reader);
    }

    private void initializeKeyboard(BufferedReader reader) throws IOException {
        //initialize parts
        System.out.println("Node names");
        System.out.println("Leave a space after each node");
        String line;
        line = reader.readLine();
        String[] fields = line.split(" ");
        Arrays.stream(fields)
                .map(State::new)
                .forEach(el -> states.put(el, new ArrayList<>()));

        //initialize values
        System.out.println("Give transition values");
        System.out.println("Leave a space after each transition value");
        line = reader.readLine();
        fields = line.split(" ");
        Arrays.stream(fields).map(Object::toString).forEach(transitionValues::add);

        //give initial node
        System.out.println("Give initial node");
        line = reader.readLine();
        initialState = new State(line);

        //give final nodes(exit nodes)
        System.out.println("Give final nodes(exit nodes)");
        System.out.println("Leave a space between them");
        line = reader.readLine();
        fields = line.split(" ");
        Arrays.stream(fields).map(State::new).forEach(finalStates::add);

        //give transition
        while (true) {
            System.out.println("Give transitions");
            System.out.println("Format: origin destination value");
            System.out.println("To stop enter a line containing a \"stop\"");

            line = reader.readLine();
            if (line.equals("stop")) {
                break;
            }

            fields = line.split(" ");
            Transition transition = new Transition(new State(fields[0]), new State(fields[1]), fields[2]);
            if (transitionValues.contains(transition.getValue())) {
                states.get(transition.getOrigin()).add(transition);
            }
        }
    }


    private void initialize(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(filename));

        for (String line : lines) {
            String[] fields = line.strip().split(" ");
            switch (fields[0]) {
                case "Q":
                    String[] nodes = fields[1].split(",");
                    Arrays.stream(nodes)
                            .map(State::new)
                            .forEach(el -> states.put(el, new ArrayList<>()));
                    break;
                case "E":
                    String[] values = fields[1].split(",");
                    Arrays.stream(values).map(Object::toString).forEach(transitionValues::add);
                    break;
                case "I":
                    initialState = new State(fields[1]);
                    break;
                case "F":
                    String[] finalNodes = fields[1].split(",");
                    Arrays.stream(finalNodes).map(State::new).forEach(finalStates::add);
                    break;
                case "transitions":
                    break;
                default:
                    String[] destinations = line.split("->");
                    String[] transitionArguments = destinations[1].split(",");
                    Transition transition = new Transition(new State(destinations[0]), new State(transitionArguments[0]), transitionArguments[1]);
                    if (transitionValues.contains(transition.getValue())) {
                        states.get(transition.getOrigin()).add(transition);
                    }
                    break;
            }
        }
    }

    private void validSequence(List<String> values) throws Exception {
        for (String value : values) {
            if (!this.transitionValues.contains(value)) {
                throw new Exception("Wrong input value");
            }
        }
    }


    public List<Transition> checkSequence(String sequence) throws Exception {
        State currentState = initialState;
        List<String> sequenceValues = new ArrayList<>(Arrays.asList(sequence.split("")));

        validSequence(sequenceValues);

        System.out.println("----Checking sequence----");
        System.out.println(sequenceValues);

        int currentSequenceValueIndex = 0;
        List<Transition> transitions = findNextTransition(currentState, sequenceValues.get(currentSequenceValueIndex));
        currentSequenceValueIndex++;
        List<List<Transition>> solutions = new ArrayList<>();
        solutions.add(transitions);
        while (currentSequenceValueIndex < sequenceValues.size()) {

            int finalCurrentSequenceValueIndex = currentSequenceValueIndex;
            solutions = solutions.stream()
                    .flatMap(el -> generateSolutions(el, sequenceValues.get(finalCurrentSequenceValueIndex)))
                    .collect(Collectors.toList());
            currentSequenceValueIndex++;
        }
        List<Transition> longestAccepted = new ArrayList<>();
        for (List<Transition> solution : solutions) {
            if (finalStates.contains(solution.get(solution.size() - 1).getDestination())) {
                if (longestAccepted.size() < solution.size()) {
                    longestAccepted = solution;
                }
            }
        }
        System.out.println("Longest");
        System.out.println(longestAccepted);
        return longestAccepted;
    }

    private Stream<List<Transition>> generateSolutions(List<Transition> transitions, String value) {
        List<List<Transition>> res = new ArrayList<>();
        final List<Transition> result = findNextTransition(transitions.get(transitions.size() - 1).getDestination(), value);
        res.add(transitions);
        for (Transition el : result) {

            List<Transition> toAdd = new ArrayList<>(transitions);
            toAdd.add(el);
            res.add(toAdd);
        }

        return res.stream();
    }


    private List<Transition> findNextTransition(State currentState, String value) {
        return states.get(currentState)
                .stream()
                .filter(el -> el.getValue().equals(value))
                .collect(Collectors.toList());
    }


    public Set<State> getAllStates() {
        return this.states.keySet();
    }

    public List<String> getAlphabet() {
        return this.transitionValues
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> getTransitions() {
        return this.states
                .keySet().stream()
                .flatMap(el -> states.get(el).stream())
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public List<String> getFinalStates() {
        return this.finalStates
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }


}
