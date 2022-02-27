package SG.MoaMoa.domain;

import SG.MoaMoa.dto.ReviewDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Funding funding;

    private String content;

    public ReviewDto toDto(){
        return ReviewDto.builder()
                .id(id)
                .content(content)
                .build();
    }


    //==연관관계 편의 메소드==//

    public void setFundingAndUser(Funding funding , User user) {
        this.funding = funding;
        funding.getReviewList().add(this);
        this.user = user;
        user.getReviewList().add(this);
    }

}
