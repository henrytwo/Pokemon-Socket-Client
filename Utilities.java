import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Utilities {
    public static <T> ArrayList<T> deepCopy(ArrayList<T> starter) {
        ArrayList<T> newArrayList = new ArrayList<>();

        for (T item: starter) {
            newArrayList.add(item);
        }

        return newArrayList;
    }

    public static <T> int indexOf(T[]field, T target) {
        for (int i = 0; i < field.length; i++) {
            if (field[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static String filter(String base, String[] target) {
        for (String kill : target) {
            base = base.replace(kill, "");
        }
        return base;
    }
}
