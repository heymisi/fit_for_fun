package hu.fitforfun.model;

import hu.fitforfun.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercise_table")
public class Exercise extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sportFacility")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private SportType sportType;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    public Exercise addComment(Comment comment) {
        comment.setExercise(this);
        this.comments.add(comment);
        return this;
    }

}
