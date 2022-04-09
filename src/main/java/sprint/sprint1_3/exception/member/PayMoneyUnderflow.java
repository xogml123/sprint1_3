package sprint.sprint1_3.exception.member;

public class PayMoneyUnderflow extends RuntimeException {

    public PayMoneyUnderflow() {
        super();
    }

    public PayMoneyUnderflow(String message) {
        super(message);
    }

    public PayMoneyUnderflow(String message, Throwable cause) {
        super(message, cause);
    }

    public PayMoneyUnderflow(Throwable cause) {
        super(cause);
    }
}
