package hu.fitforfun.configuration;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.ContactData;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.instructor.TrainingSessionDetails;
import hu.fitforfun.model.user.Authority;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.user.Role;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.InstructorService;
import hu.fitforfun.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
public class InitialUserSetup {

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
    @Autowired
    CityRepository cityRepository;

    @Autowired
    TrainingSessionDetailsRepository trainingSessionDetailsRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Authority readAuthority = createAuthority("READ_AUTHORITY");
        Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
        Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

        Role roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
        Role roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) return;
        ContactData contactData = new ContactData();
        contactData.setEmail("asd@asd.com");
        contactData.setTelNumber("702175709");

        Address address = new Address();
        address.setCountry("Magyarország");
        address.setZipCode(2730);
        address.setCity(cityRepository.findByCityNameIgnoreCase("albertirsa"));
        address.setStreet("Tó utca 26");

        User adminUser = new User();
        adminUser.setContactData(contactData);
        adminUser.setShippingAddress(address);
        adminUser.setFirstName("admin");
        adminUser.setLastName("admin");
        adminUser.getContactData().setEmail("asd@asd.com");
        adminUser.setEmailVerificationStatus(true);
        adminUser.setPassword(bCryptPasswordEncoder.encode("pass"));
        adminUser.setRoles(Arrays.asList(roleAdmin));
        userRepository.save(adminUser);

        Instructor instructor1 = createInstructor(adminUser);


    }

    @Transactional
    private Authority createAuthority(String name) {
        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private Role createRole(String name, List<Authority> authorities) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
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

        TrainingSessionDetails trainingSessionDetails = new TrainingSessionDetails();
        trainingSessionDetails.setName("Csoportos kragmaga edzés");
        trainingSessionDetails.setDurationMinutes(90);
        trainingSessionDetails.setMonthlyPrice(10000);
        trainingSessionDetails.setOccasionPrice(1500);
        TrainingSessionDetails trainingSessionDetails2 = new TrainingSessionDetails();
        trainingSessionDetails2.setName("Személyes kragmaga edzés");
        trainingSessionDetails2.setDurationMinutes(60);
        trainingSessionDetails2.setMonthlyPrice(10000);
        trainingSessionDetails2.setOccasionPrice(3000);

        User user = new User();
        user.getContactData().setEmail("misi@gmail.com");
        user.setFirstName("instructor");
        user.setLastName("instructor");
        user.setPassword("pass");
        user.setContactData(contactData);
        user.setShippingAddress(address);
        Instructor instructor = new Instructor();
        instructor.setTitle("Személyi edző");
        instructor.setBio("Segítek célod elérésében bármi áron, nincs lehetetlen csak tehetetlen");
        instructor.setUser(user);
        instructor.setTrainingSessionDetails(Arrays.asList(trainingSessionDetails,trainingSessionDetails2));
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
}
