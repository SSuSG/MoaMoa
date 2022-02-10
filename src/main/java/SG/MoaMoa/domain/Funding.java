package SG.MoaMoa.domain;


import SG.MoaMoa.dto.FundingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Funding {

    @Id @GeneratedValue
    @Column(name = "funding_id")
    private Long id;

    private int minFundingCount;
    private int maxFundingCount;
    private int nowFundingCount;
    private int price;
    private int discountPrice;
    private String menu;
    private String restaurantName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Lob
    private String introduction;
    @Lob
    private String information;
    @Lob
    private String notice;


    /*
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    */

    @Builder.Default
    @OneToMany(mappedBy = "funding")
    private List<UserFunding> userFundings = new ArrayList<>();

    @OneToMany(mappedBy = "funding" , cascade = CascadeType.ALL)
    private List<Image> imageList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private FundingStatus fundingStatus; // 진행중 or 마감

    @Enumerated(EnumType.STRING)
    private IsFundingSuccess isFundingSuccess; // 기간안에 달성 or 실패

    public Funding() {
    }


    public FundingDto toDto(){
        FundingDto fundingDto = FundingDto.builder()
                .id(id)
                .price(price)
                .restaurantName(restaurantName)
                .menu(menu)
                .introduction(introduction)
                .information(information)
                .notice(notice)
                .fundingStatus(fundingStatus)
                .discountPrice(discountPrice)
                .minFundingCount(minFundingCount)
                .maxFundingCount(maxFundingCount)
                .nowFundingCount(nowFundingCount)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return fundingDto;
    }


    //==연관관계 편의 메소드==//
    public void addUserFunding(UserFunding userFunding){
        this.userFundings.add(userFunding);
        userFunding.setFunding(this);
    }

    /*
    public void setRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
        restaurant.setFunding(this);
    }
    */


   //==비즈니스 로직==//

    //한 사람이 더 신청할때 쿠폰갯수추가
    public void addFundingCount(){
        if(this.nowFundingCount < this.maxFundingCount){
            this.nowFundingCount++;
        }
    }

    //펀딩신청수를 체크해서 max에 도달했는지 체크
    public boolean checkMaxFunding(){
        if(this.nowFundingCount+1 == this.maxFundingCount){
            this.fundingStatus = FundingStatus.CLOSE;
            this.isFundingSuccess = IsFundingSuccess.SUCCESS;

            //max에 도달 ,유저에게 쿠폰쏴주기
            return true;
        }
        //max에 도달X
        return false;
    }

    //시작날짜가 됬는지 체크
    public boolean checkFundingStartDate() {
        if(this.startDate.isAfter(LocalDateTime.now())){
            return true;
        }
        return false;
    }

    //기간이 지났는지 체크
    public boolean checkFundingEndDate(){
        if(this.endDate.isBefore(LocalDateTime.now())){
            return true;
        }
        return false;
    }

    //기간이 지났으면 min을 넘겼는지 체크
    public boolean checkMinFunding(){
        if(this.nowFundingCount >= this.minFundingCount){
            this.fundingStatus = FundingStatus.CLOSE;
            this.isFundingSuccess = IsFundingSuccess.SUCCESS;

            return true;

        }else{ //min못넘겼으면 펀딩실패
            this.fundingStatus = FundingStatus.CLOSE;
            this.isFundingSuccess = IsFundingSuccess.FAIL;

            return false;
        }
    }


    //펀딩정보를 바탕으로 쿠폰만들어준다.
    public Coupon FundingToCoupon(Funding funding){
        Coupon coupon = Coupon.builder()
                .restaurantName(funding.getRestaurantName())
                .menu(funding.getMenu())
                .discountPrice(funding.getDiscountPrice())
                .startDate(funding.getStartDate()) // 수정 -> 펀딩날짜랑 쿠폰기한이랑은 다름
                .endDate(funding.getEndDate())  // 수정 -> 펀딩날짜랑 쿠폰기한이랑은 다름
                .couponStatus(CouponStatus.AVAILABLE)
                .build();

        return coupon;
    }

    //펀딩 시작
    public void startFunding() {
        this.fundingStatus = FundingStatus.PROCEEDING;
    }
}
