package SG.MoaMoa.dto;


import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.FundingStatus;
import SG.MoaMoa.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FundingDto {

    private Long id;

    @NotEmpty(message = "가게이름을 입력해주세요.")
    private String restaurantName;
    @NotEmpty(message = "메뉴를 입력해주세요.")
    private String menu;
    @NotEmpty(message = "가게정보를 입력해주세요.")
    private String information;
    @NotEmpty(message = "가게소개를 입력해주세요.")
    private String introduction;
    @NotEmpty(message = "공지사항을 입력해주세요.")
    private String notice;

    @NotNull(message = "할인가격을 입력해주세요.")
    private Integer discountPrice;
    @NotNull(message = "정상가격 입력해주세요.")
    private Integer price;
    @NotNull(message = "최소펀딩수를 입력해주세요.")
    @Min(1)
    private Integer minFundingCount;
    @NotNull(message = "최대펀딩수를 입력해주세요.")
    private Integer maxFundingCount;
    private Integer nowFundingCount;

    private MultipartFile mainImage;
    private List<MultipartFile> imageFiles;
    private List<Image> imageList;

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

    public void setMainImage(MultipartFile mainImage) {
        this.mainImage = mainImage;
    }
}
