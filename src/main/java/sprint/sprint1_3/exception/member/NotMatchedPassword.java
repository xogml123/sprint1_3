package sprint.sprint1_3.exception.member;

public class NotMatchedPassword extends RuntimeException {

    public NotMatchedPassword() {
        super();
    }

    public NotMatchedPassword(String message) {
        super(message);
    }

    public NotMatchedPassword(String message, Throwable cause) {
        super(message, cause);
    }

    public NotMatchedPassword(Throwable cause) {
        super(cause);
    }
}
