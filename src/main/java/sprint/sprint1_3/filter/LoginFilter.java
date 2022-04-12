package sprint.sprint1_3.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import sprint.sprint1_3.domain.Member;
import sprint.sprint1_3.session.SessionConst;

@Slf4j
public class LoginFilter implements Filter {


    private static final String[] whiteList = {"/", "/members/new", "/members/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            if (!PatternMatchUtils.simpleMatch(whiteList, requestURI)) {
                HttpSession session = httpServletRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
                    httpServletResponse.sendRedirect("/members/login?requestURI=" + requestURI);
                    return;
                }
            }
            chain. doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }
}
