/**
 * AddEventCommand is a command used to add an event task into the TaskList.
 */
public class AddEventCommand extends AddCommand {

    public AddEventCommand(String taskDescription) {
        super(taskDescription); // taskDescription includes the task info and date
        time = null;
        dateTrigger = "/at";
    }

    /**
     * Adds an event task into the specified TaskList.
     * <p>
     *     The method first splits the input data into the task description and time.
     *     Using the newly acquired data, a new Event Task is created and then stored into
     *     the specified TaskList.
     * </p>
     * <p>
     *     If the method encounters an error when trying to split the input data, the method
     *     returns without doing anything.
     * </p>
     * @param tasks The TaskList where the event task is to be added.
     */
    @Override
    public void execute(TaskList tasks) {
        if (!splitDescTime()) return; // If error occurs, stop the method!
        Task newTask = new Event(taskDescription, time);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());
    }
}
