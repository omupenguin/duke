import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    public static Command handleInput(String inputLine, TaskList tasks) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                showList(tasks);
                break;
            case "done":
                completeTask(inputArray[1], tasks);
                break;
            case "remove":
                removeTask(inputArray[1], tasks);
                break;
            case "find":
                findString(command, inputLine, tasks);
                break;
            default:
                return addToList(command, inputLine, tasks);
        }
        return null;
    }

    public static Command addToList(String command, String inputLine, TaskList tasks) {
//        String taskDescription = inputLine.length() != command.length()  // If there are no characters after command,
//                ? inputLine.substring(command.length()+1) : "";          // then description is empty
        String taskDescription;
        Command commandToRun;

        try {
            taskDescription = inputLine.substring(command.length()+1);
            switch (command) {
                case "todo":
                    commandToRun = new AddTodoCommand(taskDescription);
                    return commandToRun;
                case "event":
                    commandToRun = new AddEventCommand(taskDescription);
                    return commandToRun;
                case "deadline":
                    addDeadline(taskDescription, tasks);
                    break;
            }
        } catch (IndexOutOfBoundsException e) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }
        return null;
    }

    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        //Storage.save(tasks); // Don't need to save since any previous commands are already saved
    }


    public static void addDeadline(String taskDescription, TaskList tasks) {
        String dateTrigger = "/by";
        int dateIndex = taskDescription.indexOf(dateTrigger);
        String[] data = taskDescription.split(dateTrigger + " ");
        LocalDateTime time = (data.length != 1) ? Time.readTime(data[1]) : null;

        // Error Checking
        boolean encounteredError = (taskDescription.length() == 0 || dateIndex == -1 || data.length < 2 || time == null);
        if (encounteredError) {
            ArrayList<String> msg = new ArrayList<String>();
            if (taskDescription.length() == 0) {
                msg.add("Please specify the task you want to add!");
            } else if (dateIndex == -1) {
                msg.add("Please add '/by <date>' after your task to specify the deadline date." );
            } else {
                msg.add("Please use the format 'DD/MM/YYYY HHmm'!");
            }
            Ui.printMsg(msg);
            return;

        } else {
            Task newTask = new Deadline(data[0], time);
            tasks.add(newTask);
            Ui.echoAdd(newTask, tasks.size());
        }
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

    public static void showList(TaskList tasks) {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            Task currTask = tasks.getFromList(i);
            msg.add( (i+1) + "."  + currTask.getTask() );
        }
        Ui.printMsg(msg);
    }

    public static void findString (String command, String inputLine, TaskList tasks) {
        String searchStr = inputLine.length() != command.length()  // If there are no characters after command,
                ? inputLine.substring(command.length()+1) : "";          // then description is empty
        ArrayList<String> itemsFound = new ArrayList<String>();
        ArrayList<String> msg = new ArrayList<String>();
        for (Task currTask : tasks.list) {
            String taskStr = currTask.getTask();
            if (taskStr.indexOf(searchStr) != -1) {
                itemsFound.add(taskStr);
            }
        }

        if (itemsFound.isEmpty()) {
            msg.add("There are no matching tasks in your list :(");
        } else {
            msg.add("Here are the matching tasks in your list:");
            for (int i = 0; i < itemsFound.size(); i++) {
                msg.add( (i+1) + "."  + itemsFound.get(i) );
            }
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
