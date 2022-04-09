package sprint.sprint1_3.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import sprint.sprint1_3.dto.member.MemberDto;
import sprint.sprint1_3.exception.member.NotMatchedPassword;

@Entity
@Getter
@ToString
@Builder
@Slf4j
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String loginId;
    private String loginPassword;

    public Member() {
    }

    public Member(Long id, String name, String loginId, String loginPassword) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateInfo(Long id, String name, String loginId,
        String loginPassword) {
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }

    public String getSecretName() {
        return name.substring(0, 1) + "*".repeat(name.substring(1).length());
    }

    public void validatePassword(String loginPassword) {
        if (!this.loginPassword.equals(loginPassword)){
            throw new NotMatchedPassword("비밀번호가 일치하지 않습니다.");
        }
    }

    public static MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getLoginId(),
            member.loginPassword);
    }
}
