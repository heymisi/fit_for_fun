package hu.fitforfun.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity()
@Table(name = "rating_table")
public class Rating extends BaseEntity {

    @Column(name = "value")
    private double value;

    @Column(name = "rated_by")
    private int ratedBy;

    public Rating() {
        value = 0.0;
        ratedBy = 0;
    }
}
