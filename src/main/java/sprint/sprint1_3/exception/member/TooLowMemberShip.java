package sprint.sprint1_3.exception.member;

public class TooLowMemberShip extends Exception {

    public TooLowMemberShip() {
        super();
    }

    public TooLowMemberShip(String message) {
        super(message);
    }

    public TooLowMemberShip(String message, Throwable cause) {
        super(message, cause);
    }

    public TooLowMemberShip(Throwable cause) {
        super(cause);
    }
}
