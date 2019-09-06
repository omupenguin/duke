import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Duke {

    private TaskList tasks;

    public Duke() {
        tasks = new TaskList(Storage.load());
    }

    public void run() {
//        Ui.showWelcome(); // inside Storage
        boolean isExit = false;
        Scanner input = new Scanner(System.in); // TODO: Add to Ui instead?
        while (isExit == false) {
            if (input.hasNextLine()) {
                String inputLine = input.nextLine();
                if (inputLine.equals("bye")) {
                    isExit = true;
                    Parser.exit();
                } else {
                    Command c = Parser.handleInput(inputLine, tasks);
                    c.execute(tasks);
                    Storage.save(tasks.list);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
