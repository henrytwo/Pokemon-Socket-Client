/**
 * Pokemon Arena
 * Utilities.java
 *
 * Class with common utilities
 *
 * ICS4U [2017/2018]
 * github.com/henrytwo
 * henrytu.me
 *
 * @author Henry Tu
 */

import java.util.ArrayList;

public class Utilities {
    /**
     * Deep copies iterable
     *
     * @param starter          Target object
     * @param <T>              Target type
     * @return                 Deep copied object
     */
    public static <T> ArrayList<T> deepCopy(ArrayList<T> starter) {
        ArrayList<T> newArrayList = new ArrayList<T>();

        for (T item: starter) {
            newArrayList.add(item);
        }

        return newArrayList;
    }

    /**
     * Index of target in iterable
     *
     * @param field            Target field
     * @param target           Target iterable
     * @param <T>              Target type
     * @return                 Integer of Target index
     */
    public static <T> int indexOf(T[]field, T target) {
        for (int i = 0; i < field.length; i++) {
            if (field[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Filters String array of target characters from base string
     *
     * @param base             Base String
     * @param target           String array of target characters
     * @return                 Filtered String
     */
    public static String filter(String base, String[] target) {
        for (String kill : target) {
            base = base.replace(kill, "");
        }
        return base;
    }
}
