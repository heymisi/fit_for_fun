package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Exercise;
import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.user.User;

import java.util.List;

public interface ExerciseService {
    Exercise createExercise(Exercise exercise) throws FitforfunException;

    List<Exercise> listExercises(int page, int limit);

    Exercise getExerciseById(long id) throws FitforfunException;

    void deleteExercise(Long id) throws FitforfunException;

    Exercise updateExercise(Long id, Exercise exercise) throws FitforfunException;

    Exercise commentExercise(User user, Long exerciseId, Comment comment) throws FitforfunException;

}
