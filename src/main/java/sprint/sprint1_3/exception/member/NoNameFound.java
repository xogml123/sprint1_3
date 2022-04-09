package sprint.sprint1_3.exception.member;

public class NoNameFound extends RuntimeException {

    public NoNameFound() {
        super();
    }

    public NoNameFound(String message) {
        super(message);
    }

    public NoNameFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNameFound(Throwable cause) {
        super(cause);
    }

}
