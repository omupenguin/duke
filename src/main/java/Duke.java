import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    public static ArrayList<Task> list = new ArrayList<Task>();

    // Print out message with formatting already done
    public static void printMsg(ArrayList<String> msg) {
        System.out.println("    ____________________________________________________________");
        for (String outputMsg : msg) {
            System.out.println("     " + outputMsg);
        }
        System.out.println("    ____________________________________________________________\n");
    }

    public static void startup() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Hello! I'm Duke! I help keep track of your tasks!",
                "What can I do for you?"
        ));

        try {
            FileReader inFile = new FileReader("./data/duke.txt");
            BufferedReader inStream = new BufferedReader(inFile);
            msg.add("Your save data has been loaded :)");
            String inLine;
//            System.out.println("Trying to load file now..."); // DEBUG

            while ((inLine = inStream.readLine()) != null) {
                String[] inArray = inLine.split(" \\| ");
//                System.out.println(("Adding a task...")); // DEBUG
//                System.out.println(Arrays.toString(inArray)); // DEBUG
                String type = inArray[0];
                Task newTask = null;

                if (type.equals("T")) {
                    newTask = new ToDo(inArray[2]);
                } else if (type.equals("E")) {
                    newTask = new Event(inArray[2], readTime(inArray[3]));
                } else if (type.equals("D")) {
                    newTask = new Deadline(inArray[2], readTime(inArray[3]));
                }

                if (inArray[1].equals("1")) {
                    newTask.markAsDone();
                }
                list.add(newTask);
            }

        } catch (FileNotFoundException e) {
            // exception handling
//            System.out.println("*** File not found :( ***");
            msg.add("Looks like it's your first time, let me create a save file for you :)");
        } catch (IOException e) {
            // exception handling
            System.out.println("*** there was an error reading duke.txt ***");
        }

        printMsg(msg);
    }

    // Echoes when an item is added
    public static void echoAdd(Task currTask) {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Got it. I've added this task: ",
                "  " + currTask.getTask(),
                "Now you have " + list.size() + " task(s) in the list."
        ));
        printMsg(msg);
    }

    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        printMsg(msg);
        save();
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
            printMsg(msg);
        }
    }

    public static void addTodo(String taskDescription) {
        if (taskDescription.length() == 0) {
            ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                    "Please specify the task you want to add!"
            ));
            printMsg(msg);
            return;
        }
        Task newTask = new ToDo(taskDescription);
        list.add(newTask);

        echoAdd(newTask);
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
            printMsg(msg);
            return;

        } else {
            Task newTask = new Event(data[0], time);
            list.add(newTask);
            echoAdd(newTask);
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
            printMsg(msg);
            return;

        } else {
            Task newTask = new Deadline(data[0], time);
            list.add(newTask);
            echoAdd(newTask);
        }
    }

    public static void showList() {
        ArrayList<String> msg = new ArrayList<String>();
        msg.add("Here are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            Task currTask = list.get(i);
            msg.add( (i+1) + "."  + currTask.getTask() );
        }
        printMsg(msg);
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

        printMsg(msg);
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
            printMsg(msg);
            return;
        }

        // Failsafe in case zero or a number larger than provided tasks is given
//        if (listNum == 0 || listNum > list.size()) {
//            msg.add("There is no task associated to that number... yet! :)");
//            printMsg(msg);
//            return;
//        }
        Task currTask = new Task("");
        try {
            currTask = list.get(listNum-1);
        } catch (IndexOutOfBoundsException e) {
            msg.add(completedNum + " is not associated to any task number.");
            msg.add("Use 'list' to check the tasks that are here first!");
            printMsg(msg);
            return;
        }


        if (currTask.isDone == true) {
            msg.add("Task " + listNum + " is already completed! :)");
        } else {
            currTask.markAsDone();
            msg.add("Nice! I've marked this task as done:");
            msg.add("  " + currTask.getTask());
        }
        printMsg(msg);
    }

    public static void handleInput(String inputLine) {
        String[] inputArray = inputLine.split(" ");

        // Some improvements that can be made:
        // We can ignore the length if we can ignore the rest of the contents after the
        // first word. So just check inputArray[0] and determine function from there.
        if (inputArray.length == 1) {

            if (inputLine.equals("list")) {
                showList();
            } else {
                addToList(inputArray[0], inputLine);
            }

        } else if (inputArray.length == 2) {

            if (inputArray[0].equals("done")) {
//                if (inputArray.length != 2) {
//                    ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
//                            "Sorry, I only except this command in the format 'done <number>'. Any extra characters will not work. :("
//                    ));
//                    printMsg(msg);
//                }
                completeTask(inputArray[1]);
            } else if (inputArray[0].equals("find")) {
                findString(inputArray[0], inputLine);
            } else {
                addToList(inputArray[0], inputLine);
            }
        } else if (inputArray[0].equals("find")) {
            findString(inputArray[0], inputLine);
        } else {
            addToList(inputArray[0], inputLine);
        }
    }

    public static void save() {
        try(FileWriter file = new FileWriter("./data/duke.txt")) {
            for (Task currTask : list) {
                String fileContent = currTask.formatSave();
                file.write(fileContent);
                file.write(System.lineSeparator());
            }
//            System.out.println("saved"); // DEBUG
        } catch (IOException e) {
            System.out.println("***Error writing to duke.txt***");
            // exception handling
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

    public static LocalDateTime importTime(String timeStr) {
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

        startup();

        boolean isExit = false;
        while (isExit == false) {
            Scanner input = new Scanner(System.in);
            String inputLine = input.nextLine();
            if (inputLine.equals("bye")) {
                isExit = true;
                exit();
            } else {
                handleInput(inputLine);
                save();
            }
        }

    }
}
