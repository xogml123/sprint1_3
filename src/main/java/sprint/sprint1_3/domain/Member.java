package sprint.sprint1_3.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@ToString
@Slf4j
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String loginId;
    private String loginPassword;


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
}
