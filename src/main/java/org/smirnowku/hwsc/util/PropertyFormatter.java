package org.smirnowku.hwsc.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PropertyFormatter {

    public static String format(LocalDateTime localDateTime) {
        if (localDateTime == null) return "";
        return localDateTime.format(DateTimeFormatter.ofPattern("MMMM d, u HH:mm"));
    }
}
