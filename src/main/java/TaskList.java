import java.util.ArrayList;

/**
 * TaskList stores tasks as a ArrayList and handles methods that interacts with the list.
 */
public class TaskList {
    protected ArrayList<Task> list;

    /**
     * Creates an instance of TaskList with task data from the specified ArrayList of Task.
     * @param importTaskList ArrayList of Task that is
     */
    public TaskList(ArrayList<Task> importTaskList) {
        this.list = importTaskList;
    }

    public void add(Task newTask) { list.add(newTask); }

    public int size() { return list.size(); }

    /**
     * Returns a task corresponding to the index in 'list'.
     * @param index Index to access the desired task in list.
     * @return Task to be retrieved.
     */
    public Task getFromList(int index) { return list.get(index); }

    public void removeFromList(int index) { list.remove(index); }
}
