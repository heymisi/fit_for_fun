package hu.fitforfun.repositories;

import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.rating.FacilityRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacilityRatingRepository extends JpaRepository<FacilityRating,Long> {
    Optional<FacilityRating> findBySportFacilityAndUser(SportFacility sportFacility, User user);
}
