package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Image;
import hu.fitforfun.model.Rating;
import hu.fitforfun.model.address.City;
import hu.fitforfun.model.facility.FacilityPricing;
import hu.fitforfun.model.facility.OpeningHours;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.request.CommentRequestModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.ImageService;
import hu.fitforfun.services.SportFacilityService;
import hu.fitforfun.util.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ImageService imageService;

    @Override
    public SportFacility getSportFacilityById(Long id) throws FitforfunException, IOException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(id);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }

        return setImage(optionalSportFacility.get());
    }

    @Override
    public Iterable<SportFacility> listSportFacilities(int page, int limit, Specification<SportFacility> spec) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        return sportFacilityRepository.findAll(spec, pageableRequest).map(this::setImage);
    }

    @Override
    public SportFacility createSportFacility(SportFacility sportFacility) throws FitforfunException {
        if (sportFacilityRepository.findByName(sportFacility.getName()).isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_ALREADY_EXISTS);
        }
        sportFacility.setRating(new Rating());
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
        if (pricing != null) {
            pricing.forEach(price -> {
                price.setSportFacility(saved);
                facilityPricingRepository.save(price);
            });
        }
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
            updatedSportFacility.getAddress().setStreet(sportFacility.getAddress().getStreet());
            updatedSportFacility.getAddress().setCity(sportFacility.getAddress().getCity());
        }
        if (sportFacility.getOpeningHours() != updatedSportFacility.getOpeningHours()) {
            for (int i = 0; i < sportFacility.getOpeningHours().size(); i++) {
                updatedSportFacility.getOpeningHours().get(i).setOpenTime(sportFacility.getOpeningHours().get(i).getOpenTime());
                updatedSportFacility.getOpeningHours().get(i).setCloseTime(sportFacility.getOpeningHours().get(i).getCloseTime());
            }
        }
        if (sportFacility.getInstructors() != updatedSportFacility.getInstructors()) {
            updatedSportFacility.getInstructors().forEach(instructor -> instructor.setSportFacility(null));
            sportFacility.getInstructors().forEach(instructor -> {
                updatedSportFacility.addInstructor(instructor);
                instructorRepository.save(instructor);
            });
        }
        if (sportFacility.getAvailableSports() != updatedSportFacility.getAvailableSports()) {
            updatedSportFacility.setAvailableSports(sportFacility.getAvailableSports());
        }
        if (sportFacility.getProfileImage() != updatedSportFacility.getProfileImage()) {
            updatedSportFacility.setProfileImage(sportFacility.getProfileImage());
        }
        if (sportFacility.getMapImage() != updatedSportFacility.getMapImage()) {
            updatedSportFacility.setMapImage(sportFacility.getMapImage());
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

    @Override
    public SportFacility commentSportFacility(User user, Long facilityId, Comment comment) throws FitforfunException {
        Optional<SportFacility> optionalSportFacility = sportFacilityRepository.findById(facilityId);
        if (!optionalSportFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalSportFacility.get();
        comment.setCreated(java.sql.Date.valueOf(LocalDate.now()));
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
    public void addImage(Long id, MultipartFile multipartFile, String type) throws Exception {
        Optional<SportFacility> optionalFacility = sportFacilityRepository.findById(id);
        if (!optionalFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalFacility.get();
        if (type.equals("profile"))
            sportFacility.setProfileImage(new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                    ImageUtils.compressBytes(multipartFile.getBytes())));

        if (type.equals("map"))
            sportFacility.setMapImage(new Image(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
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

    @Override
    public Comment addComment(Long id, CommentRequestModel comment) throws FitforfunException {
        Optional<SportFacility> optionalFacility = sportFacilityRepository.findById(id);
        if (!optionalFacility.isPresent()) {
            throw new FitforfunException(ErrorCode.SHOP_ITEM_NOT_EXISTS);
        }
        Optional<User> optionalUser = userRepository.findById((long) comment.getUserId());
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        SportFacility sportFacility = optionalFacility.get();
        List<Comment> optionalComment = commentRepository.findByCommenterAndSportFacility(optionalUser.get(), sportFacility);
        if (optionalComment.size() != 0) {
            throw new FitforfunException(ErrorCode.ALREADY_COMMENTED);
        }

        sportFacility.getRating().setValue((sportFacility.getRating().getValue() * sportFacility.getRating().getCounter()
                + comment.getRate()) / (sportFacility.getRating().getCounter() + 1));

        sportFacility.getRating().setCounter(sportFacility.getRating().getCounter() + 1);
        Comment commentToAdd = new Comment();
        commentToAdd.setText(comment.getMessage());
        commentToAdd.setRate(comment.getRate());
        commentToAdd.setCommenter(optionalUser.get());
        commentRepository.save(commentToAdd);
        sportFacility.addComment(commentToAdd);
        sportFacilityRepository.save(sportFacility);
        return commentToAdd;
    }

    private SportFacility setImage(SportFacility facility) {
        try {
            if (facility.getMapImage() != null) {
                Image mapImage = imageService.getImage(facility.getMapImage().getName());
                facility.setMapImageString("data:" + mapImage.getType() + ";base64," + Base64.getEncoder().encodeToString(mapImage.getPicByte()));
            }
            if (facility.getProfileImage() != null) {
                Image profileImage = imageService.getImage(facility.getProfileImage().getName());
                facility.setProfileImageString("data:" + profileImage.getType() + ";base64," + Base64.getEncoder().encodeToString(profileImage.getPicByte()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facility;
    }
}
