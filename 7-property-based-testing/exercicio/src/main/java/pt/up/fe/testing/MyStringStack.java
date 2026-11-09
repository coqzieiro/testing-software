package pt.up.fe.testing;

import java.util.ArrayList;
import java.util.List;

public class MyStringStack {

    private final List<String> elements = new ArrayList<>();

    public void push(String element) {
        elements.add(0, element);
    }

    public String pop() {
        return elements.remove(0);
    }

    public void clear() {
        elements.clear();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public String top() {
        return elements.get(0);
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        MyStringStack that = (MyStringStack) other;
        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return elements.hashCode();
    }
}
