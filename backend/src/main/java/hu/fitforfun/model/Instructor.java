package hu.fitforfun.model;


import hu.fitforfun.model.rating.FacilityRating;
import hu.fitforfun.model.rating.InstructorRating;
import hu.fitforfun.model.rating.Rateable;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructor_table")
public class Instructor extends BaseEntity implements Rateable<Instructor, InstructorRating> {

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    private List<InstructorRating> ratings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instructor")
    private List<Comment> comments;

    @Override
    public List<InstructorRating> getRatings() {
        return ratings;
    }

    @Override
    public Instructor addRating(InstructorRating instructorRating) {
        instructorRating.setInstructor(this);
        this.ratings.add(instructorRating);
        return this;
    }

    public Instructor addComment(Comment comment) {
        comment.setInstructor(this);
        this.comments.add(comment);
        return this;
    }
/*

    @ManyToMany(mappedBy = "instructors")
    private List<SportFacility> sportFacility;
    /*
    @Column(name = "sports")
    private List<SportType> sports;

    @OneToOne(cascade = CascadeType.ALL)
    private Rating rate;

    private List<Comment> comments;*/

}
