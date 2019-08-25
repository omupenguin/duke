import java.util.Scanner;

public class Duke {

    public static void startup() {
        String startupMsg = "    ____________________________________________________________\n"
                + "     Hello! I'm Duke\n"
                + "     What can I do for you?\n"
                + "    ____________________________________________________________\n";
        System.out.println(startupMsg);
    }

    public static void echo(String inputLine) {
        String echoMsg = "     ____________________________________________________________\n"
                + "     " + inputLine + "\n"
                + "     ____________________________________________________________\n";
        System.out.println(echoMsg);
    }

    public static void exit() {
        String exitMsg = "     ____________________________________________________________\n"
                + "     Bye. Hope to see you again soon!\n"
                + "     ____________________________________________________________\n";
        System.out.println(exitMsg);
    }

    public static void handleInput() {
        Scanner input = new Scanner(System.in);
        String inputLine = input.nextLine();

        if (inputLine.equals("bye")) {
            exit();
        } else {
            echo(inputLine);
            handleInput();
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
        handleInput();

    }
}
