package SG.MoaMoa.controller;

import SG.MoaMoa.domain.RoleType;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.*;
import SG.MoaMoa.service.FundingService;
import SG.MoaMoa.service.ImageService;
import SG.MoaMoa.service.ReviewService;
import SG.MoaMoa.web.argumentresolver.Login;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;
    private final ImageService imageService;
    private final ReviewService reviewService;

    //admin페이지
    @GetMapping("/admin")
    public String admin() {
        return "/admin/adminPage";
    }

    //admin인지 아닌지 체크
    @GetMapping("/admin/check")
    @ResponseBody
    public String adminCheck(@Login User loginUser){
        log.info("adminCheck!!");
        if (loginUser.getRoleType() == RoleType.ADMIN) {
            return "success";
        }else{
            return "fail";
        }
    }

    //펀딩등록하기
    @GetMapping("/admin/funding")
    public String viewRegFundingPage(@ModelAttribute FundingDto fundingDto) {
        log.info("FundingController : viewRegFundingPage");
        return "/admin/registerFunding";
    }

    //펀딩등록하기
    @PostMapping("/admin/funding")
    public String registerFunding(@Valid @ModelAttribute FundingDto fundingDto, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return "admin/registerFunding";

        log.info("FundingController : registerFunding");
        fundingService.createFunding(fundingDto);
        return "redirect:/admin";
    }



    //펀딩리스트들
    @GetMapping("/fundings")
    public String viewFundingList(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<MainViewFundingDto> fundingList = fundingService.getFundings(pageable);
        model.addAttribute("fundingList", fundingList);

        return "/funding/fundingList";
    }

    //펀딩 썸네일
    @ResponseBody
    @GetMapping("/images/{imageName}")
    public Resource downloadImage(@PathVariable String imageName) throws MalformedURLException {
        log.info("downloadImage : {}", imageName);
        return new UrlResource("file:" + imageService.getFullPath(imageName));
    }

    //상세 펀딩 페이지
    @GetMapping("/funding/{id}")
    public String viewFunding(@Login User loginUser , @PathVariable Long id,Model model) {
        Pageable pageable = PageRequest.of(0, 3);
        Slice<ReviewDto> reviewList = fundingService.getReviewList(pageable, id);

        model.addAttribute("reviewList" , reviewList);
        model.addAttribute("loginUser" , loginUser);
        model.addAttribute("funding", fundingService.getFunding(id));
        return "/funding/viewFunding";
    }


    //리뷰 불러오기 , 무한스크롤
    @GetMapping("/funding/review/{id}")
    @ResponseBody
    public Slice<ReviewDto> reviewInFunding(@PathVariable Long id, @RequestParam int page){
        Pageable pageable = PageRequest.of(page, 3);
        log.info("page : {}" ,page);

        return fundingService.getReviewList(pageable,id);
    }


    //리뷰등록
    @PostMapping("/review")
    @ResponseBody
    public String registerReview(@Login User loginUser , @RequestBody CreateReviewDto createReviewDto){
        if(reviewService.createReview(createReviewDto , loginUser.getId()))
            return "success";
        else
            return "fail";
    }

    //리뷰 수정
    @PostMapping("/review/update")
    @ResponseBody
    public String updateReview(
            @Login User loginUser,
            @RequestBody UpdateReviewDto updateReviewDto
    ){
        log.info("updateReview");
        if(reviewService.updateReview(updateReviewDto , loginUser.getId()))
            return "success";
        return "fail";
    }

    //리뷰 삭제
    @PostMapping("/review/delete/{id}")
    @ResponseBody
    public String deleteReview(
            @Login User loginUser,
            @PathVariable Long id){
        log.info("deleteReview");
        if(reviewService.deleteReview(id , loginUser.getId()))
            return "success";
        return "fail";
    }

    //펀딩참가
    @ResponseBody
    @PostMapping("/funding/application")
    public String applyFunding(
            @Login User loginUser,
            @RequestParam Long fundingId
    ) {
        log.info("applyFunding || fundingId : {}", fundingId);
        String response = fundingService.applyFunding(loginUser.getId(), fundingId);
        return response;
    }

    //검색기능
    @GetMapping("/funding/search")
    public String searchFunding(@RequestParam String searchName , Model model){
        log.info("searchFunding : {} ", searchName);
        model.addAttribute("searchFundingList",fundingService.searchFundingName(searchName));
        return "/funding/searchFundingList";
    }

    //검색기능
    @GetMapping("/funding/exist")
    @ResponseBody
    public String isExistFunding(@RequestParam String searchName){
        log.info("isExistFunding : {} ", searchName);
        if(fundingService.isExistFundingName(searchName))
            return "success";
        else
            return "fail";

    }


}



