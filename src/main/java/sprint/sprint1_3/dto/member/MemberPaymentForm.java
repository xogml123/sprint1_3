package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import sprint.sprint1_3.domain.MemberShip;

@Getter
@Setter
public class MemberPaymentForm {

    private Long id;

    private Long payMoney;
    private MemberShip memberShip;

    @NotNull
    //@Range(max=100000)
    private
    Long payment;

    public MemberPaymentForm(Long id, Long payMoney, MemberShip memberShip) {
        this.id = id;
        this.payMoney = payMoney;
        this.memberShip = memberShip;
    }
}
