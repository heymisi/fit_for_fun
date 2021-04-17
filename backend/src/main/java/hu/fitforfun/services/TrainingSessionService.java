package hu.fitforfun.services;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.user.User;

import java.util.List;

public interface TrainingSessionService {

    List<TrainingSession> getInstructorTrainingSessionsByDay(Long instructorId, WeekDays day) throws FitforfunException;

    List<TrainingSession> getClientTrainingSessionsByDay(Long clientId, WeekDays day) throws FitforfunException;

    User addTrainingSessionToClient(Long clientId, TrainingSession session) throws FitforfunException;

    Instructor addTrainingSessionToInstructor(Long id, TrainingSession trainingSession) throws FitforfunException;

    void deleteTrainingSession(Long trainingSessionId) throws FitforfunException;
}
