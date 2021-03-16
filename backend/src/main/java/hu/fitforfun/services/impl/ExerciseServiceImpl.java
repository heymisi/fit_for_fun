package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.Comment;
import hu.fitforfun.model.Exercise;
import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.ExerciseRepository;
import hu.fitforfun.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public Exercise createExercise(Exercise exercise) throws FitforfunException {
        Optional<Exercise> optionalExercise = exerciseRepository.findByName(exercise.getName());
        if (!optionalExercise.isPresent()) {
            throw new FitforfunException(ErrorCode.EXERCISE_ALREADY_EXISTS);
        }
        return exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> listExercises(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Exercise> exercises = exerciseRepository.findAll(pageableRequest);
        List<Exercise> returnValue = exercises.getContent();
        return returnValue;
    }

    @Override
    public Exercise getExerciseById(long id) throws FitforfunException{
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if (!optionalExercise.isPresent()) {
            throw new FitforfunException(ErrorCode.EXERCISE_NOT_EXISTS);
        }
        return optionalExercise.get();
    }

    @Override
    public void deleteExercise(Long id) throws FitforfunException{
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if (!optionalExercise.isPresent()) {
            throw new FitforfunException(ErrorCode.EXERCISE_NOT_EXISTS);
        }
        exerciseRepository.delete(optionalExercise.get());
    }

    @Override
    public Exercise updateExercise(Long id, Exercise exercise) throws FitforfunException{
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if (!optionalExercise.isPresent()) {
            throw new FitforfunException(ErrorCode.EXERCISE_NOT_EXISTS);
        }
        Exercise updatedExercise = optionalExercise.get();
        if(exercise.getName() != null){
            updatedExercise.setName(exercise.getName());
        }
        if(exercise.getDescription() != null){
            updatedExercise.setDescription(exercise.getDescription());
        }
        if(exercise.getDifficulty() != null){
            updatedExercise.setDifficulty(exercise.getDifficulty());
        }
        return exerciseRepository.save(updatedExercise);
    }

    @Override
    public Exercise commentExercise(User user, Long exerciseId, Comment comment) throws FitforfunException {
        return null;
    }
}
