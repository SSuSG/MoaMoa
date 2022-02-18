package SG.MoaMoa.service;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.Image;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.domain.UserFunding;
import SG.MoaMoa.dto.FundingDto;
import SG.MoaMoa.dto.MainViewFundingDto;
import SG.MoaMoa.dto.PageRequestDto;
import SG.MoaMoa.repository.FundingRepository;
import SG.MoaMoa.repository.UserFundingRepository;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final UserFundingRepository userFundingRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final CouponService couponService;
    private final EntityManager em;


    //새로운 펀딩 만들기
    @Transactional
    public void createFunding(FundingDto fundingDto) throws IOException {
        //메인 이미지 저장
        Image mainImage = imageService.storeImage(fundingDto.getMainImage(),true);

        //이미지 저장
        List<Image> images = imageService.storeImages(fundingDto.getImageFiles());

        Funding funding = fundingDto.toEntityFirst();

        //연관관계 저장
        funding.addImage(mainImage);
        for (Image image : images) {
            funding.addImage(image);
        }
        fundingRepository.save(funding);
    }

    //펀딩정보를 페이지에 띄워주기
    public FundingDto getFunding(Long id){
        Funding findFunding = fundingRepository.findById(id).get();
        FundingDto fundingDto = findFunding.toDto();
        return fundingDto;
    }

    //펀딩리스트들 넘겨주기
    public Slice<FundingDto> getFundingList(PageRequestDto pageRequestDto){
        Pageable pageable = PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize());
        return fundingRepository.findAllCustom(pageable).map(funding -> funding.toDto());
    }

    //펀딩리스트들 넘겨주기
    public Page<MainViewFundingDto> getFundings(Pageable pageable){
        return fundingRepository.findAll(pageable).map(f -> f.toMainViewDto());

    }

    //펀딩 신청 로직!!
    @Transactional
    public String applyFunding(Long userId, Long fundingId){
        Funding funding = fundingRepository.findById(fundingId).get();
        User user = userRepository.findById(userId).get();
        log.info("FundingService || applyFunding : hi");
        //이 펀딩에 이미 참여한적이 있다면 실패
        if(userFundingRepository.findUserFunding(userId,fundingId) != null){
            //null 이 아니라는뜻은 참여한적이 있다라는 뜻
            return "failByDuplication";
        }
        log.info("FundingService || applyFunding : hi2");
        //펀딩에 참여하려면 돈이 있어야함 , 없으면 실패
        if (user.getMoney() >= funding.getDiscountPrice()){
            //유저가 펀딩에 참여할 돈이 있다면 돈차감
            user.spendMoney(funding.getDiscountPrice());
        }else{
            //유저가 펀딩에 참여할 돈이 없다면 실패
            return "failByLackMoney";
        }
        log.info("FundingService || applyFunding : hi3");

        //이번 신청으로 max에 도달했다면
        if(checkMaxFundingCount(fundingId)){
            //nowFundingCount +1 증가
            funding.addFundingCount();
            //user와 funding의 연관관계 맺어줌
            addUserFunding(userId,fundingId);

            //펀딩정보 최신화
            em.flush();
            //쿠폰 발송
            couponService.createCoupon(funding);
            return "success";
        }else{
            funding.addFundingCount();
            addUserFunding(userId,fundingId);
            return "success";
        }

    }


    //한 사람이 펀딩에 참여했을때 Funding에 userFunding 추가 + User에다가도 userFunding추가
    @Transactional
    public void addUserFunding(Long userId , Long fundingId){
        log.info("FundingService : addUserFunding");
        User findUser = userRepository.findById(userId).get();
        Funding findFunding = fundingRepository.findById(fundingId).get();

        UserFunding createUserFunding = UserFunding.builder()
                .user(findUser)
                .funding(findFunding)
                .build();

        userFundingRepository.save(createUserFunding);
    }


    //이번 신청으로 펀딩이max에 도달한지
    @Transactional
    public boolean checkMaxFundingCount(Long fundingId){
        log.info("FundingService : checkMaxFundingCount");
        Funding findFunding = fundingRepository.findById(fundingId).get();
        // 이번 신청으로 max에 도달할수있다면
        if(findFunding.checkMaxFunding()){
            return true;
        }
        //이번 신청으로 max에 도달하지 못했음
        return false;
    }

    //매일 자정에 펀딩이 끝났는지 아닌지 체크
    @Transactional
    @Scheduled(cron = "1 0 0 * * *") // 매일 0시에 체크
    public void checkFundingEndDate(){
        log.info("FundingService : checkFundingEndDate");

        //현재 진행중인 펀딩리스트들 가져옴
        List<Funding> proceedingFunding = fundingRepository.findProceedingFunding();

        for (Funding funding : proceedingFunding) {
            if(funding.checkFundingEndDate()) { //기간이 지났다면
                if (funding.checkMinFunding() == true) { //min을 넘겼다면
                    couponService.createCoupon(funding);
                } else { //min을 못넘겼다면 , 유저에게 환불해줘야함

                }
            }
        }
    }

    //펀딩 시작날짜가 되면 펀딩시작!
    @Transactional
    @Scheduled(cron = "1 0 0 * * *") // 매일 0시에 체크
    public void checkFundingStartDate(){
        log.info("FundingService : checkFundingStartDate");
        List<Funding> readyFunding = fundingRepository.findReadyFunding();

        for (Funding funding : readyFunding) {
            if(funding.checkFundingStartDate()){ //시작날짜가 됬다면
                funding.startFunding(); //펀딩상태를 READY -> PROCEEDING
            }
        }
    }

}
