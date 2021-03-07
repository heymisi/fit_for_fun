package hu.fitforfun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

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

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "facility_table_opening_hours",
            joinColumns = @JoinColumn(name = "sport_facilities_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "opening_hours_id",referencedColumnName = "id"))
    @Column(name = "opening_hours")
    private List<OpeningHours> openingHours;

   /* @Column(name = "available_sports")
    private List<SportType> availableSports;

    @OneToMany(mappedBy = "sportFacility", cascade = CascadeType.ALL)
    private List<Instructor> instructors;

    private List<OpeningHours> openingHours;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rate;

    private List<Comment> comments;*/
}
