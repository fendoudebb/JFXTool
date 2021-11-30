package fendoudebb.fx.tool.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * zbj: created on 2021/11/29 8:57.
 */
public class DateFormatter {

    private static final DateTimeFormatter formatter;

    private static final String defaultPattern = "uuuu-MM-dd HH:mm:ss";

    static {
        String pattern = Resource.config().getString("date_format_pattern");
        formatter = DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
    }

    public static String tsToString(long millis) {
        LocalDateTime ldt = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return ldt.format(formatter);
    }

}
