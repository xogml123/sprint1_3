package sprint.sprint1_3.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("member")
@Builder
public class MemberRedis {
    @Id
    private String id;

    private String name;

    private String loginId;
    private String loginPassword;

    public MemberRedis(String id, String name, String loginId, String loginPassword) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPassword = loginPassword;
    }
}
