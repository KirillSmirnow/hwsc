package org.smirnowku.hwsc.model.dto;

import java.util.Collection;

public class PropertyValidator {

    public static boolean isEmpty(Object o) {
        return o == null;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }
}
