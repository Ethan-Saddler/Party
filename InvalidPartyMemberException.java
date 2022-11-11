/**
 * @author esaddler3
 * @version 1.0
 * Unchecked exception class for when there is an invalid party member.
 */
public class InvalidPartyMemberException extends RuntimeException {
    /**
     * Exception constructor that creates a exception with specified message.
     * @param message String of exception message.
     */
    public InvalidPartyMemberException(String message) {
        super(message);
    }

    /**
     * No arg constructor that has default message.
     */
    public InvalidPartyMemberException() {
        super("Invalid Party Member!");
    }
}