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


    public void put(String loginId, String loginPassword) {
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.delete("member:loginpassword", loginId);
        hashOperations.put("member:loginpassword", loginId, loginPassword);
    }

    public Optional<String> findPasswordByLoginId(String loginId) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return Optional.ofNullable((String)hashOperations.get("member:loginpassword", loginId));
    }

    public void deleteByLoginId(String loginID) {
        HashOperations hashOperations = redisTemplate.opsForHash();

        hashOperations.delete("members:loginpassword", loginID);
    }
}
