package hu.fitforfun.model;

import hu.fitforfun.enums.SportFacilityType;
import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.rating.Rateable;
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
public class SportFacility extends BaseEntity implements Rateable<SportFacility, FacilityRating> {

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private SportFacilityType sportFacilityType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<OpeningHours> openingHours;

    @ManyToMany()
    @JoinTable(name = "facility_available_sports",
            joinColumns = @JoinColumn(name = "facilities_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private List<SportType> availableSports;

    @OneToMany(mappedBy = "sportFacility")
    private List<FacilityRating> ratings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<Comment> comments;


    @Override
    public SportFacility addRating(FacilityRating facilityRating) {
        facilityRating.setSportFacility(this);
        this.ratings.add(facilityRating);
        return this;
    }

    public SportFacility addComment(Comment comment) {
        comment.setSportFacility(this);
        this.comments.add(comment);
        return this;
    }

    public SportFacility addSport(SportType sportType) {
        sportType.getSportFacilities().add(this);
        this.availableSports.add(sportType);
        return this;
    }

    @Override
    public List<FacilityRating> getRatings() {
        return ratings;
    }
    /*

        @ManyToMany()
        @JoinTable(name = "facility_instructors",
                joinColumns = @JoinColumn(name = "facilities_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "instructors_id",referencedColumnName = "id"))
        private List<Instructor> instructors;
         */


}
