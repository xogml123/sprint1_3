package sprint.sprint1_3.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.dto.member.MemberDto;
import sprint.sprint1_3.service.MemberService;

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
//
//    @GetMapping(value = "/members/updateform")
//    public String updateForm(@RequestParam("id") Long memberId,  Model model) {
//        Member member = memberService.findOne(memberId);
//        model.addAttribute("id", memberId);
//        model.addAttribute("member", Member.toDto(member));
//        return "members/memberUpdateForm";
//    }
//
//    @GetMapping(value = "/members/{id}")
//    public String memberInfo(@PathVariable("id") Long id, Model model) {
//        Member member = memberService.findOne(id);
//        model.addAttribute("member", Member.toDto(member));
//        return "members/memberInfo";
//    }
//
//    @GetMapping(value = "/members/login")
//    public String loginForm(Model model) {
//        model.addAttribute("member", new MemberLoginForm());
//        return "members/memberLoginForm";
//    }
//
//    @GetMapping(value = "/members/payment")
//    public String paymentForm(Model model, @RequestParam("id") Long memberId) {
//        Member member = memberService.findOne(memberId);
//        model.addAttribute("member", new MemberPaymentForm(memberId, member.getPayMoney(), member.getMemberShip()));
//        return "members/memberPaymentForm";
//    }
//
//    @GetMapping(value = "/members")
//    public String list(Model model) {
//        List<Member> members = memberService.findMembers();
//        List<MemberDto> collect = members.stream().map((member) -> Member.toDto(member)).collect(Collectors.toList());
//        model.addAttribute("members", collect);
//        return "members/memberList";
//    }
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
//
//    @PostMapping(value = "/members")
//    public String join(@Validated @ModelAttribute("member") MemberJoinForm memberJoinForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            return "members/memberCreateForm";
//        }
//        Member member = MemberJoinForm.toEntity(memberJoinForm);
//        try{
//            Long memberId = memberService.join(member);
//            redirectAttributes.addAttribute("id", memberId);
//            return "redirect:members/{id}";
//        }
//        catch(Exception ex){
//            bindingResult.reject("duplicate", null, ex.getMessage());
//        }
//        return "members/memberCreateForm";
//    }
//
//    @PostMapping(value = "/members/edit/{id}")
//    public String editMember(@Validated @ModelAttribute("member") MemberUpdateForm memberUpdateForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("id") Long id, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("id", id);
//            return "members/memberUpdateForm";
//        }
//        Long memberId = memberService.update(id, memberUpdateForm.getName(),memberUpdateForm.getAge(), memberUpdateForm.getGender());
//        redirectAttributes.addAttribute("id", memberId);
//        return "redirect:/members/{id}";
//    }
//
//    @PostMapping(value = "/members/delete/{id}")
//    public String deleteMember(RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {
//
//        Long memberId = memberService.delete(id);
//        redirectAttributes.addAttribute("id", memberId);
//        return "redirect:/";
//    }
//
//    @PostMapping(value = "/members/login")
//    public String login(@Validated @ModelAttribute("member") MemberLoginForm memberLoginForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            return "members/memberLoginForm";
//        }
//        try{
//            Member member = memberService.login(memberLoginForm.getName());
//            redirectAttributes.addAttribute("id", member.getId());
//            return "redirect:/members/{id}";
//        }
//        catch (Exception ex) {
//            bindingResult.reject("noIdFound", new Object[]{memberLoginForm.getName()}, "No name ,please re enter name.");
//        }
//        return "members/memberLoginForm";
//    }
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
