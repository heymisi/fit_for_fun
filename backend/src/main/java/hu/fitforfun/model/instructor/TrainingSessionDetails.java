package hu.fitforfun.model.instructor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_session_details")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TrainingSessionDetails extends BaseEntity {
    private String name;

    private Integer monthlyPrice;

    private Integer occasionPrice;

    private Integer durationMinutes;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
