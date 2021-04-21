package hu.fitforfun.configuration;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.*;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.facility.FacilityPricing;
import hu.fitforfun.model.facility.OpeningHours;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.InstructorService;
import hu.fitforfun.services.SportFacilityService;
import hu.fitforfun.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    CityRepository cityRepository;

    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    InstructorRepository instructorRepository;
    @Autowired
    InstructorService instructorService;
    @Autowired
    TrainingSessionService trainingSessionService;
    @Autowired
    AddressRepository addressRepository;
  /*  @Autowired
    FacilityRatingRepository facilityRatingRepository;*/


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) throws Exception {
        SportFacility sportFacility = new SportFacility();

        Address address = new Address();
        address.setZipCode(1143);
        address.setCountry("Budapest");
        address.setCounty("Pest");
        address.setStreet("Stefánia Út 2.");
        address.setCity(cityRepository.findByCityNameIgnoreCase("Budapest"));
        sportFacility.setAddress(address);
        sportFacility.setName("Papp László Budapest Sportaréna");

        User user = createUser();
        User user2 = createUser2();

        OpeningHours openingHours = new OpeningHours();
        openingHours.setDay(WeekDays.Hétfő);
        openingHours.setOpenTime(10D);
        openingHours.setCloseTime(20D);
        openingHours.setIsClosedToday(false);
        OpeningHours openingHours2 = new OpeningHours();
        openingHours2.setDay(WeekDays.Kedd);
        openingHours2.setOpenTime(12D);
        openingHours2.setCloseTime(22D);
        openingHours2.setIsClosedToday(false);
        OpeningHours openingHours3 = new OpeningHours();
        openingHours3.setDay(WeekDays.Szerda);
        openingHours3.setOpenTime(12D);
        openingHours3.setCloseTime(22D);
        openingHours3.setIsClosedToday(false);
        OpeningHours openingHours4 = new OpeningHours();
        openingHours4.setDay(WeekDays.Csütörtök);
        openingHours4.setOpenTime(7D);
        openingHours4.setCloseTime(20D);
        openingHours4.setIsClosedToday(false);
        OpeningHours openingHours5 = new OpeningHours();
        openingHours5.setDay(WeekDays.Péntek);
        openingHours5.setOpenTime(8D);
        openingHours5.setCloseTime(22D);
        openingHours5.setIsClosedToday(false);
        OpeningHours openingHours6 = new OpeningHours();
        openingHours6.setDay(WeekDays.Szombat);
        openingHours6.setOpenTime(9D);
        openingHours6.setCloseTime(22D);
        openingHours6.setIsClosedToday(false);
        OpeningHours openingHours7 = new OpeningHours();
        openingHours7.setDay(WeekDays.Vasárnap);
        openingHours7.setOpenTime(12D);
        openingHours7.setCloseTime(22D);
        openingHours7.setIsClosedToday(false);


        SportType sportType5 = new SportType();
        sportType5.setName("Saját testsúlyos edzés");
        sportTypeRepository.save(sportType5);


        SportType sportType6 = new SportType();
        sportType6.setName("Kézilabda");
        sportTypeRepository.save(sportType6);

        Instructor instructor = createInstructor(user2);
        Instructor instructor2 = createInstructor2(user);

        SportType sportType222 = new SportType();
        sportType222.setName("Szabadidős sport");
        sportTypeRepository.save(sportType222);

        instructor.addSport(sportType222);
        ContactData contactData = new ContactData();
        contactData.setTelNumber("702175709");
        contactData.setEmail("SportCsarnok@gmail.com");

        FacilityPricing facilityPricing = new FacilityPricing();
        facilityPricing.setAgeGroup("Felnőtt");
        facilityPricing.setSessionTicketPrice(15000D);
        facilityPricing.setSingleTicketPrice(1000D);

        FacilityPricing facilityPricing2 = new FacilityPricing();
        facilityPricing2.setAgeGroup("Diák");
        facilityPricing2.setSessionTicketPrice(10000D);
        facilityPricing2.setSingleTicketPrice(700D);

        FacilityPricing facilityPricing3 = new FacilityPricing();
        facilityPricing3.setAgeGroup("Nyugdíjas");
        facilityPricing3.setSessionTicketPrice(0D);
        facilityPricing3.setSingleTicketPrice(0D);

        sportFacility.setOpeningHours(Arrays.asList(openingHours, openingHours2, openingHours3, openingHours4, openingHours5, openingHours6, openingHours7));
        sportFacility.setAvailableSports(Arrays.asList(sportType5));
        sportFacility.setDescription("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Reiciendis aliquid atque,\n" +
                "              nulla? Quos cum ex quis soluta, a laboriosam. Dicta expedita corporis animi vero voluptate voluptatibus\n" +
                "              possimus, veniam magni quis!");
        sportFacility.setContactData(contactData);
        sportFacility.setPricing(Arrays.asList(facilityPricing,facilityPricing2,facilityPricing3));
        sportFacility.setInstructors(Arrays.asList(instructor));
        SportFacility savedFacility = service.createSportFacility(sportFacility);
     //   service.addInstructor(sportFacility.getId(), instructor);
      //         savedFacility.addInstructor(instructor);
//        sportFacilityRepository.save(savedFacility);

        Comment comment = new Comment();
        comment.setText("THIS IS MY FIRST COMMENT");
        Comment comment2 = new Comment();
        comment2.setText("THIS IS MY SECOND COMMENT");
        service.commentSportFacility(user, savedFacility.getId(), comment2);
        service.commentSportFacility(user, savedFacility.getId(), comment);

    }

    private User createUser() {
        User adminUser = new User();
        adminUser.setFirstName("a");
        adminUser.setLastName("a");
        adminUser.getContactData().setEmail("a");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setPassword("pass");
        return userRepository.save(adminUser);
    }

    private User createUser2() {
        User adminUser = new User();
        adminUser.setFirstName("a2");
        adminUser.setLastName("a2");
        adminUser.getContactData().setEmail("a2");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setPassword("pass");
        return userRepository.save(adminUser);
    }

    @Transactional
    private Instructor createInstructor(User client) {

        TrainingSession session = new TrainingSession();
        session.setClient(client);
        session.setDay(WeekDays.Péntek);
        session.setSessionStart(15d);
        session.setSessionEnd(16d);
        TrainingSession session2 = new TrainingSession();
        session2.setClient(client);
        session2.setDay(WeekDays.Péntek);
        session2.setSessionStart(16d);
        session2.setSessionEnd(17d);
        TrainingSession session3 = new TrainingSession();
        session3.setClient(client);
        session3.setDay(WeekDays.Péntek);
        session3.setSessionStart(16d);
        session3.setSessionEnd(17d);

        ContactData contactData = new ContactData();
        contactData.setTelNumber("702175709");
        contactData.setEmail("heymisi99@gmail.com");

        Address address = new Address();
        address.setCountry("Hungary");
        address.setStreet("Pesti út 32");
        address.setZipCode(2730);
        address.setCounty("Pest");
        address.setCity(cityRepository.findByCityNameIgnoreCase("Albertirsa"));
        addressRepository.save(address);

        User user = new User();
        user.getContactData().setEmail("misis@gmail.com");
        user.setFirstName("instructor");
        user.setLastName("instructor");
        user.setPassword("pass");
        user.setContactData(contactData);
        user.setShippingAddress(address);
        Instructor instructor = new Instructor();
        instructor.setTitle("Személyi edző");
        instructor.setBio("Segítek célod elérésében bármi áron, nincs lehetetlen csak tehetetlen");
        instructor.setUser(user);
        try {
            instructor = instructorService.createInstructor(instructor);
            //  instructorService.addTrainingSession(instructor.getId(),session3);
            session.setInstructor(instructor);
            session2.setInstructor(instructor);
            session3.setInstructor(instructor);
            trainingSessionService.addTrainingSessionToClient(client.getId(),session);
            trainingSessionService.deleteTrainingSession(session.getId());
            trainingSessionService.addTrainingSessionToClient(client.getId(),session2);
            trainingSessionService.addTrainingSessionToClient(client.getId(),session3);


        } catch (FitforfunException e) {
            System.err.println(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instructor;
    }
    @Transactional
    private Instructor createInstructor2(User client) {

        ContactData contactData = new ContactData();
        contactData.setTelNumber("asd");
        contactData.setEmail("asd@gmail.com");

        Address address = new Address();
        address.setCountry("Hungary");
        address.setStreet("Pesti út 32");
        address.setZipCode(2730);
        address.setCounty("Pest");
        address.setCity(cityRepository.findByCityNameIgnoreCase("Budapest"));
        addressRepository.save(address);

        User user = new User();
        user.getContactData().setEmail("asd@gmail.com");
        user.setFirstName("i22nstructor");
        user.setLastName("i22nstructor");
        user.setPassword("pass");
        user.setContactData(contactData);
        user.setShippingAddress(address);
        Instructor instructor = new Instructor();
        instructor.setTitle("Személyi edző");
        instructor.setBio("Segítek célod elérésében bármi áron, nincs lehetetlen csak tehetetlen");
        instructor.setUser(user);
        try {
            instructor = instructorService.createInstructor(instructor);
            //  instructorService.addTrainingSession(instructor.getId(),session3);

        } catch (FitforfunException e) {
            System.err.println(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instructor;
    }

}
