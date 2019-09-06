import java.util.ArrayList;
import java.util.Arrays;

public class AddTodoCommand extends AddCommand {

   public AddTodoCommand(String taskDescription) {
        super(taskDescription);
    }

    @Override
    public void execute(TaskList tasks) {

        if (taskDescription.length() == 0) { // TODO: Exception handling?
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    MissingDescriptionString
            ));
            Ui.printMsg(msg);
            return;
        }

        Task newTask = new ToDo(taskDescription);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());
    }
}
