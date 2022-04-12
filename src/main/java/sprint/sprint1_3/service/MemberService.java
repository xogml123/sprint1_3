package sprint.sprint1_3.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.exception.member.NoSuchLoginId;
import sprint.sprint1_3.exception.member.NotMatchedPassword;
import sprint.sprint1_3.repository.LoginRedisRepository;
import sprint.sprint1_3.repository.MemberRedisRepository;
import sprint.sprint1_3.repository.MemberRepositoryImpl;
import sprint.sprint1_3.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRedisRepository memberRedisRepository;
    private final LoginRedisRepository loginRedisRepository;

    @Transactional
    public void join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
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

    //영속화 되지 않은 객체 return
    public Member findOne(Long memberId) {
        Optional<Member> memberInRedis = memberRedisRepository.findById(String.valueOf(memberId));
        if (memberInRedis.isPresent()) {
            log.info("member findOne hit ----");
            return memberInRedis.get();
        } else {
            log.info("member findOne hit miss ---");

            Optional<Member> memberInDB = memberRepository.findById(memberId);
            if (memberInDB.isPresent()) {
                memberRedisRepository.put(memberInDB.get());
                return memberRedisRepository.findById(String.valueOf(memberId)).orElse(null);
            } else {
                return null;
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        memberRepository.deleteById(id);
        loginRedisRepository.deleteByLoginId(memberRedisRepository.findById(String.valueOf(id)).get().getLoginId());
        memberRedisRepository.delete(String.valueOf(id));
    }

    @Transactional
    public void update(Member member) {
        Optional<Member> memberOptional = memberRepository.findById(member.getId());
        if (memberOptional.isPresent()) {
            Member m = memberOptional.get();
            m.updateInfo(member.getId(), member.getName(), member.getLoginId(),
                member.getLoginPassword());
            memberRedisRepository.put(member);
            loginRedisRepository.put(member.getLoginId(), member.getLoginPassword());
        }
    }

    public String login(String loginId, String loginPassword) {

        Optional<String> passwordByLoginId = loginRedisRepository.findPasswordByLoginId(loginId);
        if (passwordByLoginId.isPresent()) {
            log.info("login hit---------------");

            String p = passwordByLoginId.get();
            if (loginPassword.equals(p)) {
                return loginId;
            }else {
                throw new NotMatchedPassword("비밀번호가 일치하지 않습니다.");
            }
        }
        //Hit miss
        log.info("login hit miss---------------");
        List<Member> members = memberRepository.findByLoginId(loginId);
        if (members.size() == 1) {
            memberRedisRepository.put(members.get(0));
            loginRedisRepository.put(members.get(0).getLoginId(),
                members.get(0).getLoginPassword());
            members.get(0).validatePassword(loginPassword);
        }
        else if (members.size() == 0) {
            throw new NoSuchLoginId("아이디가 없습니다.");
        }
        return members.get(0).getLoginId();
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).get(0);
    }
}
