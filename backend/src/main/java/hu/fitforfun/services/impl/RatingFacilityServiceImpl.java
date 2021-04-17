/*
package hu.fitforfun.services.impl;

import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.request.RatingRequestModel;
import hu.fitforfun.services.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingFacilityServiceImpl implements RatingService<SportFacility> {


    @Override
    public RatingRequestModel getRate(SportFacility sportFacility) {
        RatingRequestModel returnRating = new RatingRequestModel();
        int counter = sportFacility.getRatings().size();
        returnRating.setCounter(counter);

        returnRating.setValue(
                (sportFacility.getRatings().stream()
                .filter(value -> value != null && value.getValue() != null)
                .mapToDouble(FacilityRating::getValue)
                .sum()) / counter
        );
        return returnRating;
    }
}
*/
