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
}
