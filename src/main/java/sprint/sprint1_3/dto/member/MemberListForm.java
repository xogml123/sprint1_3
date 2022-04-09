package sprint.sprint1_3.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberListForm {
    private Long id;
    private String name;

    private String loginId;
}
