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

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;
    /*
    @ManyToMany(mappedBy = "availableSports")
    private List<SportFacility> sportFacilities;
    */
}
