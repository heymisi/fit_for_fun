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
@Table(name = "facility_table")
public class SportFacility extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

   /* @Column(name = "available_sports")
    private List<SportType> availableSports;

    @OneToMany(mappedBy = "sportFacility", cascade = CascadeType.ALL)
    private List<Instructor> instructors;

    private List<OpeningHours> openingHours;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rate;

    private List<Comment> comments;*/
}
