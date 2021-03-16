package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.repositories.CommentRepository;
import hu.fitforfun.repositories.FacilityRatingRepository;
import hu.fitforfun.repositories.OpeningHoursRepository;
import hu.fitforfun.repositories.SportFacilityRepository;
import hu.fitforfun.services.RatingService;
import hu.fitforfun.services.SportFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SportFacilityServiceImpl implements SportFacilityService {

    @Autowired
    SportFacilityRepository sportFacilityRepository;

    @Autowired
    OpeningHoursRepository openingHoursRepository;

    @Autowired
    FacilityRatingRepository facilityRatingRepository;

    @Autowired
    RatingService ratingService;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public SportFacility getSportFacilityById(Long id) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(id);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        return optionalSportFacility.get();
    }

    @Override
    public List<SportFacility> listSportFacilities(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<SportFacility> sportFacilities = sportFacilityRepository.findAll(pageableRequest);
        List<SportFacility> returnValue = sportFacilities.getContent();
        return returnValue;
    }

    @Override
    public SportFacility createSportFacility(SportFacility sportFacility) throws FitforfunException {
        if (sportFacilityRepository.findByName(sportFacility.getName()).isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_ALREADY_EXISTS);
        }
        sportFacility.setRatings(new ArrayList<>());
        sportFacility.setComments(new ArrayList<>());
        return sportFacilityRepository.save(sportFacility);
    }

    @Override
    public SportFacility updateSportFacility(Long id, SportFacility sportFacility) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(id);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility updatedSportFacility = optionalSportFacility.get();
        if (sportFacility.getName() != null) {
            updatedSportFacility.setName(sportFacility.getName());
        }
        if (sportFacility.getAddress() != null) {
            updatedSportFacility.setAddress(sportFacility.getAddress());
        }
        if (sportFacility.getOpeningHours() != null) {
            updatedSportFacility.setOpeningHours(sportFacility.getOpeningHours());
        }
        sportFacilityRepository.save(updatedSportFacility);
        return updatedSportFacility;
    }

    @Override
    public FacilityRating rateSportFacility(User user, Long facilityId, Double value) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(facilityId);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalSportFacility.get();
        if (isFacilityAlreadyRatedByUser(sportFacility, user)) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_ALREADY_RATED);
        }

        FacilityRating rating = new FacilityRating();
        rating.setValue(value);
        rating.setUser(user);
        sportFacility.addRating(rating);

        return facilityRatingRepository.save(rating);
    }

    @Override
    public SportFacility commentSportFacility(User user, Long facilityId, Comment comment) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(facilityId);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalSportFacility.get();
        comment.setCreated(java.sql.Date.valueOf(LocalDate.now()));
        comment.setCommenterName(user.getLastName() + " " + user.getFirstName());
        sportFacility.addComment(comment);

        commentRepository.save(comment);
        return sportFacility;
    }

    @Override
    public void deleteSportFacility(Long id) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(id);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        sportFacilityRepository.delete(optionalSportFacility.get());
    }


    public boolean isFacilityAlreadyRatedByUser(SportFacility facility, User user) {
        Optional<FacilityRating> rating = facilityRatingRepository.findBySportFacilityAndUser(facility, user);
        if (rating.isPresent()) return true;
        return false;
    }

}
