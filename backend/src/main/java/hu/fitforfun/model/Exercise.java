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

    @Column(name = "sports")
    @OneToMany( mappedBy = "exercise")
    private List<SportType> sports;

    @Enumerated
    private Difficulty difficulty;

    @Column(name = "comments")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "exercise")
    private List<Comment> comments;
}
