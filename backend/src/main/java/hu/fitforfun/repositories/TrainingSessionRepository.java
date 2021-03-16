package hu.fitforfun.repositories;

import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.model.Instructor;
import hu.fitforfun.model.TrainingSession;
import hu.fitforfun.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByInstructorAndDay(Instructor instructor, WeekDays day);

    List<TrainingSession> findByClientAndDay(User client, WeekDays day);

    Optional<TrainingSession> findByInstructorAndDayAndSessionStart(Instructor instructor, WeekDays day, Double sessionStart);

    Optional<TrainingSession> findByInstructorAndDayAndSessionEnd(Instructor instructor, WeekDays day,Double sessionEnd);

    Optional<TrainingSession> findByClientAndDayAndSessionStart(User client, WeekDays day, Double sessionStart);

    Optional<TrainingSession> findByClientAndDayAndSessionEnd(User client, WeekDays day,Double sessionEnd);

}
