package SG.MoaMoa.web.interceptor;

import SG.MoaMoa.SessionConst;
import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
public class AssociateUserCheckInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);

        if(loginUser.getRoleType() == RoleType.ASSOCIATE){
            log.info("AssociateUserCheckInterceptor : {}" , request.getRequestURI());
            //준회원이면 이메일 인증이 먼저
            log.info("준회원입니다. 이메일 인증을 먼저해주세요.");

            response.sendRedirect("/email?redirectURL="+request.getRequestURI());
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
