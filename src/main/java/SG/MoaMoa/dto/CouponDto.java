package SG.MoaMoa.dto;



import SG.MoaMoa.domain.CouponStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class CouponDto {

    private String restaurantName;
    private String menu;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int discountPrice;
    private CouponStatus couponStatus;

    /*
    public Coupon toEntity(){
        Coupon build = Coupon.builder()
                .restaurantName(restaurantName)
                .menu(menu)
                .startDate(startDate)
                .endDate(endDate)
                .discountPrice(discountPrice)
                .build();
        return build;
    }
    */


    @Builder
    public CouponDto(String restaurantName , String menu , int discountPrice , LocalDateTime startDate , LocalDateTime endDate , CouponStatus couponStatus){
        this.restaurantName = restaurantName;
        this.discountPrice = discountPrice;
        this.menu = menu;
        this.startDate = startDate;
        this.endDate= endDate;
        this.couponStatus = couponStatus;
    }
}
