import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    public static ArrayList<Task> list = new ArrayList<Task>();

    // Print out message with formatting already done
    public static void printMsg(ArrayList<String> msg) {
        System.out.println("    ____________________________________________________________\n");
        for (String outputMsg : msg) {
            System.out.println("     " + outputMsg);
        }
        System.out.println("    ____________________________________________________________\n");
    }

    public static void startup() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Hello! I'm Duke!",
                "What can I do for you?"
        ));
        printMsg(msg);
    }

    // Echoes when an item is added
    public static void echo(String inputLine) {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "added: " + inputLine
        ));
        printMsg(msg);
    }

    public static void exit() {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Bye. Hope to see you again soon!"
        ));
        printMsg(msg);
    }

    public static void addToList(String inputLine) {
        Task newTask = new Task(inputLine);
        list.add(newTask);
        echo(inputLine);
    }

    public static void showList() {
        ArrayList<String> msg = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            msg.add( (i+1) + ". " + (list.get(i)).description );
        }
        printMsg(msg);
    }

    public static void handleInput(String inputLine) {
        String[] inputArray = inputLine.split(" ");

        if (inputArray.length == 1) {

            if (inputLine.equals("list")) {
                showList();
            } else {
                addToList(inputLine);
            }

        } else {
            addToList(inputLine);
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
            }
        }

    }
}
