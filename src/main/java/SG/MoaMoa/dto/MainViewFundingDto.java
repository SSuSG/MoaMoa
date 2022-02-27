package SG.MoaMoa.dto;

import SG.MoaMoa.domain.FundingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Enumerated;

@Getter
@Builder
@AllArgsConstructor
public class MainViewFundingDto {

    private Long id;
    private String restaurantName;
    private String menu;
    private Integer discountPrice;
    private Integer price;
    private String mainImageFileName;
    private FundingStatus fundingStatus;

}
