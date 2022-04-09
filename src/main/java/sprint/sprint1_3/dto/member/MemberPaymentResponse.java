package sprint.sprint1_3.dto.member;

import lombok.Getter;
import lombok.Setter;
import sprint.sprint1_3.domain.MemberShip;

@Getter
@Setter
public class MemberPaymentResponse {
    private Long id;

    private Long payMoney;
    private MemberShip memberShip;
}
