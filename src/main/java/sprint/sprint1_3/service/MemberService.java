package sprint.sprint1_3.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.exception.member.NoSuchLoginId;
import sprint.sprint1_3.repository.MemberRedisRepository;
import sprint.sprint1_3.repository.MemberRepositoryImpl;
import sprint.sprint1_3.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRedisRepository memberRedisRepository;
    private final RedisTemplate redisTemplate;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByLoginId(member.getLoginId());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        Optional<Member> memberInRedis = memberRedisRepository.findById(String.valueOf(memberId));
        return memberRepository.findById(memberId).orElse(null);
    }

    @Transactional
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public void update(Member member) {
        Member memberInDB = memberRepository.findById(member.getId()).get();
        memberRedisRepository.save(member);

        SetOperations setOperations = redisTemplate.opsForSet();
        setOperations.remove(memberInDB.getLoginId(), memberInDB.getLoginPassword());
        setOperations.add(member.getLoginId(), member.getLoginPassword());
        memberInDB.updateInfo(member.getId()
            , member.getName(), member.getLoginId(), member.getLoginPassword());
    }

    public Member login(String loginId, String loginPassword) {
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        List<Member> members = memberRepository.findByLoginId(loginId);
        Member member;
        if (members.size() == 1) {
            member = members.get(0);
            member.validatePassword(loginPassword);
        }
        if (members.size() == 0) {
            throw new NoSuchLoginId("아이디가 없습니다.");
        }
        return members.get(0);
    }
}
