import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    public static ArrayList<String> list = new ArrayList<String>();

    public static void startup() {
        String startupMsg = "    ____________________________________________________________\n"
                + "     Hello! I'm Duke\n"
                + "     What can I do for you?\n"
                + "    ____________________________________________________________\n";
        System.out.println(startupMsg);
    }

    // Echoes when an item is added
    public static void echo(String inputLine) {
        String echoMsg = "     ____________________________________________________________\n"
                + "     added: " + inputLine + "\n"
                + "     ____________________________________________________________\n";
        System.out.println(echoMsg);
    }

    public static void exit() {
        String exitMsg = "     ____________________________________________________________\n"
                + "     Bye. Hope to see you again soon!\n"
                + "     ____________________________________________________________\n";
        System.out.println(exitMsg);
    }

    public static void addToList(String inputLine) {
        list.add(inputLine);
        echo(inputLine);
    }

    public static void showList() {
        System.out.println("     ____________________________________________________________");
        for (int i = 0; i < list.size(); i++) {
            String msg = "     " + (i+1) + ". " + list.get(i);
            System.out.println(msg);
        }
        System.out.println("     ____________________________________________________________");
    }

    public static void handleInput() {
        Scanner input = new Scanner(System.in);
        String inputLine = input.nextLine();
        String[] inputArray = inputLine.split(" ");

        if (inputArray.length == 1) {

            if (inputLine.equals("bye")) {
                exit();
                return;
            } else if (inputLine.equals("list")) {
                showList();
            } else {
                addToList(inputLine);
            }
        } else {
            addToList(inputLine);
        }
        handleInput();
    }

    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        startup();
        handleInput();

    }
}
