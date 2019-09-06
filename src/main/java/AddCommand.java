import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public abstract class AddCommand extends Command{

    protected String taskDescription; // The input line after the command
    protected LocalDateTime time;
    protected String dateTrigger;
    public String MissingDescriptionString = "Please specify the task you want to add!";

    public AddCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    // If an error occurs while splitting the array, return false.
    public boolean splitDescTime() {
        String[] data = taskDescription.split(dateTrigger + " "); // data[0] os description, data[1] is the time
        try {
            time = Time.readTime(data[1]);
            taskDescription = data[0];
            return true;
        } catch(ArrayIndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>();
            msg.add("Please add '" + dateTrigger + " <date>' after your task to specify the event date." );
            Ui.printMsg(msg);
            return false;
        }  catch(DateTimeParseException e) {
            ArrayList<String> msg = new ArrayList<String>();
            msg.add("Please use the format 'DD/MM/YYYY HHmm'!" );
            Ui.printMsg(msg);
            return false;
        }
    }
}
