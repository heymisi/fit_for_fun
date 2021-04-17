package hu.fitforfun.model.instructor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.enums.WeekDays;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructor_time_sheet")
@JsonIdentityInfo(scope = TrainingSession.class,generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class TrainingSession extends BaseEntity {

    @Column(name = "day", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WeekDays day;

    @Column(name = "session_start", nullable = false)
    private Double sessionStart;

    @Column(name = "session_end", nullable = false)
    private Double sessionEnd;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
