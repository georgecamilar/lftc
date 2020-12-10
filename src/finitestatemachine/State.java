package finitestatemachine;

import java.util.Objects;

public class State {
    private String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}