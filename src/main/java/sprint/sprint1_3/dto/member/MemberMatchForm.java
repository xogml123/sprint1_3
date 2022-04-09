package sprint.sprint1_3.dto.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import sprint.sprint1_3.domain.Gender;

@Getter
@Setter
public class MemberMatchForm {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @Range(min=20, max=60)
    private Integer age;

    public MemberMatchForm(Long id, String name, Integer age, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    @NotNull
    private Gender gender;


}
