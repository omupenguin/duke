public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String date) {
        super(description);
        type = 'D';
        this.by = date;
    }

    @Override
    public String getDate () {
        return "(by: " + by + ")";
    }

    @Override
    public String formatDateSave() {
        return " | " + by;
    }
}
