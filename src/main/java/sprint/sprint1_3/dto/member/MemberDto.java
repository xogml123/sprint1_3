package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import sprint.sprint1_3.domain.Gender;
import sprint.sprint1_3.domain.MemberShip;


@Getter
@Setter
public class MemberDto {

    private Long id;
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

    @NotNull
    @Range(max=1000000)
    private Long payMoney;
    @NotNull
    private Gender gender;
    @NotNull
    private MemberShip memberShip;

    public MemberDto(Long id, String name, Integer age, Long payMoney,
        Gender gender, MemberShip memberShip) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.payMoney = payMoney;
        this.gender = gender;
        this.memberShip = memberShip;
    }

  /*  public static Member toEntity(MemberDto memberDto) {
        return new Member(memberDto.getName(), memberDto.getAge(), memberDto.getPayMoney(), memberDto.getGender(), memberDto.getMemberShip());
    }*/

}
