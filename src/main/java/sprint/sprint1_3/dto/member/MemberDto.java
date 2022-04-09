package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


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

}
