public class Event extends Task {

    protected String at;

    public Event(String description, String date) {
        super(description);
        type = 'E';
        this.at = date;
    }

    public String getDate() {
        return "(at: " + at + ")";
    }
}
