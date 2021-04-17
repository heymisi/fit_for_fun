package hu.fitforfun.services.impl;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.InstructorRepository;
import hu.fitforfun.repositories.TrainingSessionRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.TrainingSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingSessionServiceImpl implements TrainingSessionService {
    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TrainingSessionRepository trainingSessionRepository;

    @Override
    public List<TrainingSession> getInstructorTrainingSessionsByDay(Long instructorId, WeekDays day) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();

        return trainingSessionRepository.findByInstructorAndDay(instructor, day);
    }

    @Override
    public List<TrainingSession> getClientTrainingSessionsByDay(Long clientId, WeekDays day) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(clientId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        User user = optionalUser.get();

        return trainingSessionRepository.findByClientAndDay(user, day);
    }

    public User addTrainingSessionToClient(Long clientId, TrainingSession session) throws FitforfunException {
        Optional<User> optionalUser = userRepository.findById(clientId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        if (!isSessionTimeFreeForClient(clientId,session.getInstructor(), session.getDay(), session.getSessionStart(), session.getSessionEnd())) {
            throw new FitforfunException(ErrorCode.SESSION_TIME_ALREADY_OCCUPIED);
        }
        User user = optionalUser.get();
        session.setClient(user);
        trainingSessionRepository.save(session);
        return user;
    }

    public Instructor addTrainingSessionToInstructor(Long id, TrainingSession trainingSession) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(id);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        if (!isSessionTimeFreeForInstructor(instructor.getId(), trainingSession.getDay(), trainingSession.getSessionStart(), trainingSession.getSessionEnd())) {
            throw new FitforfunException(ErrorCode.SESSION_TIME_ALREADY_OCCUPIED);
        }
        instructor.addTrainingSession(trainingSession);
        trainingSessionRepository.save(trainingSession);

        return instructor;
    }

    @Override
    public void deleteTrainingSession(Long trainingSessionId) throws FitforfunException{
        System.err.println("delete");
        Optional<TrainingSession> optionalTrainingSession = trainingSessionRepository.findById(trainingSessionId);
        if (!optionalTrainingSession.isPresent()) {
            throw new FitforfunException(ErrorCode.TRAINING_SESSION_NOT_EXISTS);
        }
        TrainingSession trainingSession = optionalTrainingSession.get();
        trainingSessionRepository.delete(trainingSession);
    }

    private boolean isSessionTimeValid(Double sessionStart, Double sessionEnd) {
        if (sessionEnd > sessionStart) return true;
        return false;
    }

    public boolean isSessionTimeFreeForClient(Long clientId, Instructor instructor, WeekDays day, Double sessionStart, Double sessionEnd) throws FitforfunException {
        if (!isSessionTimeValid(sessionStart, sessionEnd)) {
            return false;
        }
        Optional<User> optionalUser =userRepository.findById(clientId);
        if (!optionalUser.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
        }
        User user = optionalUser.get();
        if (trainingSessionRepository.findByClientAndDayAndSessionStart(user, day, sessionStart).isPresent() ||
                trainingSessionRepository.findByClientAndDayAndSessionEnd(user, day, sessionEnd).isPresent()) {
            return false;
        }
        List<TrainingSession> trainingSessionsThisDay = getClientTrainingSessionsByDay(user.getId(), day);
        for (TrainingSession session : trainingSessionsThisDay) {
            if ((sessionStart < session.getSessionStart() && session.getSessionStart() < sessionEnd) ||
                    (sessionStart < session.getSessionEnd() && session.getSessionEnd() < sessionEnd)) {
                return false;
            }
        }
        return isSessionTimeFreeForInstructor(instructor.getId(),day,sessionStart,sessionEnd);
    }
    public boolean isSessionTimeFreeForInstructor(Long instructorId, WeekDays day, Double sessionStart, Double sessionEnd) throws FitforfunException {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        if (!optionalInstructor.isPresent()) {
            throw new FitforfunException(ErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        if (trainingSessionRepository.findByInstructorAndDayAndSessionStart(instructor, day, sessionStart).isPresent() ||
                trainingSessionRepository.findByInstructorAndDayAndSessionEnd(instructor, day, sessionEnd).isPresent()) {
            return false;
        }
        List<TrainingSession> trainingSessionsThisDay = getInstructorTrainingSessionsByDay(instructor.getId(), day);
        for (TrainingSession session : trainingSessionsThisDay) {
            if ((sessionStart < session.getSessionStart() && session.getSessionStart() < sessionEnd) ||
                    (sessionStart < session.getSessionEnd() && session.getSessionEnd() < sessionEnd)) {
                return false;
            }
        }
        return true;
    }
}
