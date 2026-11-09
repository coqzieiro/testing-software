package pt.up.fe.testing;

public final class IntArraySearch {

    private IntArraySearch() {
    }

    /**
     * Finds the first occurrence of a value at or after {@code startIndex}.
     *
     * @param array array to search, or null
     * @param valueToFind value to find
     * @param startIndex starting index; negative values are treated as zero
     * @return the matching index, or -1 if the array is null or no match exists
     */
    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        int start = Math.max(startIndex, 0);
        for (int index = start; index < array.length; index++) {
            if (array[index] == valueToFind) {
                return index;
            }
        }
        return -1;
    }
}
