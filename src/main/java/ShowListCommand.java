import java.util.ArrayList;

public class ShowListCommand extends Command {

    @Override
    public void execute(TaskList tasks) {

            ArrayList<String> msg = new ArrayList<String>();
            msg.add("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                Task currTask = tasks.getFromList(i);
                msg.add( (i+1) + "."  + currTask.getTask() );
            }
            Ui.printMsg(msg);

    }
}
