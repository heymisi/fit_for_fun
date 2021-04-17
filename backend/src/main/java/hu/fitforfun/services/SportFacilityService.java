package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.facility.SportFacility;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SportFacilityService {
    SportFacility getSportFacilityById(Long id) throws FitforfunException;

    Page<SportFacility> listSportFacilities(int page, int limit);

    SportFacility createSportFacility(SportFacility sportFacility) throws FitforfunException;

    void deleteSportFacility(Long id) throws FitforfunException;

    SportFacility updateSportFacility(Long id,SportFacility sportFacility) throws FitforfunException;

    //FacilityRating rateSportFacility(User user,Long facilityId, Double value) throws FitforfunException;

    SportFacility commentSportFacility(User user,Long facilityId, Comment comment) throws FitforfunException;

    Page<SportFacility> findByNameContaining(String keyword, Pageable pageable);

    Page<SportFacility> findByCity(String city, Pageable pageable);

    Page<SportFacility> listFacilitiesBySportId(Long id, int page, int limit);
}
