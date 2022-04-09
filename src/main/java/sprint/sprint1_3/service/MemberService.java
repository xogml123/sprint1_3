package sprint.sprint1_3.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.repository.MemberRepositoryImpl;
import sprint.sprint1_3.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

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
        return memberRepository.findById(memberId).orElse(null);
    }

    @Transactional
    public Long delete(Long memberId) {
        memberRepository.deleteOne(memberId);
        return memberId;
    }

    @Transactional
    public Long update(Long id, String name, Integer age,
        Gender gender) {
        Member memberOrigin = memberRepository.findOne(id);
        return memberOrigin.updateInfo(id, name, age, gender);
    }

    public Member login(String name) {
        return memberRepository.findByName(name).orElseThrow(NoNameFound::new);
    }

    @Transactional
    public Long payment(Long id, Long payment) {
        Member member = memberRepository.findOne(id);

        member.addPayment(payment);
        return member.getId();
    }

    public List<Member> findMatchMembers(Long id) throws Exception{
        Member member = memberRepository.findOne(id);
        if (member.getMemberShip().equals(MemberShip.BRONZE)) {
            throw new TooLowMemberShip("멤버쉽 등급이 너무 낮아서 매칭기능을 사용할 수 없습니다.");
        }

        List<Member> matchAll = memberRepository.findMatchAll(member);
        for (Member m : matchAll) {
            m.setName(m.getSecretName());
        }
        return matchAll;
    }
}
