package com.study.financial.utils;

import java.util.List;

public class ListUtils {
    public static <T> boolean isEquals(List<T> listA, List<T> listB) {
        if (listA.size() != listB.size()) {
            return false;
        }

        for (T a : listA) {
            if (!listB.contains(a)) {
                return false;
            }
        }

        return true;
    }
}
