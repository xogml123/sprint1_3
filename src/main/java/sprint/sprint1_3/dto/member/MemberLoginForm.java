package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginForm {

    @NotNull
    @NotBlank
    private String loginId;

    @NotNull
    @NotBlank
    private String loginPassword;

}
