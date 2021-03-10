package hu.fitforfun.configuration;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.OpeningHours;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.SportType;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.SportFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

@Component
public class InitialSportFacilitySetup {

    @Autowired
    SportFacilityService service;

    @Autowired
    SportFacilityRepository sportFacilityRepository;

    @Autowired
    OpeningHoursRepository openingHoursRepository;

    @Autowired
    SportTypeRepository sportTypeRepository;

    @Autowired
    FacilityRatingRepository facilityRatingRepository;

    @Autowired
    UserRepository userRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) throws FitforfunException {
        SportFacility sportFacility = new SportFacility();
        sportFacility.setAddress("address");
        sportFacility.setName("name");


        User user = createUser();
        User user2 = createUser2();
        OpeningHours openingHours = new OpeningHours();
        openingHours.setDay(WeekDays.FRIDAY);
        openingHours.setOpenTime(10D);
        openingHours.setCloseTime(20D);
        openingHours.setIsClosedToday(false);
        openingHours.setSportFacility(sportFacility);
        OpeningHours openingHours2 = new OpeningHours();
        openingHours2.setDay(WeekDays.MONDAY);
        openingHours2.setOpenTime(12D);
        openingHours2.setCloseTime(22D);
        openingHours2.setIsClosedToday(false);
        openingHours2.setSportFacility(sportFacility);
        OpeningHours openingHours3 = new OpeningHours();
        openingHours3.setDay(WeekDays.MONDAY);
        openingHours3.setOpenTime(12D);
        openingHours3.setCloseTime(22D);
        openingHours3.setIsClosedToday(false);
        openingHours3.setSportFacility(sportFacility);


        SportType sportType = new SportType();
        sportType.setName("FOOTBALL");
        sportTypeRepository.save(sportType);
        SportType sportType2 = new SportType();
        sportType2.setName("BASKETBALL");
        sportTypeRepository.save(sportType2);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //facilityRating.setUser((User)auth.getDetails());
        System.err.println(facilityRatingRepository.findAll());
        sportFacility.setOpeningHours(Arrays.asList(openingHours, openingHours2, openingHours3));
        //sportFacility.setRating(Arrays.asList(savedRating));
        // sportFacility.setAvailableSports(Arrays.asList(sportType, sportType2));
        SportFacility savedFacility = service.createSportFacility(sportFacility);
        try {
            service.rateSportFacility(user, savedFacility.getId(), 5d);
            service.rateSportFacility(user2, savedFacility.getId(), 4d);
        } catch (FitforfunException e) {
            System.err.println(e.getErrorCode());
        }

        Comment comment = new Comment();
        comment.setText("THIS IS MY FIRST COMMENT");
        Comment comment2 = new Comment();
        comment2.setText("THIS IS MY SECOND COMMENT");
        service.commentSportFacility(user,savedFacility.getId(),comment2);
        service.commentSportFacility(user,savedFacility.getId(),comment);
        //service.rateSportFacility(savedFacility.getId(), 3d);


    }

    private User createUser() {
        User adminUser = new User();
        adminUser.setFirstName("a");
        adminUser.setLastName("a");
        adminUser.setEmail("a");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setPassword("pass");
        return userRepository.save(adminUser);
    }

    private User createUser2() {
        User adminUser = new User();
        adminUser.setFirstName("a2");
        adminUser.setLastName("a2");
        adminUser.setEmail("a2");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setPassword("pass");
        return userRepository.save(adminUser);
    }

}
