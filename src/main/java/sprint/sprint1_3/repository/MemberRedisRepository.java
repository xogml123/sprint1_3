package sprint.sprint1_3.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import sprint.sprint1_3.domain.Member;

@Repository
@RequiredArgsConstructor
public class MemberRedisRepository {

    public final RedisTemplate redisTemplate;


    public void put(Member member) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        String key = "member:" + String.valueOf(member.getId());
        hashOperations.put(key, "_class"
            , "sprint.sprint1_3.domain.Member");
        hashOperations.put(key, "id"
            , String.valueOf(member.getId()));
        hashOperations.put(key, "loginId"
            , String.valueOf(member.getLoginId()));
        hashOperations.put(key, "loginPassword"
            , String.valueOf(member.getLoginPassword()));
        hashOperations.put(key, "name"
            , String.valueOf(member.getName()));
    }

    public Optional<Member> findById(String id) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        Long idRead = (Long)hashOperations.get("member:" + id, "id");
        if (idRead == null) {
            return Optional.empty();
        }
        String loginId = (String)hashOperations.get("member:" + id, "loginId");
        String loginPassword = (String)hashOperations.get("member:" + id, "loginPassword");
        String name = (String)hashOperations.get("member:" + id, "name");
        return Optional.ofNullable(Member.builder().id(idRead)
            .loginId(loginId)
            .loginPassword(loginPassword)
            .name(name)
            .build());
    }


    public void delete(String id) {
        HashOperations hashOperations = redisTemplate.opsForHash();

        redisTemplate.delete("member:" + id);
    }
}
