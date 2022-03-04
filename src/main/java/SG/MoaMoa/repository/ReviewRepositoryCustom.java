package SG.MoaMoa.repository;


import SG.MoaMoa.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepositoryCustom {

    Slice<Review> findReviewInFunding(Pageable pageable , Long fundingId);
}
