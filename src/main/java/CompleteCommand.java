import java.util.ArrayList;

public class CompleteCommand extends Command {
    protected String taskNumStr;

    public CompleteCommand(String taskNumStr) {
        this.taskNumStr = taskNumStr;
    }

    @Override
    public void execute(TaskList tasks) {
        int taskNumInt = stringToInt(taskNumStr);
        if (taskNumInt == 0) return; // don't do anything if task number is invalid

        ArrayList<String> msg = new ArrayList<String>();

        Task currTask;
        try {
            currTask = tasks.getFromList(taskNumInt-1);
        } catch (IndexOutOfBoundsException e) {
            msg.add(taskNumInt + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            return;
        }

        if (currTask.isDone == true) {
            msg.add("Task " + taskNumInt + " is already completed! :)");
        } else {
            currTask.markAsDone();
            msg.add("Nice! I've marked this task as done:");
            msg.add("  " + currTask.getTask());
        }
        Ui.printMsg(msg);

    }
}
