package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import sprint.sprint1_3.domain.Gender;
import sprint.sprint1_3.domain.Member;

@Getter
@Setter
public class MemberJoinForm {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String loginId;

    @NotNull
    @NotBlank
    private String loginPassword;

    @NotNull
    @Range(min=20, max=60)
    private Integer age;

    /*@NotNull
    @Range(max=1000000)
    private Long payMoney;*/
    @NotNull
    private Gender gender;
    /*@NotNull
    private MemberShip memberShip;*/

    public static Member toEntity(MemberJoinForm memberJoinForm) {
        return new Member(memberJoinForm.getName(), memberJoinForm.getLoginId(), memberJoinForm.getLoginPassword()
            , memberJoinForm.getAge(), memberJoinForm.getGender());
    }

}
