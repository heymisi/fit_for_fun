package hu.fitforfun.services.impl;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.*;
import hu.fitforfun.services.InstructorService;
import hu.fitforfun.services.TrainingSessionService;
import hu.fitforfun.services.UserService;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserService userService;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Autowired
    TrainingSessionService trainingSessionService;

    @Override
    public Instructor getInstructorById(Long id) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        return optionalInstructor.get();
    }

    @Override
    public Page<Instructor> listInstructors(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Instructor> instructors = instructorRepository.findAll(pageableRequest);
        return instructors;
    }

    @Override
    public Instructor createInstructor(Instructor instructor) throws Exception {
        if (userRepository.findByContactDataEmail(instructor.getUser().getContactData().getEmail()).isPresent()) {
            throw new FitforfunException(ErrorCode.SPORT_FACILITY_ALREADY_EXISTS);
        }
        User savedUser = userService.createUser(instructor.getUser(), Roles.ROLE_INSTRUCTOR.name());
        //  instructor.setRatings(new ArrayList<>());
        instructor.setComments(new ArrayList<>());
        instructor.setTrainingSessions(new ArrayList<>());
        instructor.setKnownSports(new ArrayList<>());
        return instructorRepository.save(instructor);
    }

    @Override
    public void deleteInstructor(Long id) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        instructorRepository.delete(optionalInstructor.get());
    }

    @Override
    public Instructor updateInstructor(Long id, User user) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        userService.updateUser(instructor.getUser().getId(), user);
        return instructor;
    }

    /*
        @Override
        public InstructorRating rateInstructor(User user, Long instructorId, Double value) throws FitforfunException {
            Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
            if (!optionalInstructor.isPresent()) {
                throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
            }
            Instructor instructor = optionalInstructor.get();
           if(isInstructorAlreadyRatedByUser(instructor,user)){
                throw new FitforfunException(ErrorCode.INSTRUCTOR_ALREADY_RATED);
            }

            InstructorRating rating = new InstructorRating();
            rating.setValue(value);
            rating.setUser(user);
            instructor.addRating(rating);

            return instructorRatingRepository.save(rating);
        }
    */
    @Override
    public Instructor commentInstructor(User user, Long instructorId, Comment comment) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        comment.setCreated(java.sql.Date.valueOf(LocalDate.now()));
        comment.setCommenterName(user.getLastName() + " " + user.getFirstName());
        instructor.addComment(comment);

        commentRepository.save(comment);
        return instructor;
    }

    @Override
    public List<Instructor> listInstructorsByAvailableFacility() {
        List<Instructor> returnValue = new ArrayList<>();
        instructorRepository.findAll().forEach(instructor -> {
                    if (instructor.getSportFacility() == null) {
                        returnValue.add(instructor);
                    }
                }
        );
        return returnValue;
    }




/*    public boolean isInstructorAlreadyRatedByUser(Instructor instructor, User user) {
        Optional<InstructorRating> rating = instructorRatingRepository.findByInstructorAndUser(instructor, user);
        if (rating.isPresent()) return true;
        return false;
    }*/
}
