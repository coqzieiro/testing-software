package pt.up.fe.testing;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

class UniqueTest {

    @Property
    void returnsEveryDistinctValueOnceInDescendingOrder(
        @ForAll("arraysContainingDuplicates") double[] data) {
        double[] expected = Arrays.stream(data)
            .boxed()
            .distinct()
            .sorted(Comparator.reverseOrder())
            .mapToDouble(Double::doubleValue)
            .toArray();

        assertArrayEquals(expected, Unique.unique(data));
    }

    @Example
    void rejectsANullArray() {
        assertThrows(NullPointerException.class, () -> Unique.unique(null));
    }

    @Example
    void returnsAnEmptyArrayForEmptyInput() {
        assertArrayEquals(new double[0], Unique.unique(new double[0]));
    }

    @Example
    void preservesTheOrderingOfInfinitiesAndNaN() {
        double[] input = {
            Double.NEGATIVE_INFINITY, 3.0, Double.POSITIVE_INFINITY,
            Double.NaN, 3.0, Double.NaN
        };
        double[] expected = {
            Double.NaN, Double.POSITIVE_INFINITY, 3.0,
            Double.NEGATIVE_INFINITY
        };

        assertArrayEquals(expected, Unique.unique(input));
    }

    @Provide
    Arbitrary<double[]> arraysContainingDuplicates() {
        return smallIntegers().list().ofMinSize(1).ofMaxSize(50)
            .map(this::duplicateAsDoubles);
    }

    @Provide
    Arbitrary<Integer> smallIntegers() {
        return Arbitraries.integers().between(-100, 100);
    }

    private double[] duplicateAsDoubles(List<Integer> values) {
        double[] result = new double[values.size() * 2];
        for (int index = 0; index < values.size(); index++) {
            double value = values.get(index);
            result[index] = value;
            result[index + values.size()] = value;
        }
        return result;
    }
}
