package bg.softuni.parking.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDateTime formatDate(LocalDateTime dateTime) {
        return LocalDateTime.parse(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}