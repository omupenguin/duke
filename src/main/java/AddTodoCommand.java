import java.util.ArrayList;
import java.util.Arrays;

/**
 * AddToDoCommand is a command used to add a todo task into the TaskList.
 */
public class AddTodoCommand extends AddCommand {

   public AddTodoCommand(String taskDescription) {
        super(taskDescription);
    }

    /**
     * Adds a todo task into the specified TaskList.
     * <p>
     *     If the task description is missing, return without doing anything.
     * </p>
     * @param tasks The TaskList where the todo task is to be added.
     */
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
