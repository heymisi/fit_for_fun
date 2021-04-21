package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.address.City;
import hu.fitforfun.model.facility.FacilityPricing;
import hu.fitforfun.model.facility.OpeningHours;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.SportFacilityService;
import hu.fitforfun.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class SportFacilityServiceImpl implements SportFacilityService {

    @Autowired
    SportFacilityRepository sportFacilityRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    OpeningHoursRepository openingHoursRepository;


    @Autowired
    FacilityPricingRepository facilityPricingRepository;

    @Autowired
    ContactDataRepository contactDataRepository;
    @Autowired
    AddressRepository addressRepository;

/*    @Autowired
    FacilityRatingRepository facilityRatingRepository;*/

    @Autowired
    CityRepository cityRepository;


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
    public Page<SportFacility> listSportFacilities(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<SportFacility> sportFacilities = sportFacilityRepository.findAll(pageableRequest);
        return sportFacilities;
    }

    @Override
    public SportFacility createSportFacility(SportFacility sportFacility) throws FitforfunException {
        if (sportFacilityRepository.findByName(sportFacility.getName()).isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_ALREADY_EXISTS);
        }
        sportFacility.setComments(new ArrayList<>());
        List<Instructor> instructors = sportFacility.getInstructors();
        List<OpeningHours> openingHours = sportFacility.getOpeningHours();
        List<FacilityPricing> pricing = sportFacility.getPricing();
        sportFacility.setInstructors(new ArrayList<>());
        SportFacility saved = sportFacilityRepository.save(sportFacility);
        instructors.forEach(instructor -> {
            saved.addInstructor(instructor);
            instructorRepository.save(instructor);
        });
        openingHours.forEach(hours -> {
            hours.setSportFacility(saved);
            openingHoursRepository.save(hours);
        });
        pricing.forEach(price ->{
            price.setSportFacility(saved);
            facilityPricingRepository.save(price);
        });

        return saved;
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
        if (sportFacility.getAddress() != updatedSportFacility.getAddress()) {
            updatedSportFacility.getAddress().setCountry(sportFacility.getAddress().getCountry());
            updatedSportFacility.getAddress().setZipCode(sportFacility.getAddress().getZipCode());
            updatedSportFacility.getAddress().setStreet(sportFacility.getAddress().getStreet());
            updatedSportFacility.getAddress().setCity(sportFacility.getAddress().getCity());
            updatedSportFacility.getAddress().setZipCode(sportFacility.getAddress().getZipCode());
        }
        if (sportFacility.getOpeningHours() != updatedSportFacility.getOpeningHours()) {
            for (int i = 0; i < sportFacility.getOpeningHours().size(); i++) {
                updatedSportFacility.getOpeningHours().get(i).setOpenTime(sportFacility.getOpeningHours().get(i).getOpenTime());
                updatedSportFacility.getOpeningHours().get(i).setCloseTime(sportFacility.getOpeningHours().get(i).getCloseTime());
            }
        }
        if (sportFacility.getInstructors() != updatedSportFacility.getInstructors()) {
            System.err.println(sportFacility.getInstructors());
            updatedSportFacility.getInstructors().forEach(instructor -> {
                instructor.setSportFacility(null);
            });
            sportFacility.getInstructors().forEach(instructor -> {
                updatedSportFacility.addInstructor(instructor);
                instructorRepository.save(instructor);
            });
        }
        if (sportFacility.getAvailableSports() != updatedSportFacility.getAvailableSports()) {
            updatedSportFacility.setAvailableSports(sportFacility.getAvailableSports());
        }
        if (sportFacility.getImage() != updatedSportFacility.getImage()) {
            updatedSportFacility.setImage(sportFacility.getImage());
        }
        if (sportFacility.getPricing() != updatedSportFacility.getPricing()) {
            for (int i = 0; i < sportFacility.getPricing().size(); i++) {
                updatedSportFacility.getPricing().get(i).setSingleTicketPrice(sportFacility.getPricing().get(i).getSingleTicketPrice());
                updatedSportFacility.getPricing().get(i).setSessionTicketPrice(sportFacility.getPricing().get(i).getSessionTicketPrice());
            }
        }
        if (sportFacility.getComments() != updatedSportFacility.getComments()) {
            updatedSportFacility.setComments(sportFacility.getComments());
        }
        if (sportFacility.getDescription() != null) {
            updatedSportFacility.setDescription(sportFacility.getDescription());
        }
        if (sportFacility.getContactData() != updatedSportFacility.getContactData()) {
            updatedSportFacility.getContactData().setTelNumber(sportFacility.getContactData().getTelNumber());
            updatedSportFacility.getContactData().setEmail(sportFacility.getContactData().getEmail());
        }

        return sportFacilityRepository.save(updatedSportFacility);
    }

    /*
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
    */
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
    public Page<SportFacility> findByNameContaining(String keyword, Pageable pageable) {
        return sportFacilityRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public void deleteSportFacility(Long id) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(id);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalSportFacility.get();
        sportFacility.getInstructors().forEach(instructor -> {
            instructor.setSportFacility(null);
        });
        sportFacilityRepository.delete(optionalSportFacility.get());
    }

    @Override
    public Page<SportFacility> findByCity(String city, Pageable pageable) {
        City cityName = cityRepository.findByCityNameIgnoreCase(city);
        if (cityName == null) {
            return new PageImpl<>(new ArrayList<>());
        }
        return sportFacilityRepository.findByAddressCity(cityName, pageable);
    }

    @Override
    public Page<SportFacility> listFacilitiesBySportId(Long id, int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        return sportFacilityRepository.findByAvailableSportsIdIn(Arrays.asList(id), pageableRequest);
    }

    @Override
    public void addImage(Long id, MultipartFile multipartFile) throws Exception {
        Optional<SportFacility> optionalFacility = sportFacilityRepository.findById(id);
        if (!optionalFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalFacility.get();
        sportFacility.setImage(new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                ImageUtils.compressBytes(multipartFile.getBytes())));
        sportFacilityRepository.save(sportFacility);
    }

    @Override
    public void addInstructor(Long id, Instructor instructor) throws Exception {
        Optional<SportFacility> optionalFacility = sportFacilityRepository.findById(id);
        if (!optionalFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalFacility.get();
        sportFacility.addInstructor(instructor);
    }

/*
    public boolean isFacilityAlreadyRatedByUser(SportFacility facility, User user) {
        Optional<FacilityRating> rating = facilityRatingRepository.findBySportFacilityAndUser(facility, user);
        if (rating.isPresent()) return true;
        return false;
    }
*/
}
