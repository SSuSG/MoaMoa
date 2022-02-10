package SG.MoaMoa.repository;


import SG.MoaMoa.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    public Optional<Restaurant> findByRestaurantName(String restaurantName);
}
