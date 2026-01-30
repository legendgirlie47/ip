public class TaskParser {
    public Task parse(String line) throws Exception {
        if (line == null) {
            throw new Exception("Null line");
        }

        String trimmed = line.trim();
        if (trimmed.isEmpty()) {
            throw new Exception("Empty line");
        }

        // split by " | " (with optional surrounding spaces)
        String[] parts = trimmed.split("\\s*\\|\\s*");

        // Minimum: TYPE | DONE | DESC
        if (parts.length < 3) {
            throw new Exception("Corrupted line: not enough parts");
        }

        String type = parts[0].trim();
        String donePart = parts[1].trim();
        String desc = parts[2].trim();

        if (!(donePart.equals("0") || donePart.equals("1"))) {
            throw new Exception("Corrupted done flag");
        }
        boolean isDone = donePart.equals("1");

        Task task;

        switch (type) {
            case "T":
                task = new ToDo(desc);
                break;

            case "D":
                if (parts.length < 4) {
                    throw new Exception("Corrupted deadline: missing /by");
                }
                String by = parts[3].trim();
                task = new Deadline(desc, by);
                break;

            case "E":
                if (parts.length < 5) {
                    throw new Exception("Corrupted event: missing /from or /to");
                }
                String from = parts[3].trim();
                String to = parts[4].trim();
                task = new Event(desc, from, to);
                break;

            default:
                throw new Exception("Unknown task type: " + type);
        }

        if (isDone) {
            task.markDone();
        }
        return task;
    }
}
