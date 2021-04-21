package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.user.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {
    Instructor getInstructorById(Long id) throws FitforfunException;

    Page<Instructor> listInstructors(int page, int limit);

    Instructor createInstructor(Instructor instructor) throws Exception;

    void deleteInstructor(Long id) throws FitforfunException;

    Instructor updateInstructor(Long id, User user) throws FitforfunException;

   // InstructorRating rateInstructor(User user, Long instructorId, Double value) throws FitforfunException;

    Instructor commentInstructor(User user, Long instructorId, Comment comment) throws FitforfunException;

    List<Instructor> listInstructorsByAvailableFacility();
}
