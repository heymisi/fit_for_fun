package hu.fitforfun.model;

import hu.fitforfun.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercise_table")
public class Exercise extends BaseEntity {

    private String name;

    private List<SportType> sports;

    @Enumerated
    private Difficulty difficulty;

    private List<Comment> comments;
}
