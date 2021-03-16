package hu.fitforfun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sports_table")
public class SportType extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "sportType")
    private List<Exercise> exercises;

    @ManyToMany(mappedBy = "availableSports")
    private List<SportFacility> sportFacilities;

    @ManyToMany(mappedBy = "knownSports")
    private List<Instructor> instructors;

    public SportType addExercises(Exercise exercise) {
        exercise.setSportType(this);
        this.exercises.add(exercise);
        return this;
    }
}
