public class AddDeadlineCommand extends AddCommand {

    public AddDeadlineCommand(String taskDescription) {
        super(taskDescription); // taskDescription includes the task info and date
        time = null;
        dateTrigger = "/by";
    }

    @Override
    public void execute(TaskList tasks) {
        if (!splitDescTime()) return; // If error occurs, stop the method!
        Task newTask = new Deadline(taskDescription, time);
        tasks.add(newTask);
        Ui.echoAdd(newTask, tasks.size());
    }

}
