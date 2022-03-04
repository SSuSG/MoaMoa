package SG.MoaMoa.repository;

import SG.MoaMoa.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> , ReviewRepositoryCustom {
}
