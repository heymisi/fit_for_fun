package hu.fitforfun.model.facility;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.*;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.instructor.Instructor;
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
@JsonIdentityInfo(scope= SportFacility.class,generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class SportFacility extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;


    @Lob
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactData contactData;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<OpeningHours> openingHours;

    @ManyToMany()
    @JoinTable(name = "facility_available_sports",
            joinColumns = @JoinColumn(name = "facilities_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private List<SportType> availableSports ;

    @OneToMany(mappedBy = "sportFacility")
    private List<Instructor> instructors;
    /*
    @OneToMany(mappedBy = "sportFacility")
    private List<FacilityRating> ratings;
*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FacilityPricing> pricing;
/*
    @Override
    public SportFacility addRating(FacilityRating facilityRating) {
        facilityRating.setSportFacility(this);
        this.ratings.add(facilityRating);
        return this;
    }
*/
    public SportFacility addComment(Comment comment) {
        comment.setSportFacility(this);
        this.comments.add(comment);
        return this;
    }
    public SportFacility addInstructor(Instructor instructor) {
        instructor.setSportFacility(this);
        this.instructors.add(instructor);
        return this;
    }
    public SportFacility addSport(SportType sportType) {
        sportType.getSportFacilities().add(this);
        this.availableSports.add(sportType);
        return this;
    }
/*
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
