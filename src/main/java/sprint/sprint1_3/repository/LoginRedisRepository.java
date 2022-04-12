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
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        hashOperations.delete(getKey(), loginId);
        hashOperations.put(getKey(), loginId, loginPassword);
    }

    public Optional<String> findPasswordByLoginId(String loginId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return Optional.ofNullable((String)hashOperations.get(getKey(), loginId));
    }

    public void deleteByLoginId(String loginID) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        hashOperations.delete(getKey(), loginID);
    }

    private String getKey() {
        return "member:loginpassword";
    }


}
