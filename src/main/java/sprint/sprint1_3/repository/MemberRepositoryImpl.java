package sprint.sprint1_3.repository;


import java.util.ArrayList;
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
    private final RedisTemplate redisTemplate;

    @Override
    public void save(Member member) {
        em.persist(member);
    }



    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));

    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    @Override
    public List<Member> findByLoginId(String loginId) {
        return em.createQuery(
                    "select m from Member m where m.loginId = :loginId",
                    Member.class)
                .setParameter("loginId", loginId)
                .getResultList();

    }

    @Override
    public void deleteById(Long id) {
        Optional<Member> member = findById(id);
        if (member.isPresent()) {
            em.remove(member.get());
        }
    }

}