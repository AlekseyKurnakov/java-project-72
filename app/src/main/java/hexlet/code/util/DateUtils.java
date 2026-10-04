package hexlet.code.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final ZoneId ZONE = ZoneId.of("Europe/Moscow");

    public static String format(Instant createdAt) {
        if (createdAt == null) {
            return null;
        }
        return createdAt.atZone(ZONE).format(FORMATTER);
    }
}