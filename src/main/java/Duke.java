import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

//    private Ui ui;
//
//    public Duke() {
//        ui = new Ui();
//    }

    public static ArrayList<Task> list = new ArrayList<Task>();

    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        Ui.printMsg(msg);
        Storage.save(list);
    }

    public static void addToList(String command, String inputLine) {
        String taskDescription = inputLine.length() != command.length()  // If there are no characters after command,
                ? inputLine.substring(command.length()+1) : "";          // then description is empty

        // Consider using Case Statements
        if (command.equals("todo")) {
            addTodo(taskDescription);
        } else if (command.equals("event")) {
            addEvent(taskDescription);
        } else if (command.equals("deadline")) {
            addDeadline(taskDescription);
        }

        else {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Invalid command given!"
            ));
            Ui.printMsg(msg);
        }
    }

    public static void addTodo(String taskDescription) {
        if (taskDescription.length() == 0) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Please specify the task you want to add!"
            ));
            Ui.printMsg(msg);
            return;
        }
        Task newTask = new ToDo(taskDescription);
        list.add(newTask);

        Ui.echoAdd(newTask, list.size());
    }

    public static void addEvent(String taskDescription) {
        String dateTrigger = "/at";
        int dateIndex = taskDescription.indexOf(dateTrigger);
        String[] data = taskDescription.split(dateTrigger + " ");
        LocalDateTime time = (data.length != 1) ? readTime(data[1]) : null;

        // Error Checking
        boolean encounteredError = (taskDescription.length() == 0 || dateIndex == -1 || data.length < 2 || time == null);
        if (encounteredError) {
            ArrayList<String> msg = new ArrayList<String>();
            if (taskDescription.length() == 0) {
                msg.add("Please specify the task you want to add!");
            } else if (dateIndex == -1) {
                msg.add("Please add '/at <date>' after your task to specify the event date." );
            } else {
                msg.add("Please use the format 'DD/MM/YYYY HHmm'!");
            }
            Ui.printMsg(msg);
            return;

        } else {
            Task newTask = new Event(data[0], time);
            list.add(newTask);
            Ui.echoAdd(newTask, list.size());
        }
    }

    public static void addDeadline(String taskDescription) {
        String dateTrigger = "/by";
        int dateIndex = taskDescription.indexOf(dateTrigger);
        String[] data = taskDescription.split(dateTrigger + " ");
        LocalDateTime time = (data.length != 1) ? readTime(data[1]) : null;

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
            list.add(newTask);
            Ui.echoAdd(newTask, list.size());
        }
    }

    public static void removeTask(String taskNum) {
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
            list.get(taskNumInt-1); // Check if the task exists first
            msg.add("Noted. I've removed this task: ");
            msg.add("  " + list.get(taskNumInt-1).getTask());
            list.remove(taskNumInt-1);
            msg.add("Now you have " + list.size() + " tasks in the list.");
        }
        catch (IndexOutOfBoundsException e) {
            msg.add(taskNum + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            Ui.printMsg(msg);
            return;
        }

        Ui.printMsg(msg);
    }

    public static void showList() {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            Task currTask = list.get(i);
            msg.add( (i+1) + "."  + currTask.getTask() );
        }
        Ui.printMsg(msg);
    }

    public static void findString (String command, String inputLine) {
        String searchStr = inputLine.length() != command.length()  // If there are no characters after command,
                ? inputLine.substring(command.length()+1) : "";          // then description is empty
        ArrayList<String> itemsFound = new ArrayList<String>();
        ArrayList<String> msg = new ArrayList<String>();
        for (Task currTask : list) {
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

    // Need to find out how to only allow integers to pass thru :(
    public static void completeTask(String completedNum) {
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
            currTask = list.get(listNum-1);
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

    public static void handleInput(String inputLine) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                showList();
                break;
            case "done":
                completeTask(inputArray[1]);
                break;
            case "remove":
                removeTask(inputArray[1]);
                break;
            case "find":
                findString(command, inputLine);
                break;
            default:
                addToList(command, inputLine);
                break;
        }
    }



    public static LocalDateTime readTime(String timeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm", Locale.ENGLISH);
            LocalDateTime time = LocalDateTime.parse(timeStr, formatter);
//            System.out.println(time);
            return time;
        } catch(DateTimeParseException e) {
//            System.out.println("This format is not okay!");
            return null;
        }
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        list = Storage.load();

        boolean isExit = false;
        Scanner input = new Scanner(System.in);
        while (isExit == false) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equals("bye")) {
                    isExit = true;
                    exit();
                } else {
                    handleInput(inputLine);
                    Storage.save(list);
                }
            }
        }

    }
}
