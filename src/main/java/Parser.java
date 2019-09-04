public class Parser {
    public static void handleInput(String inputLine) {
        String[] inputArray = inputLine.split(" ");
        String command = inputArray[0];

        switch (command) {
            case "list":
                Duke.showList();
                break;
            case "done":
                Duke.completeTask(inputArray[1]);
                break;
            case "remove":
                Duke.removeTask(inputArray[1]);
                break;
            case "find":
                Duke.findString(command, inputLine);
                break;
            default:
                Duke.addToList(command, inputLine);
                break;
        }
    }
}
