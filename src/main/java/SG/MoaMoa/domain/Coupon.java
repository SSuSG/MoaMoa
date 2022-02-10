package SG.MoaMoa.domain;



import SG.MoaMoa.dto.CouponDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    private String restaurantName;
    private String menu;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int discountPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //필요없을듯?
    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    @Builder
    public Coupon(Long id , String restaurantName , String menu , int discountPrice , LocalDateTime startDate , LocalDateTime endDate , CouponStatus couponStatus){
        this.id = id;
        this.restaurantName = restaurantName;
        this.discountPrice = discountPrice;
        this.menu = menu;
        this.startDate = startDate;
        this.endDate= endDate;
        this.couponStatus = couponStatus;
    }

    public CouponDto toDto(){
        CouponDto couponDto = CouponDto.builder()
                .restaurantName(restaurantName)
                .discountPrice(discountPrice)
                .menu(menu)
                .startDate(startDate)
                .endDate(endDate)
                .couponStatus(couponStatus)
                .build();

        return  couponDto;
    }


    //==연관관계 편의 메소드==//



    //==비즈니스 로직==//
    //쿠폰상태를 사용할수없음으로 바꿈
    public void couponStatusUpdate(){
        this.couponStatus = CouponStatus.UNAVAILABLE;
    }





}
