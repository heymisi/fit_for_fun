/*
package hu.fitforfun.model.rating;

import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@MappedSuperclass
public class Rating extends BaseEntity {

    @Column(name = "rating_value")
    private Double value;

    @OneToOne
    @JoinColumn(name = "users_id")
    private User user;

    public Rating() {
        value = 0.0;
    }
}
*/
