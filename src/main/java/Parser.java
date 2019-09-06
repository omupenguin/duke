import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public static Command handleInput(String inputLine, TaskList tasks) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                return new ShowListCommand();
            case "done":
                completeTask(inputArray[1], tasks);
                break;
            case "remove":
                removeTask(inputArray[1], tasks);
                break;
            case "find":
                return new FindStringCommand(inputLine);
            default:
                return addToList(command, inputLine);
        }
        return new ErrorCommand();
    }

    public static Command addToList(String command, String inputLine) {

        String taskDescription;
        Command commandToRun = new ErrorCommand();

        try {
            taskDescription = inputLine.substring(command.length()+1);
            switch (command) {
                case "todo":
                    commandToRun = new AddTodoCommand(taskDescription);
                case "event":
                    commandToRun = new AddEventCommand(taskDescription);
                case "deadline":
                    commandToRun = new AddDeadlineCommand(taskDescription);
            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }

        return commandToRun;
    }

    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        //Storage.save(tasks); // Don't need to save since any previous commands are already saved
    }

    public static void removeTask(String taskNum, TaskList tasks) {
        // Duplicate code with completeTask
        // Can make a class called "parseInt" or something :D
        int taskNumInt = 0;
        ArrayList<String> msg = new ArrayList<String>();

        try {
            taskNumInt = Integer.parseInt(taskNum);
        }
        catch (NumberFormatException e) {
            msg.add(taskNum + " is not a number. Please use a number instead!");
            Ui.printMsg(msg);
            return;
        }

        try {
            tasks.getFromList(taskNumInt-1); // Check if the task exists first
            msg.add("Noted. I've removed this task: ");
            msg.add("  " + tasks.getFromList(taskNumInt-1).getTask());
            tasks.removeFromList(taskNumInt-1);
            msg.add("Now you have " + tasks.size() + " tasks in the list.");
        }
        catch (IndexOutOfBoundsException e) {
            msg.add(taskNum + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            return;
        }

        Ui.printMsg(msg);
    }

    public static void completeTask(String completedNum, TaskList tasks) {
        int listNum = 0;
        ArrayList<String> msg = new ArrayList<String>();

        try {
            listNum = Integer.parseInt(completedNum);
        }
        catch (NumberFormatException e) {
            msg.add(completedNum + " is not a number. Please use a number instead!");
            Ui.printMsg(msg);
            return;
        }

        Task currTask = new Task("");
        try {
            currTask = tasks.getFromList(listNum-1);
        } catch (IndexOutOfBoundsException e) {
            msg.add(completedNum + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            return;
        }

        if (currTask.isDone == true) {
            msg.add("Task " + listNum + " is already completed! :)");
        } else {
            currTask.markAsDone();
            msg.add("Nice! I've marked this task as done:");
            msg.add("  " + currTask.getTask());
        }
        Ui.printMsg(msg);
    }
}
