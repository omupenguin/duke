import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Task is a class that all types of tasks will inherit from.
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected char type;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns an 'O' or 'X' depending on isDone.
     * @return a readable char corresponding to isDone.
     */
    public String getStatusIcon() {
//        return (isDone ? "\u2713" : "\u2718"); // returns ✓ or X
        return (isDone ? "O" : "X"); // returns O or X
    }

    public void markAsDone() {
        isDone = true;
    }

    /**
     * Returns an empty String.
     * <p>
     *     By default, a task does not have a date stored, so nothing is printed. But for
     *     tasks with dates, this method will be overwritten.
     * </p>
     * <p>
     *     This method is mainly used in 'getTask()' for printing the task information.
     *     Unless the task stores date for whatever reason, nothing should be printed
     *     since no data for the date exists.
     * </p>
     * @return Empty String.
     */
    public String getDate() {
        return "";
    }

    /**
     * Returns a string corresponding to the specified LocalDateTime in the format 'dd/MM/yyyy HHmm'.
     * @param time The LocalDateTime variable to be converted.
     * @return A string in the format 'dd/MM/yyyy HHmm'
     */
    public String timeToString(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
        String timeStr = time.format(formatter);
        return timeStr;
    }

    /**
     * Returns a string containing details of the task in the format '[TYPE][ISDONE]DESCRIPTION DATE'.
     * <p>
     *     Mainly used to display tasks to the user.
     * </p>
     * @return a string containing details of the task.
     */
    public String getTask() {
        return "[" + type + "][" + getStatusIcon() + "] " + description + getDate();
    }

    /**
     * Returns an empty String.
     * <p>
     *      By default, a task does not have a date stored, so nothing is printed. But for
     *      tasks with dates, this method will be overwritten.
     * </p>
     * <p>
     *      This method is mainly used in 'formatSave()' for saving the task information.
     *      Unless the task stores date for whatever reason, nothing should be printed
     *      since no data for the date exists.
     * </p>
     * @return Empty String.
     */
    public String formatDateSave() {
        return "";
    }

    /**
     * Returns a string containing details of the task in the format 'TYPE | ISDONE | DESCRIPTION | TIME'.
     * <p>
     *     Mainly used to create save data that is quick to load.
     * </p>
     * @return a string containing details of the task.
     */
    public String formatSave() {
        return type + " | " + (isDone ? 1 : 0) + " | " + description + formatDateSave();
    }
}
