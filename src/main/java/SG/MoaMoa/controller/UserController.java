package SG.MoaMoa.controller;

import SG.MoaMoa.SessionConst;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.LoginDto;
import SG.MoaMoa.dto.UserDto;
import SG.MoaMoa.service.UserService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //로그인
    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginDto loginDto){
        return "user/login";
    }


    //로그인
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginDto loginDto,
                        BindingResult bindingResult,
                        @RequestParam(value = "redirectURL" , required = false) String redirectUrl,
                        HttpServletRequest request
                        ){
        if(bindingResult.hasErrors())
            return "user/login";
        log.info("{}" , loginDto.getLoginId());
        User loginUser = userService.login(loginDto.getLoginId(), loginDto.getPassword());
        log.info("{}" , loginUser.getName());
        if(loginUser == null){
            bindingResult.reject("loginFail" , "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER , loginUser);
        if(redirectUrl==null)
            return "redirect:/";
        else
            return "redirect:"+redirectUrl;
    }

    //회원가입
    @GetMapping("/join")
    public String join(@ModelAttribute UserDto userDto){
        return "user/join";

    }

    //회원가입
    @PostMapping("/join")
    public String joinSuccess(@ModelAttribute UserDto userDto , BindingResult result) throws Exception {
        if(result.hasErrors()){
            return "user/join";
        }

        if(userService.join(userDto)){
            return "redirect:/login";
        }
        return "redirect:/user/join";
    }

    //마이페이지
    @GetMapping("/user")
    public String viewMyPage(
            @Login User loginUser, Model model
            ){
        model.addAttribute("loginUser" , loginUser);
        return "/user/myPage";
    }

    //내정보
    @GetMapping("/user/myInfo")
    public String viewMyInfo(
            @Login User loginUser, Model model

    ){
        model.addAttribute("loginUser" , loginUser);
        return "/user/info";
    }

    //쿠폰함
    @GetMapping("/user/coupon")
    public String viewMyCoupon(
            @Login User loginUser, Model model
    ){
        model.addAttribute("loginUser" , loginUser);
        return "/user/coupon";
    }

    //펀딩참여내역
    @GetMapping("/user/funding")
    public String viewMyFunding(
            @Login User loginUser, Model model
    ){
        model.addAttribute("loginUser" , loginUser);
        return "/user/funding";
    }

    //결제
    @GetMapping("/user/payment")
    public String myPayment(
            @Login User loginUser, Model model
    ){
        model.addAttribute("loginUser" , loginUser);
        return "/user/payment";
    }



}
