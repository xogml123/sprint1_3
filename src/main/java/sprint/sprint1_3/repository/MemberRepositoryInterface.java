package sprint.sprint1_3.repository;

import java.util.List;
import java.util.Optional;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.dto.member.MemberUpdateForm;

public interface MemberRepositoryInterface {

    public void save(Member member);
    public Member findOne(Long id);
    public List<Member> findAll();
    public Optional<Member> findByName(String name);

    public void updateOne(Long id, MemberUpdateForm memberUpdateForm);

    public void deleteOne(Long id);

    public List<Member> findMatchAll(Member member);
}
