import java.util.ArrayList;
import java.util.Arrays;

/**
 * Ui deals with interactions with the user.
 */
public class Ui {

    protected ArrayList<String> messageArray;

    public static void showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
    }

    /**
     * This method prints the strings of text from 'msg' with the proper format. Each element
     * from 'msg' is a line of text to be printed.
     * @param msg ArrayList of strings containing the messages to be printed.
     */
    public static void printMsg(ArrayList<String> msg) {
        System.out.println("    ____________________________________________________________");
        for (String outputMsg : msg) {
            System.out.println("     " + outputMsg);
        }
        System.out.println("    ____________________________________________________________\n");
    }

    // Echoes when an item is added

    /**
     * This method prints the details of the specified task and specified TaskList size.
     * <p>
     *     This method is typically called when a task is created, so that the user can
     *     check the details of the created task.
     * </p>
     * @param currTask Task to be printed.
     * @param listSize Size of the TaskList.
     */
    public static void echoAdd(Task currTask, int listSize) {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Got it. I've added this task: ",
                "  " + currTask.getTask(),
                "Now you have " + listSize + " task(s) in the list."
        ));
        printMsg(msg);
    }

    public static void showLine() {
        System.out.println("    ____________________________________________________________");
    }
}
