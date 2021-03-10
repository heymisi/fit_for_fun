package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.SportFacility;

import java.util.List;

public interface SportFacilityService {
    SportFacility getSportFacilityById(Long id) throws FitforfunException;

    List<SportFacility> listSportFacilities(int page, int limit);

    SportFacility createSportFacility(SportFacility sportFacility) throws FitforfunException;

    void deleteSportFacility(Long id) throws FitforfunException;

    SportFacility updateSportFacility(Long id,SportFacility sportFacility) throws FitforfunException;

    FacilityRating rateSportFacility(User user,Long facilityId, Double value) throws FitforfunException;

    SportFacility commentSportFacility(User user,Long facilityId, Comment comment) throws FitforfunException;
}
