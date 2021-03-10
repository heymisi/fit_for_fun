package hu.fitforfun.services.impl;

import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.rating.InstructorRating;
import hu.fitforfun.model.request.RatingRequestModel;
import hu.fitforfun.repositories.FacilityRatingRepository;
import hu.fitforfun.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;

public class RatingInstructorServiceImpl implements RatingService<Instructor> {

    @Override
    public RatingRequestModel getRate(Instructor instructor) {
        RatingRequestModel returnRating = new RatingRequestModel();
        int counter = instructor.getRatings().size();
        returnRating.setCounter(counter);

        returnRating.setValue(
                (instructor.getRatings().stream()
                        .filter(value -> value != null && value.getValue() != null)
                        .mapToDouble(InstructorRating::getValue)
                        .sum()) / counter
        );
        return returnRating;
    }
}
