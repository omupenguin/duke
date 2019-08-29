import java.time.LocalDateTime;

public class Deadline extends Task {

    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime date) {
        super(description);
        type = 'D';
        this.by = date;
    }

    @Override
    public String getDate () {
        String byStr = timeToString(by);
        return "(by: " + byStr + ")";
    }

    @Override
    public String formatDateSave() {
        String byStr = timeToString(by);
        return " | " + byStr;
    }
}
