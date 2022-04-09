package sprint.sprint1_3.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.session.SessionConst;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(@SessionAttribute(name=SessionConst.LOGIN_MEMBER, required = false) Member member, Model model) {
        if (member == null) {
            return "home";
        }
        model.addAttribute("member", Member.toDto(member));
        return "loginHome";
    }
}
