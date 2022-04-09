package sprint.sprint1_3.exception.member;

public class NoSuchLoginId extends RuntimeException {

    public NoSuchLoginId() {
        super();
    }

    public NoSuchLoginId(String message) {
        super(message);
    }

    public NoSuchLoginId(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchLoginId(Throwable cause) {
        super(cause);
    }
}
