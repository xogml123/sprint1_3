package sprint.sprint1_3.repository;

import org.springframework.data.repository.CrudRepository;
import sprint.sprint1_3.domain.Member;

public interface MemberRedisRepositoryInterface extends CrudRepository<Member, Long> {

}
