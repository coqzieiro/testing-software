package pt.up.fe.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Deque;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

class MyStringStackTest {

    @Property
    void followsLifoTransitions(@ForAll("actionSequences") List<StackAction> actions) {
        MyStringStack stack = new MyStringStack();
        Deque<String> model = new ArrayDeque<>();

        List<StackAction> sequence = new ArrayList<>();
        for (int index = 0; index < 5; index++) {
            sequence.add(StackAction.push("seed-" + index));
        }
        sequence.add(StackAction.clear());
        sequence.addAll(actions);

        for (StackAction action : sequence) {
            switch (action.kind) {
            case PUSH:
                stack.push(action.value);
                model.push(action.value);
                break;
            case POP:
                if (!model.isEmpty()) {
                    assertEquals(model.pop(), stack.pop());
                }
                break;
            case TOP:
                if (!model.isEmpty()) {
                    assertEquals(model.peek(), stack.top());
                }
                break;
            case CLEAR:
                stack.clear();
                model.clear();
                break;
            default:
                throw new IllegalStateException("Unsupported stack action: " + action.kind);
            }

            assertEquals(model.size(), stack.size());
            assertEquals(model.isEmpty(), stack.isEmpty());
            if (!model.isEmpty()) {
                assertEquals(model.peek(), stack.top());
            }
        }
    }

    @Property
    void poppingOrReadingTheTopOfAnEmptyStackThrows(@ForAll boolean pop) {
        MyStringStack stack = new MyStringStack();
        assertThrows(IndexOutOfBoundsException.class,
            () -> {
                if (pop) {
                    stack.pop();
                } else {
                    stack.top();
                }
            });
        assertTrue(stack.isEmpty());
    }

    @Provide
    Arbitrary<List<StackAction>> actionSequences() {
        Arbitrary<StackAction> pushes = Arbitraries.strings()
            .alpha().ofMinLength(1).ofMaxLength(12)
            .map(StackAction::push);
        Arbitrary<StackAction> otherActions = Arbitraries.of(
            StackAction.pop(), StackAction.top(), StackAction.clear());
        return Arbitraries.oneOf(pushes, otherActions).list().ofMaxSize(100);
    }

    private enum ActionKind {
        PUSH, POP, TOP, CLEAR
    }

    private static final class StackAction {

        private final ActionKind kind;
        private final String value;

        private StackAction(ActionKind kind, String value) {
            this.kind = kind;
            this.value = value;
        }

        static StackAction push(String value) {
            return new StackAction(ActionKind.PUSH, value);
        }

        static StackAction pop() {
            return new StackAction(ActionKind.POP, null);
        }

        static StackAction top() {
            return new StackAction(ActionKind.TOP, null);
        }

        static StackAction clear() {
            return new StackAction(ActionKind.CLEAR, null);
        }
    }
}
