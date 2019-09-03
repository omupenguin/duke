import java.util.ArrayList;
import java.util.Arrays;

public class Ui {

    protected ArrayList<String> messageArray;

    // Print out message with formatting already done
    public static void printMsg(ArrayList<String> msg) {
        System.out.println("    ____________________________________________________________");
        for (String outputMsg : msg) {
            System.out.println("     " + outputMsg);
        }
        System.out.println("    ____________________________________________________________\n");
    }

    // Echoes when an item is added
    public static void echoAdd(Task currTask, int listSize) {
        ArrayList<String> msg = new ArrayList<String>(Arrays.asList(
                "Got it. I've added this task: ",
                "  " + currTask.getTask(),
                "Now you have " + listSize + " task(s) in the list."
        ));
        printMsg(msg);
    }
}
