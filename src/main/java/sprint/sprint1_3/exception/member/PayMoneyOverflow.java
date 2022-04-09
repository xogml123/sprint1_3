package sprint.sprint1_3.exception.member;

public class PayMoneyOverflow extends RuntimeException {

    public PayMoneyOverflow() {
        super();
    }

    public PayMoneyOverflow(String message) {
        super(message);
    }

    public PayMoneyOverflow(String message, Throwable cause) {
        super(message, cause);
    }

    public PayMoneyOverflow(Throwable cause) {
        super(cause);
    }
}
