import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

public class Time {
    public static LocalDateTime readTime(String timeStr) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
            LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
//            System.out.println(time);
            return time;

    }
}
