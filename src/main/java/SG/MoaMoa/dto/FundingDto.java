package SG.MoaMoa.dto;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.FundingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FundingDto {

    private Long id;
    private String restaurantName;
    private String menu;
    private String information;
    private String introduction;
    private String notice;
    private int discountPrice;
    private int price;
    private int minFundingCount;
    private int maxFundingCount;
    private int nowFundingCount;

    @Enumerated(EnumType.STRING)
    private FundingStatus fundingStatus;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Funding toEntityFirst(){
        Funding build = Funding.builder()
                .restaurantName(restaurantName)
                .menu(menu)
                .price(price)
                .discountPrice(discountPrice)
                .information(information)
                .introduction(introduction)
                .notice(notice)
                .maxFundingCount(maxFundingCount)
                .minFundingCount(minFundingCount)
                .nowFundingCount(0)
                .fundingStatus(FundingStatus.READY)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return build;
    }

    public Funding toEntity(){
        Funding build = Funding.builder()
                .id(id)
                .restaurantName(restaurantName)
                .menu(menu)
                .price(price)
                .discountPrice(discountPrice)
                .maxFundingCount(maxFundingCount)
                .minFundingCount(minFundingCount)
                .nowFundingCount(nowFundingCount)
                .information(information)
                .introduction(introduction)
                .notice(notice)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return build;
    }


}
