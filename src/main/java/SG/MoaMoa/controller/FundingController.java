package SG.MoaMoa.controller;

import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.FundingDto;
import SG.MoaMoa.service.FundingService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    //admin페이지
    @GetMapping("/admin")
    public String admin(
            @Login User loginUser, Model model
    ){
        if(loginUser.getRoleType() == RoleType.ADMIN){
            return "/admin/adminPage";
        }
        return "redirect:/";
    }

    //펀딩등록하기
    @GetMapping("/admin/funding")
    public String viewRegFundingPage(
            @Login User loginUser,
            @ModelAttribute FundingDto fundingDto
    ){
        log.info("hihi");
        return "/admin/registerFunding";
    }

    //펀딩등록하기
    @PostMapping("/admin/funding")
    public String registerFunding(
            @Login User loginUser,
            @ModelAttribute FundingDto fundingDto
    ) throws IOException {
        log.info("{}" , fundingDto.getStartDate());
        fundingService.createFunding(fundingDto);
        return "redirect:/admin";
    }

    //펀딩리스트들
    @GetMapping("/funding")
    public String viewFundingList(
            @Login User loginUser,
            @RequestParam(defaultValue = "1") int page , Model model
    ){
        Pageable pageable = PageRequest.of(page-1,4);
        Page<FundingDto> fundingList = fundingService.getFundings(pageable);
        System.out.println("fundingList.getTotalPages() = " + fundingList.getTotalPages());
        System.out.println("fundingList.getTotalElements() = " + fundingList.getTotalElements());
        System.out.println("fundingList.getPageSize() = " + fundingList.getPageable().getPageSize());
        System.out.println("fundingList.getPageNumber() = " + fundingList.getPageable().getPageNumber());
        model.addAttribute("fundingList" , fundingList);
        return "/funding/fundingList";
    }

}
