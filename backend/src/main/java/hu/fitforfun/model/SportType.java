package hu.fitforfun.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sports_table")
public class SportType extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "sportType")
    private List<Exercise> exercises;
    @JsonIgnore
    @ManyToMany(mappedBy = "availableSports")
    private List<SportFacility> sportFacilities;
    @JsonIgnore
    @ManyToMany(mappedBy = "knownSports")
    private List<Instructor> instructors;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<ShopItem> items;

    public SportType addExercises(Exercise exercise) {
        exercise.setSportType(this);
        this.exercises.add(exercise);
        return this;
    }
}
