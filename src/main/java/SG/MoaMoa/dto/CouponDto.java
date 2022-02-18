package SG.MoaMoa.dto;



import SG.MoaMoa.domain.CouponStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDto {

    private Long id;
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

}
