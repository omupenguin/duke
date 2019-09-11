import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Storage handles the saving and loading of data from ./data/duke.txt.
 */
public class Storage {

    /**
     * Returns a TaskList containing tasks from a save file (if available),
     * else returns an empty TaskList.
     * <p>
     *     This method first tries to read from ./data/duke.txt. For every line
     *     in the file, this method checks the type of task stored, and then converts
     *     them into a task accordingly and stores into a TaskList.
     * </p>
     * <p>
     *     If ./data/duke.txt is not found, an empty TaskList will be returned.
     * </p>
     * <p>
     *     If an error occurs while reading from ./data/duke.txt, exit duke.
     * </p>
     * @return TaskList containing data (if any) from ./data/duke.txt.
     */
    public static ArrayList<Task> load() {
        ArrayList<Task> list = new ArrayList<Task>();

        Ui.showWelcome();
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
                    newTask = new Event(inArray[2], Time.readTime(inArray[3])); //TODO: Update readTime
                } else if (type.equals("D")) {
                    newTask = new Deadline(inArray[2], Time.readTime(inArray[3]));
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
            Parser.exit(); // TODO: Find out what is supposed to happen here
        }

        Ui.printMsg(msg);
        return list;
    }

    /**
     * This method takes and writes the information of the tasks
     *  within the specified ArrayList and into a file ./data/duke.txt.
     *  <p>
     *      If an error occurs while writing to the file, the method stops running.
     *  </p>
     * @param list An Arraylist containing the tasks to be saved.
     */
    public static void save(ArrayList<Task> list) {
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

}
