package SG.MoaMoa.service;

import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.Review;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.CreateReviewDto;
import SG.MoaMoa.repository.FundingRepository;
import SG.MoaMoa.repository.ReviewRepository;
import SG.MoaMoa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final FundingRepository fundingRepository;

    //리뷰등록
    @Transactional
    public boolean createReview(CreateReviewDto createReviewDto , Long userId){
        Funding funding = fundingRepository.findById(createReviewDto.getFundingId()).get();
        User user = userRepository.findById(userId).get();

        Review review = createReviewDto.toEntity();
        review.setFundingAndUser(funding,user);

        reviewRepository.save(review);
        return true;
    }
}
