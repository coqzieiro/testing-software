package pt.up.fe.testing;

import java.util.Iterator;
import java.util.TreeSet;

public final class Unique {

    private Unique() {
    }

    /**
     * Returns the distinct values from {@code data} in descending order.
     *
     * @param data array to scan
     * @return the distinct values in descending order
     * @throws NullPointerException if {@code data} is null
     */
    public static double[] unique(double[] data) {
        TreeSet<Double> values = new TreeSet<>();
        for (double value : data) {
            values.add(value);
        }

        double[] result = new double[values.size()];
        Iterator<Double> iterator = values.descendingIterator();
        int index = 0;
        while (iterator.hasNext()) {
            result[index++] = iterator.next();
        }
        return result;
    }
}
