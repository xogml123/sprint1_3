package sprint.sprint1_3.repository;

import java.util.List;
import java.util.Optional;
import sprint.sprint1_3.domain.Member;

public interface MemberRepository {

    public void save(Member member);
    public Optional<Member> findById(Long id);
    public List<Member> findAll();
    public List<Member> findByLoginId(String name);

  //  public void update(Long id, String Name, String loginId, String loginPassword);

    public void deleteById(Long id);

}
