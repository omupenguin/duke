import java.time.LocalDateTime;

public class Event extends Task {

    protected LocalDateTime at;

    public Event(String description, LocalDateTime date) {
        super(description);
        type = 'E';
        this.at = date;
    }

    @Override
    public String getDate() {
        String atStr = timeToString(at);
        return "(at: " + atStr + ")";
    }

    @Override
    public String formatDateSave() {
        String atStr = timeToString(at);
        return " | " + atStr;
    }
}
