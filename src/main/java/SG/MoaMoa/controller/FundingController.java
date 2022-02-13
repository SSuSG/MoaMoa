package SG.MoaMoa.controller;

import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.FundingDto;
import SG.MoaMoa.dto.MainViewFundingDto;
import SG.MoaMoa.service.FundingService;
import SG.MoaMoa.service.ImageService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;
    private final ImageService imageService;

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
        Page<MainViewFundingDto> fundingList = fundingService.getFundings(pageable);

        model.addAttribute("fundingList" , fundingList);
        return "/funding/fundingList";
    }

    //펀딩 메인 이미지
    @ResponseBody
    @GetMapping("/images/{imageName}")
    public Resource downloadImage(@PathVariable String imageName) throws
            MalformedURLException {
        return new UrlResource("file:" + imageService.getFullPath(imageName));
    }

    @GetMapping("/funding/{id}")
    public String viewFunding(
            @Login User loginUser,
            @PathVariable Long id, Model model
    ){
        FundingDto funding = fundingService.getFunding(id);
        model.addAttribute("funding" , funding);
        return "/funding/viewFunding";
    }


}
