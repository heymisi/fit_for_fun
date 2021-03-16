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

    @ManyToMany()
    @JoinTable(name = "instructors_known_sports",
            joinColumns = @JoinColumn(name = "instructors_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id", referencedColumnName = "id"))
    private List<SportType> knownSports;

    @OneToMany(mappedBy = "instructor")
    private List<TrainingSession> trainingSessions;

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
    public Instructor addSport(SportType sportType){
        sportType.getInstructors().add(this);
        this.knownSports.add(sportType);
        return this;
    }
    public Instructor addTrainingSession(TrainingSession session){
        session.setInstructor(this);
        this.trainingSessions.add(session);
        return this;
    }

}
