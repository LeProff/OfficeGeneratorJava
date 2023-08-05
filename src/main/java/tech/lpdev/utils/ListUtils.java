package tech.lpdev.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUtils {

    public static <T> List<T> duplicateList(List<T> list) {
        List<T> newList = new ArrayList<>();
        for (T t : list) {
            newList.add(t);
        }
        return newList;
    }
}
