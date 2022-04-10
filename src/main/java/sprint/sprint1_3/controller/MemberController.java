package sprint.sprint1_3.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.dto.member.MemberDto;
import sprint.sprint1_3.dto.member.MemberLoginForm;
import sprint.sprint1_3.service.MemberService;
import sprint.sprint1_3.session.SessionConst;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/memberCreateForm";
    }

    @GetMapping(value = "/members/edit")
    public String updateForm(@SessionAttribute(name=SessionConst.LOGIN_MEMBER)Member member, Model model) {
        model.addAttribute("member", Member.toDto(member));
        return "members/memberUpdateForm";
    }

    @GetMapping(value = "/members/{id}")
    public String memberInfo(@PathVariable("id") Long id, Model model) {
        Member member = memberService.findOne(id);
        model.addAttribute("member", Member.toDto(member));
        return "members/memberInfo";
    }

    @GetMapping(value = "/members/login")
    public String loginForm(Model model) {
        model.addAttribute("member", new MemberDto());
        return "members/memberLoginForm";
    }

    //
//    @GetMapping(value = "/members/payment")
//    public String paymentForm(Model model, @RequestParam("id") Long memberId) {
//        Member member = memberService.findOne(memberId);
//        model.addAttribute("member", new MemberPaymentForm(memberId, member.getPayMoney(), member.getMemberShip()));
//        return "members/memberPaymentForm";
//    }
//
    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream().map((member) -> Member.toDto(member))
            .collect(Collectors.toList());
        model.addAttribute("members", collect);
        return "members/memberList";
    }
//
//    @GetMapping("/members/match")
//    public String matchList(Model model, @RequestParam Long id, RedirectAttributes redirectAttributes ) {
//        try {
//            List<Member> members = memberService.findMatchMembers(id);
//            List<MemberMatchForm> collect = members.stream().map((member) -> Member.toMatchForm(member)).collect(Collectors.toList());
//            model.addAttribute("members", collect);
//            return "members/memberMatchList";
//        } catch (Exception exception) {
//            model.addAttribute("member", memberService.findOne(id));
//            redirectAttributes.addAttribute("id", id);
//        }
//        return "redirect:/members/{id}";
//    }

    @PostMapping(value = "/members/new")
    public String join(@Validated @ModelAttribute("member") MemberDto memberDto,
        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "members/memberCreateForm";
        }
        Member member = MemberDto.toEntity(memberDto);
        try {
            Long memberId = memberService.join(member);
            return "redirect:/";
        } catch (RuntimeException ex) {
            bindingResult.reject("duplicate", null, ex.getMessage());
            return "members/memberCreateForm";
        }
    }

    @PostMapping(value = "/members/edit/{id}")
    public String editMember(@SessionAttribute(name = SessionConst.LOGIN_MEMBER) Member member,
        @Validated @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult,
        @PathVariable("id") Long id, Model model, HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("id", id);
            return "members/memberUpdateForm";
        }
        //세션과 바꾸려는 member 같은지 비교
        if (!member.getId().equals(id)) {
            return "redirect:/";
        }
        Member memberRequest = MemberDto.toEntity(memberDto);
        memberService.update(memberRequest);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, memberService.findOne(memberRequest.getId()));
        return "redirect:/";
    }

    @PostMapping(value = "/members/login")
    public String login(@Validated @ModelAttribute("member") MemberLoginForm memberLoginForm,
        BindingResult bindingResult, HttpServletRequest httpServletRequest,
        @RequestParam(required = false, defaultValue = "/") String requestURI) {

        if (bindingResult.hasErrors()) {
            return "members/memberLoginForm";
        }
        try {
            Member member = memberService.login(memberLoginForm.getLoginId(),
                memberLoginForm.getLoginPassword());
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, member);
            return "redirect:" + requestURI;
        } catch (RuntimeException ex) {
            bindingResult.reject("loginFail", ex.getMessage());
            return "members/memberLoginForm";
        }
    }

    @PostMapping(value = "/members/logout")
    public String logout(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping(value = "/members/delete/{id}")
    public String delete(
        @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member member,
        @PathVariable Long id, HttpServletRequest httpServletRequest) {
        if (member.getId().equals(id))
            memberService.delete(id);
        HttpSession session = httpServletRequest.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
//
//    @PostMapping(value = "/members/payment/{id}")
//    public String payment(@PathVariable Long id, @Validated @ModelAttribute("member") MemberPaymentForm memberPaymentForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            return "members/memberPaymentForm";
//        }
//        try{
//            Long memberId = memberService.payment(id, memberPaymentForm.getPayment());
//            redirectAttributes.addAttribute("id", memberId);
//            return "redirect:/members/{id}";
//        }
//        catch (Exception ex) {
//            bindingResult.reject("overflow", null, ex.getMessage());
//        }
//        redirectAttributes.addAttribute("id", id);
//        return "redirect:/members/payment";
//    }
//

}
