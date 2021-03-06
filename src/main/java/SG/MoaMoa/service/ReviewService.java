package SG.MoaMoa.service;

import SG.MoaMoa.domain.Funding;
import SG.MoaMoa.domain.Review;
import SG.MoaMoa.domain.User;
import SG.MoaMoa.dto.CreateReviewDto;
import SG.MoaMoa.dto.UpdateReviewDto;
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

    //리뷰 삭제
    @Transactional
    public boolean deleteReview(Long reviewId , Long userId) {
        Review review = reviewRepository.findById(reviewId).get();
        //작성자와 로그인유저의 id가 다르면 삭제불가
        if(review.getUser().getId() != userId){
            return false;
        }
        reviewRepository.deleteById(reviewId);
        return true;
    }

    //리뷰 수정
    @Transactional
    public boolean updateReview(UpdateReviewDto updateReviewDto, Long userId) {
        //작성자와 로그인유저의 id가 다르면 수정불가
        if(updateReviewDto.getWriterId() != userId){
            return false;
        }
        Review review = reviewRepository.findById(updateReviewDto.getId()).get();
        review.updateReview(updateReviewDto.getContent());
        return true;

    }
}
