package sprint.sprint1_3.repository;


import java.util.List;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.domain.MemberRedis;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final EntityManager em;
    private final MemberRedisRepository memberRedisRepository;
    private final RedisTemplate redisTemplate;

    @Override
    public void save(Member member) {
        memberRedisRepository.save(
            new MemberRedis(String.valueOf(member.getId()), member.getName(), member.getLoginId(),
                member.getLoginPassword()));
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put(String.valueOf("members:loginpassword"), String.valueOf(member.getLoginId()),
            String.valueOf(member.getLoginPassword()));
        em.persist(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        Optional<MemberRedis> memberInRedis = memberRedisRepository.findById(String.valueOf(id));
        if (memberInRedis.isPresent()) {
            return memberInRedis;
        } else {
            Optional<Member> memberInDB = findById(id);
            if (memberInDB.isPresent()) {
                memberRedisRepository.save(memberInDB.get());
                return memberRedisRepository.findById(String.valueOf(id));
            } else {
                return memberInDB;
            }
        }
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    @Override
    public List<Member> findByLoginId(String loginId) {
        return em.createQuery("select m from Member m where m.loginId = :loginId",
                Member.class)
            .setParameter("loginId", loginId)
            .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Member> member = findById(id);
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (member.isPresent()) {
            memberRedisRepository.deleteById(String.valueOf(id));
            Member memberExist = member.get();
            hashOperations.delete("members:loginpassword", String.valueOf(memberExist.getLoginId()));
            em.remove(memberExist);
        }
    }

}