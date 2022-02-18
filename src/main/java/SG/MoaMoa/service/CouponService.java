package SG.MoaMoa.service;


import SG.MoaMoa.domain.Coupon;
import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.domain.UserFunding;
import SG.MoaMoa.dto.CouponDto;
import SG.MoaMoa.repository.CouponRepository;
import SG.MoaMoa.repository.FundingRepository;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final FundingRepository fundingRepository;
    private final UserRepository userRepository;

    //쿠폰사용버튼 클릭시 쿠폰의 상태변경기능
    @Transactional
    public void updateCouponStatus(Long id) {
        Coupon findCoupon = couponRepository.findById(id).get();
        findCoupon.couponStatusUpdate();
    }

    //펀딩정보를 바탕으로 각 유저마다 쿠폰을 만들어주는 서비스
    @Transactional
    public void createCoupon(Funding funding){

        List<UserFunding> userFundingList = funding.getUserFundings();

        for (UserFunding userFunding : userFundingList) {
            User user = userFunding.getUser();
            Coupon coupon = funding.FundingToCoupon(funding);
            user.addCoupon(coupon);
            couponRepository.save(coupon);
        }
    }

    //유저가 가진 쿠폰리스트
    public List<CouponDto> getCouponList(Long userId) {
        List<Coupon> myCouponList = userRepository.findById(userId).get().getCoupons();
        return myCouponList.stream().map(c -> c.toDto()).collect(Collectors.toList());
    }

    //쿠폰 사용하기
    @Transactional
    public void useCoupon(Long couponId) {
        couponRepository.findById(couponId).get().couponStatusUpdate();
    }
}
