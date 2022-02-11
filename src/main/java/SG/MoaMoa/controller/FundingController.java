package SG.MoaMoa.controller;

import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.service.FundingService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FundingController {

    /*
    private final FundingService fundingService;

    //admin페이지
    @GetMapping("/admin")
    public String registerFunding(
            @Login User loginUser, Model model
    ){
        if(loginUser.getRoleType() == RoleType.ADMIN){
            return "/admin/adminPage";
        }
    }


    //펀딩등록하기
    @GetMapping("/admin/funding")
    public String registerFunding(
            @Login User loginUser, Model model
    ){

    }
     */
}
