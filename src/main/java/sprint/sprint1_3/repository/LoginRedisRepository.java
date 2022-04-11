package sprint.sprint1_3.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import sprint.sprint1_3.domain.Member;

@Repository
@RequiredArgsConstructor
public class LoginRedisRepository {


    public final RedisTemplate redisTemplate;


    public void put(Member member) {
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.delete("member:loginpassword", member.getLoginId());
        hashOperations.put("member:loginpassword", member.getLoginId(), member.getLoginPassword());
    }

    public Optional<String> findPasswordByLoginId(String loginId) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return Optional.ofNullable((String)hashOperations.get("member:loginpassword", loginId));
    }
}
