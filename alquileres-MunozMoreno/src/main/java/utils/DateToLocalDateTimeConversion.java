package utils;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateToLocalDateTimeConversion {

    // Convert Date to LocalDateTime
    public static LocalDateTime toLocalDateTime(Date date) {
    	if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime())
                      .atZone(ZoneId.systemDefault())
                      .toLocalDateTime();
    }

    // Convert LocalDateTime to Date
    public static Date toDate(LocalDateTime localDateTime) {
    	if (localDateTime == null) return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
