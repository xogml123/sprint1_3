package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import sprint.sprint1_3.domain.Member;


@Getter
@Setter
@ToString
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

    public MemberDto() {
    }

    public MemberDto(Long id, String name, String loginId, String loginPassword) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }

    public static Member toEntity(MemberDto memberDto) {
        return Member.builder()
            .id(memberDto.getId())
            .name(memberDto.getName())
            .loginId(memberDto.getLoginId())
            .loginPassword(memberDto.getLoginPassword())
            .build();
    }
}
