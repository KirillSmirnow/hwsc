package org.smirnowku.hwsc.util;

import com.vaadin.ui.UI;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeService {

    public static LocalDateTime getClientNow() {
        return LocalDateTime.now(getClientOffset());
    }

    public static LocalDateTime toUtc(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        return localDateTime.atOffset(getClientOffset())
                .atZoneSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
    }

    public static LocalDateTime toLocal(LocalDateTime utcDateTime) {
        if (utcDateTime == null) return null;
        return utcDateTime.atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(getClientOffset())
                .toLocalDateTime();
    }

    private static ZoneOffset getClientOffset() {
        int offset = UI.getCurrent().getPage().getWebBrowser().getTimezoneOffset();
        return ZoneOffset.ofTotalSeconds(offset / 1000);
    }
}
