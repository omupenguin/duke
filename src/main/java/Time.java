import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Time {
    public static LocalDateTime readTime(String timeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
            LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
//            System.out.println(time);
            return time;
        } catch(DateTimeParseException e) {
//            System.out.println("This format is not okay!");
            return null;
        }
    }
}
