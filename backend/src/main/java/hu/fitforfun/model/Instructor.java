package hu.fitforfun.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "instructor_table")
public class Instructor extends BaseEntity {


/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;

    @Column(name = "sports")
    private List<SportType> sports;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rate;

    private List<Comment> comments;*/

}
