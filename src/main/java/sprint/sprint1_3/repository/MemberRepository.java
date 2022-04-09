package sprint.sprint1_3.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import sprint.sprint1_3.domain.Gender;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.domain.MemberShip;
import sprint.sprint1_3.domain.QMember;
import sprint.sprint1_3.dto.member.MemberUpdateForm;

@Repository
public class MemberRepository implements MemberRepositoryInterface {

    @PersistenceContext
    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }
    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.ofNullable(em.createQuery("select m from Member m where m.name = :name",
                Member.class)
            .setParameter("name", name)
            .getSingleResult());
    }

    @Override
    public void deleteOne(Long id) {
        Member member = findOne(id);

        em.remove(member);
    }

    @Override
    public void updateOne(Long id, MemberUpdateForm memberUpdateForm) {
        Member one = findOne(id);
    }


    @Override
    public List<Member> findMatchAll(Member member) {


        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        Member findMember = em.find(Member.class, member.getId());
        List<Member> members = new ArrayList<>();
        JPAQuery<Member> jpaQueryMembers = jpaQueryFactory
            .select(QMember.member)
            .from(QMember.member)
            .where(matchingAge(findMember.getAge(), findMember.getGender()),
                isGender(findMember.getGender()));
        if (member.getMemberShip().equals(MemberShip.SILVER)) {
            members.add(jpaQueryMembers.fetchFirst());
        } else if (member.getMemberShip().equals(MemberShip.GOLD)) {
            members = jpaQueryMembers.fetch();
        }
        return members;
    }

    private BooleanExpression matchingAge(int age, Gender gender) {

        if (gender.equals(Gender.MALE)) {
            return QMember.member.age.loe(age);
        }
        return QMember.member.age.goe(age);
    }

    private BooleanExpression isGender(Gender gender) {

        return QMember.member.gender.ne(gender);
    }
}