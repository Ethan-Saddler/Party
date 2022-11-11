/**
 * @author esaddler3
 * @version 1.0
 * Checked exception class for when there is not quest found.
 */
public class QuestNotFoundException extends Exception {
    /**
     * Exception constructor that creates a exception with specified message.
     * @param message String of exception message.
     */
    public QuestNotFoundException(String message) {
        super(message);
    }

    /**
     * No arg constructor that has default message.
     */
    public QuestNotFoundException() {
        super("Selected Quest Not Found");
    }
}