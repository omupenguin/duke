public class Parser {

    public static void handleInput(String inputLine, TaskList tasks) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                Duke.showList(tasks);
                break;
            case "done":
                Duke.completeTask(inputArray[1], tasks);
                break;
            case "remove":
                Duke.removeTask(inputArray[1], tasks);
                break;
            case "find":
                Duke.findString(command, inputLine, tasks);
                break;
            default:
                Duke.addToList(command, inputLine, tasks);
                break;
        }
    }
}
