package SG.MoaMoa.dto;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.FundingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private Integer discountPrice;
    private Integer price;
    private Integer minFundingCount;
    private Integer maxFundingCount;
    private Integer nowFundingCount;
    private List<MultipartFile> imageFiles;

    @Enumerated(EnumType.STRING)
    private FundingStatus fundingStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
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
                .imageList(new ArrayList<>())
                .userFundings(new ArrayList<>())
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
