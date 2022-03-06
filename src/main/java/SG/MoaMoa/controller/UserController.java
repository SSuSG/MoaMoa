package SG.MoaMoa.controller;

import SG.MoaMoa.SessionConst;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.*;
import SG.MoaMoa.service.CouponService;
import SG.MoaMoa.service.UserService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CouponService couponService;

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

        User loginUser = userService.login(loginDto.getLoginId(), loginDto.getPassword());

        if(loginUser == null){
            bindingResult.reject("loginFail" , "아이디 또는 비밀번호가 맞지 않습니다.");
            return "user/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER , loginUser);
        if(redirectUrl==null)
            return "redirect:/";
        else
            return "redirect:"+redirectUrl;
    }

    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/";
    }

    //회원가입
    @GetMapping("/join")
    public String join(@ModelAttribute UserDto userDto){
        return "user/join";
    }

    /*
    //회원가입
    @PostMapping("/join")
    public String joinSuccess(@Valid @ModelAttribute UserDto userDto , BindingResult bindingResult) throws Exception {
        log.info("joinSuccess");
        log.info("{}",userDto.getLoginId());
        if(bindingResult.hasErrors())
            return "user/join";


        log.info("joinSuccess2");
        return "redirect:/login";
    }
     */

    //회원가입 , 비밀번호 일치하는지 체크 , 회원중복이 체크하는지 확인
    @PostMapping("/join")
    @ResponseBody
    public String PasswordAndDuplicateUserCheckInJoin(@RequestBody UserDto userDto) throws Exception {

        //비밀번호가 일치하지 않을경우
        if(!userDto.getPassword().equals(userDto.getCheckPassword()))
            return "notMatch";

        //회원 중복이 존재할경우
        if(userService.validateDuplicateUser(userDto.getName(),userDto.getEmail())){
            return "duplicate";
        }

        userService.join(userDto);
        log.info("hello");
        return "pass";
    }



    //아이디 중복 체크
    @GetMapping("/join/id")
    @ResponseBody
    public String joinIdCheck(@RequestParam String loginId){

        if(userService.checkLoginIdDuplicate(loginId)){
            //아이디가 중복되었음
            return "duplicate";
        }
        return "noDuplicate";
    }

    //이메일 인증페이지
    @GetMapping("/email")
    public String emailAuthenticatePage(
            @RequestParam(required = false) String redirectURL,
            Model model
    ){
        model.addAttribute("redirectURL" ,redirectURL );
        return "/user/email";
    }

    //이메일 인증
    @PostMapping("/email")
    @ResponseBody
    public String emailAuthenticate(
            @Login User loginUser,
            @RequestBody AuthenticationKeyDto authenticationKey, HttpServletRequest request
            ){
        if(userService.IsEqualAuthenticationKey(loginUser.getId() , authenticationKey.getAuthenticationKey())){
            //로그인 유저 최신화
            HttpSession session = request.getSession();
            session.removeAttribute(SessionConst.LOGIN_USER);
            session.setAttribute(SessionConst.LOGIN_USER , userService.getLoginUser(loginUser.getId()));

            //인증성공!
            return "success";

        }
        //인증실패
        return "fail";
    }


    //아이디 찾기
    @GetMapping("/find/loginId")
    public String viewFindLoginId(@ModelAttribute FindLoginIdDto findLoginIdDto){
        return "/user/findId";
    }

    //아이디 찾기
    @PostMapping("/find/loginId")
    public String findLoginId(@ModelAttribute FindLoginIdDto findLoginIdDto , BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/user/findId";

        String findLoginId = userService.findLoginId(findLoginIdDto);
        //입력한 정보와 일치하는 아이디가 없을경우
        if(findLoginId.equals("false")){
            bindingResult.reject("findLoginIdFail" , "입력한 정보와 일치하는 아이디가 없습니다.");
            return "/user/findId";
        }else{
            bindingResult.reject("findLoginIdSuccess" , "loginId : " + findLoginId);
            return "/user/findId";
        }
    }


    //비밀번호 찾기
    @GetMapping("/find/password")
    public String viewFindPassword(@ModelAttribute FindPasswordDto findPasswordDto){
        return "/user/findPw";
    }

    //비밀번호 찾기
    @PostMapping("/find/password")
    public String findPassword(@ModelAttribute FindPasswordDto findPasswordDto , BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "/userPw";

        String findPassword = userService.findPassword(findPasswordDto);
        if (findPassword.equals("false")){
            bindingResult.reject("findPasswordFail" , "입력한 정보와 일치하는 비밀번호가 없습니다.");
            return "/user/findPw";
        }else{
            bindingResult.reject("findPasswordSuccess" , "Password : " + findPassword);
            return "/user/findPw";
        }
    }

    //마이페이지
    @GetMapping("/user")
    public String viewMyPage(@Login User loginUser, Model model){
        model.addAttribute("loginUser" , loginUser);
        return "/user/myPage";
    }

    //내정보
    @GetMapping("/user/myInfo")
    public String viewMyInfo(@Login User loginUser, Model model){
        model.addAttribute("loginUser" , userService.getMyInfo(loginUser.getId()));
        return "/user/info";
    }

    //쿠폰함
    @GetMapping("/user/coupon")
    public String viewMyCoupon(@Login User loginUser, Model model){
        model.addAttribute("couponList" , couponService.getCouponList(loginUser.getId()));
        return "/user/coupon";
    }

    //쿠폰사용하기
    @PostMapping("/user/coupon")
    public String useMyCoupon(@RequestParam Long couponId){
        couponService.useCoupon(couponId);
        return "redirect:/user/coupon";
    }

    //펀딩참여내역
    @GetMapping("/user/funding")
    public String viewMyFunding(@Login User loginUser, Model model){
        model.addAttribute("myFundingList" , userService.getMyFundingList(loginUser.getId()));
        return "/user/myFunding";
    }

    //결제
    @GetMapping("/user/payment")
    public String myPayment(@Login User loginUser, Model model){
        model.addAttribute("loginUser" , loginUser);
        return "/user/payment";
    }

    //결제성공
    @ResponseBody
    @PostMapping("/user/payment")
    public String myPaymentSuccess(
            @Login User loginUser,
            @RequestParam int amount
    ){
        userService.chargeMoney(loginUser.getId() , amount);
        return "1111";
    }

    //구독하기
    @GetMapping("/user/subscription")
    public String mySubscriptionPage(){
        return "/user/subscription";
    }

    //구독하기
    @PostMapping("/user/subscription")
    @ResponseBody
    public String mySubscription(@Login User loginUser , HttpServletRequest request){

        //참이면 구독성공
        if(userService.subscription(loginUser.getId())){
            //로그인 유저 최신화
            HttpSession session = request.getSession();
            session.removeAttribute(SessionConst.LOGIN_USER);
            session.setAttribute(SessionConst.LOGIN_USER , userService.getLoginUser(loginUser.getId()));
            return "success";
        } else {
            return "fail";
        }
    }


}
