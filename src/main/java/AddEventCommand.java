import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AddEventCommand extends AddCommand {

    public AddEventCommand(String taskDescription) {
        super(taskDescription); // taskDescription includes the task info and date
        time = null;
        dateTrigger = "/at";
    }

    @Override
    public void execute(TaskList tasks) {
        if (!splitDescTime()) return; // If error occurs, stop the method!
        Task newTask = new Event(taskDescription, time);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());
    }
}
