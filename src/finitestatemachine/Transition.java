package finitestatemachine;

public class Transition {
    private State origin;
    private State destination;
    private String value;

    public Transition(State origin, State destination, String value) {
        this.origin = origin;
        this.destination = destination;
        this.value = value;
    }

    public State getOrigin() {
        return origin;
    }

    public void setOrigin(State origin) {
        this.origin = origin;
    }

    public State getDestination() {
        return destination;
    }

    public void setDestination(State destination) {
        this.destination = destination;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                origin +
                " -> " + destination +
                ", value=" + value +
                '}';
    }
}
