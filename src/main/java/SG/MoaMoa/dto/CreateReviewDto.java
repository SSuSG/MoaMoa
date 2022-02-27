package SG.MoaMoa.dto;

import SG.MoaMoa.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDto {

    private Long fundingId;
    private String content;

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .build();
    }
}
