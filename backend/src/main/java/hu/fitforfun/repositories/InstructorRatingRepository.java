package hu.fitforfun.repositories;

import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.rating.InstructorRating;
import hu.fitforfun.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRatingRepository extends JpaRepository<InstructorRating,Long> {
    Optional<InstructorRating> findByInstructorAndUser(Instructor instructor, User user);

}