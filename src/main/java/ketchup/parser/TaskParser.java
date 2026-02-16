package ketchup.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import ketchup.tasks.Deadline;
import ketchup.tasks.Event;
import ketchup.tasks.Task;
import ketchup.tasks.ToDo;

/**
 * Responsible for reconstructing {@link Task} objects from stored text lines.
 * <p>
 * Each input line must follow the format produced by
 * {@code Task#toFileString()}, using the delimiter {@code |}.
 * The parser validates the structure of the line and recreates
 * the appropriate concrete {@link Task} subtype.
 */
public class TaskParser {

    /**
     * Parses a single line from the save file into a {@link Task}.
     * <p>
     * Expected format:
     * <pre>
     * TYPE | DONE | DESCRIPTION [| additional fields...]
     * </pre>
     * where:
     * <ul>
     *   <li>{@code T} represents a {@link ToDo}</li>
     *   <li>{@code D} represents a {@link Deadline}</li>
     *   <li>{@code E} represents an {@link Event}</li>
     *   <li>{@code DONE} is {@code 0} (not done) or {@code 1} (done)</li>
     * </ul>
     *
     * @param line a line of text read from persistent storage
     * @return the reconstructed {@link Task} object
     * @throws Exception if the line is null, empty, corrupted,
     *                   improperly formatted, or contains invalid date-time data
     */
    public Task parse(String line) throws Exception {

        assert line != null : "Input line must not be null";

        validateLine(line);

        String[] parts = splitLine(line);
        assert parts != null : "Split parts must not be null";

        validateBasicFormat(parts);

        String type = parts[0].trim();
        assert type != null : "Task type must not be null";

        boolean isDone = parseDoneFlag(parts[1]);

        String desc = parts[2].trim();
        assert desc != null : "Task description must not be null";

        Task task = createTask(type, desc, parts);
        assert task != null : "Created task must not be null";

        if (isDone) {
            task.markDone();
        }

        return task;
    }

    /**
     * Validates that the input line is non-null and non-empty.
     *
     * @param line the raw input line
     * @throws Exception if the line is null or empty
     */
    private void validateLine(String line) throws Exception {
        if (line == null) {
            throw new Exception("Null line");
        }

        if (line.trim().isEmpty()) {
            throw new Exception("Empty line");
        }
    }

    /**
     * Splits a storage line into components using {@code |} as delimiter.
     *
     * @param line the raw input line
     * @return an array of parsed components
     */
    private String[] splitLine(String line) {
        return line.trim().split("\\s*\\|\\s*");
    }

    /**
     * Ensures that the line contains the minimum required fields.
     *
     * @param parts the split components of the input line
     * @throws Exception if the minimum format is not satisfied
     */
    private void validateBasicFormat(String[] parts) throws Exception {
        if (parts.length < 3) {
            throw new Exception("Corrupted line: not enough parts");
        }
    }

    /**
     * Parses and validates the done flag field.
     *
     * @param donePart the raw done flag component
     * @return {@code true} if the task is marked as done, {@code false} otherwise
     * @throws Exception if the done flag is invalid
     */
    private boolean parseDoneFlag(String donePart) throws Exception {
        String trimmed = donePart.trim();

        if (!(trimmed.equals("0") || trimmed.equals("1"))) {
            throw new Exception("Corrupted done flag");
        }

        return trimmed.equals("1");
    }

    /* ================= TASK CREATION ================= */

    /**
     * Creates a concrete {@link Task} based on its type identifier.
     *
     * @param type the task type identifier
     * @param desc the task description
     * @param parts the split storage fields
     * @return the constructed {@link Task}
     * @throws Exception if the task type is unknown or corrupted
     */
    private Task createTask(String type, String desc, String[] parts)
            throws Exception {

        switch (type) {
            case "T":
                return createTodo(desc);

            case "D":
                return createDeadline(desc, parts);

            case "E":
                return createEvent(desc, parts);

            default:
                throw new Exception("Unknown task type: " + type);
        }
    }

    /**
     * Creates a {@link ToDo} task.
     *
     * @param desc the task description
     * @return a new {@link ToDo} instance
     */
    private Task createTodo(String desc) {
        Task task = new ToDo(desc);
        assert task != null : "ToDo task creation failed";
        return task;
    }

    /**
     * Creates a {@link Deadline} task from stored fields.
     *
     * @param desc the task description
     * @param parts the split storage fields
     * @return a new {@link Deadline} instance
     * @throws Exception if deadline data is missing or invalid
     */
    private Task createDeadline(String desc, String[] parts)
            throws Exception {

        if (parts.length < 4) {
            throw new Exception("Corrupted deadline: missing /by");
        }

        String by = parts[3].trim();

        try {
            LocalDateTime byTime =
                    LocalDateTime.parse(by, Task.getDateFormat());
            assert byTime != null : "Parsed deadline time must not be null";

            Task task = new Deadline(desc, byTime);
            assert task != null : "Deadline task creation failed";

            return task;

        } catch (DateTimeParseException e) {
            throw new Exception("Corrupted line: invalid DateTime format.");
        }
    }

    /**
     * Creates an {@link Event} task from stored fields.
     *
     * @param desc the task description
     * @param parts the split storage fields
     * @return a new {@link Event} instance
     * @throws Exception if event data is missing or invalid
     */
    private Task createEvent(String desc, String[] parts)
            throws Exception {

        if (parts.length < 5) {
            throw new Exception("Corrupted event: missing /from or /to");
        }

        String from = parts[3].trim();
        String to = parts[4].trim();

        try {
            LocalDateTime fromTime =
                    LocalDateTime.parse(from, Task.getDateFormat());
            LocalDateTime toTime =
                    LocalDateTime.parse(to, Task.getDateFormat());

            assert fromTime != null : "Parsed event start must not be null";
            assert toTime != null : "Parsed event end must not be null";

            Task task = new Event(desc, fromTime, toTime);
            assert task != null : "Event task creation failed";

            return task;

        } catch (DateTimeParseException e) {
            throw new Exception("Corrupted line: invalid DateTime format.");
        }
    }
}
