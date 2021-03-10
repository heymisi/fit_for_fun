package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.SportFacility;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.rating.InstructorRating;
import hu.fitforfun.model.user.User;

import java.util.List;

public interface InstructorService {
    Instructor getInstructorById(Long id) throws FitforfunException;

    List<Instructor> listInstructors(int page, int limit);

    Instructor createInstructor(User user) throws FitforfunException;

    void deleteInstructor(Long id) throws FitforfunException;

    Instructor updateInstructor(Long id,Instructor instructor) throws FitforfunException;

    InstructorRating rateInstructor(User user, Long instructorId, Double value) throws FitforfunException;

    Instructor commentInstructor(User user,Long instructorId, Comment comment) throws FitforfunException;
}
