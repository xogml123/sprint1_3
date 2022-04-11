package sprint.sprint1_3.repository;

import org.springframework.data.repository.CrudRepository;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.domain.MemberRedis;

public interface MemberRedisRepository extends CrudRepository<MemberRedis, String> {

}
