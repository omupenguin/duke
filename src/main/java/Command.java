import java.util.ArrayList;

public abstract class Command {

    public abstract void execute(TaskList task);

    public int stringToInt(String str) {
        int newInt = 0;

        try {
            newInt = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            ArrayList<String> msg = new ArrayList<String>();
            msg.add(str + " is not a number. Please use a number instead!");
            Ui.printMsg(msg);
        }

        return newInt;
    }

    public void extractDesc(String inputLine) {

    }
}
