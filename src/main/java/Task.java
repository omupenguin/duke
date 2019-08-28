public class Task {
    protected String description;
    protected boolean isDone;
    protected char type;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); // returns ✓ or X
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getDate() {
        return "";
    }

    public String getTask() {
        return "[" + type + "][" + getStatusIcon() + "] " + description + getDate();
    }
}
