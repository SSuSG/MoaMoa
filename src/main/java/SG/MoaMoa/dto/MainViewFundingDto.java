package SG.MoaMoa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MainViewFundingDto {

    private Long id;
    private String restaurantName;
    private Integer discountPrice;
    private Integer price;
    private String mainImageFileName;

}
