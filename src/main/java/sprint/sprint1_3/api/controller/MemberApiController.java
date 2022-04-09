package sprint.sprint1_3.api.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.dto.member.MemberDto;
import sprint.sprint1_3.dto.member.MemberJoinForm;
import sprint.sprint1_3.dto.member.MemberLoginForm;
import sprint.sprint1_3.dto.member.MemberMatchForm;
import sprint.sprint1_3.dto.member.MemberPaymentForm;
import sprint.sprint1_3.dto.member.MemberPaymentResponse;
import sprint.sprint1_3.dto.member.MemberUpdateForm;
import sprint.sprint1_3.service.MemberService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping(value = "/api/members/{id}")
    public MemberDto memberInfo(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        return Member.toDto(member);
    }


    @GetMapping(value = "/api/members")
    public List<MemberDto> list(Model model) {
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map((member) -> Member.toDto(member))
            .collect(Collectors.toList());
        return collect;
    }

    @GetMapping("/api/members/match")
    public Object matchList(Model model, @RequestParam Long id) {

        String message;
        try {
            List<Member> members = memberService.findMatchMembers(id);
            List<MemberMatchForm> collect = members.stream().map((member) -> Member.toMatchForm(member)).collect(Collectors.toList());
            return collect;
        } catch (Exception exception) {
            message = exception.getMessage();
        }
        return message;
    }

    @PostMapping(value = "/api/members")
    public Object join(@Validated @RequestBody MemberJoinForm memberJoinForm,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }
        Member member = MemberJoinForm.toEntity(memberJoinForm);
        try {
            Long memberId = memberService.join(member);
            return memberId;
        } catch (Exception ex) {
            bindingResult.reject("duplicate", null, ex.getMessage());
        }
        return bindingResult.getAllErrors();
    }

    @PatchMapping(value = "/api/members/{id}")
    public Object editMember(@Validated @RequestBody MemberUpdateForm memberUpdateForm,
        BindingResult bindingResult, RedirectAttributes redirectAttributes,
        @PathVariable("id") Long id, Model model) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }
        Long memberId = memberService.update(id, memberUpdateForm.getName(),
            memberUpdateForm.getAge(), memberUpdateForm.getGender());
        return memberId;
    }

    @DeleteMapping(value = "/api/members/{id}")
    public Long deleteMember(RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {

        Long memberId = memberService.delete(id);
        return memberId;
    }

    @PostMapping(value = "/api/members/login")
    public Object login(@Validated @RequestBody MemberLoginForm memberLoginForm,
        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }
        try {
            Member member = memberService.login(memberLoginForm.getName());

            return member.getId();
        } catch (Exception ex) {
            bindingResult.reject("noIdFound", new Object[]{memberLoginForm.getName()},
                "No name ,please re enter name.");
        }
        return bindingResult.getAllErrors();
    }

    @PostMapping(value = "/api/members/payment/{id}")
    public Object payment(@PathVariable Long id,
        @Validated @RequestBody MemberPaymentForm memberPaymentForm,
        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getAllErrors();
        }
        try {
            Long memberId = memberService.payment(id, memberPaymentForm.getPayment());
            Member member = memberService.findOne(memberId);
            return toPaymentResponse(member);
        } catch (Exception ex) {
            bindingResult.reject("overflow", null, ex.getMessage());
        }
        return bindingResult.getAllErrors();
    }

    private MemberPaymentResponse toPaymentResponse(Member member) {
        MemberPaymentResponse memberPaymentResponse = new MemberPaymentResponse();
        memberPaymentResponse.setId(member.getId());
        memberPaymentResponse.setPayMoney(member.getPayMoney());
        memberPaymentResponse.setMemberShip(member.getMemberShip());
        return memberPaymentResponse;
    }

}
