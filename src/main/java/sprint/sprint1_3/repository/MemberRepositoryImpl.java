package sprint.sprint1_3.repository;


import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import sprint.sprint1_3.domain.Member;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
    }

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
        return em.createQuery("select m from Member m where m.loginId = :loginId",
                Member.class)
            .setParameter("loginId", loginId)
            .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Optional<Member> member = findById(id);
        member.ifPresent(m->em.remove(m));
    }

}