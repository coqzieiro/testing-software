package pt.up.fe.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IntArraySearchTest {

    @ParameterizedTest
    @MethodSource("manualSearchCases")
    void matchesTheOriginalManualExamples(
        int[] array, int value, int startIndex, int expected) {
        assertEquals(expected, IntArraySearch.indexOf(array, value, startIndex));
    }

    @Example
    void handlesEmptyArraysAndStartIndexBoundaries() {
        int[] array = {0, 1, 2, 3, 0};

        assertEquals(-1, IntArraySearch.indexOf(new int[0], 0, 0));
        assertEquals(0, IntArraySearch.indexOf(array, 0, -1));
        assertEquals(4, IntArraySearch.indexOf(array, 0, 2));
        assertEquals(-1, IntArraySearch.indexOf(array, 1, 2));
        assertEquals(-1, IntArraySearch.indexOf(array, 0, array.length));
        assertEquals(-1, IntArraySearch.indexOf(array, 0, array.length + 1));
    }

    private static Stream<Arguments> manualSearchCases() {
        int[] array = {0, 1, 2, 3, 0};
        return Stream.of(
            Arguments.of((int[]) null, 0, 2, -1),
            Arguments.of(array, 0, 2, 4),
            Arguments.of(array, 1, 2, -1),
            Arguments.of(array, 2, 2, 2),
            Arguments.of(array, 3, 2, 3),
            Arguments.of(array, 3, -1, 3),
            Arguments.of(array, 99, 0, -1),
            Arguments.of(array, 0, 6, -1));
    }

    @Property
    void findsTheFirstMatchingValueAtOrAfterTheStartIndex(
        @ForAll("searchCases") SearchCase searchCase) {
        int expected = -1;
        for (int index = Math.max(0, searchCase.startIndex);
             index < searchCase.array.length; index++) {
            if (searchCase.array[index] == searchCase.value) {
                expected = index;
                break;
            }
        }

        assertEquals(expected, IntArraySearch.indexOf(
            searchCase.array, searchCase.value, searchCase.startIndex));
    }

    @Property
    void returnsMinusOneForNullArrays(@ForAll int value, @ForAll int startIndex) {
        assertEquals(-1, IntArraySearch.indexOf(null, value, startIndex));
    }

    @Provide
    Arbitrary<SearchCase> searchCases() {
        return Arbitraries.integers().between(1, 100).flatMap(length ->
            Arbitraries.integers().between(-1000, 1000).flatMap(value ->
                Arbitraries.integers().between(-10, length + 10).flatMap(start ->
                    Arbitraries.integers().between(0, length - 1).flatMap(insertedAt ->
                        Arbitraries.integers().between(-1000, 1000)
                            .list().ofSize(length)
                            .map(values -> createSearchCase(
                                length, value, start, insertedAt, values))))));
    }

    private SearchCase createSearchCase(
        int length, int value, int start, int insertedAt, List<Integer> values) {
        int[] array = new int[length];
        for (int index = 0; index < length; index++) {
            array[index] = values.get(index);
        }
        array[insertedAt] = value;
        return new SearchCase(array, value, start);
    }

    private static final class SearchCase {

        private final int[] array;
        private final int value;
        private final int startIndex;

        SearchCase(int[] array, int value, int startIndex) {
            this.array = array;
            this.value = value;
            this.startIndex = startIndex;
        }
    }
}
