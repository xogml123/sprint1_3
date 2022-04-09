package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import sprint.sprint1_3.domain.Gender;

@Getter
@Setter

public class MemberUpdateForm {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Range(min=20, max=60)
    private Integer age;


    @NotNull
    @NotBlank
    private String loginId;

    @NotNull
    @NotBlank
    private String loginPassword;


    /*@NotNull
    @Range(max=1000000)
    private Long payMoney;*/
    @NotNull
    private Gender gender;


/*public static Member toEntity(MemberUpdateForm memberUpdateForm) {
        return new Member(memberUpdateForm.getName(), memberUpdateForm.getAge(), memberUpdateForm.getPayMoney(), memberUpdateForm.getGender());
    }
*/
}
