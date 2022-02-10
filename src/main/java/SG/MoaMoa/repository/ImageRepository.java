package SG.MoaMoa.repository;

import SG.MoaMoa.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
