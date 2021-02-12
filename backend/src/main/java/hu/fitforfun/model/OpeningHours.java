package hu.fitforfun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "opening_hours_table")
public class OpeningHours extends BaseEntity {

    @Column(name = "day", nullable = false)
    private String day;

    @Column(name = "open_time", nullable = false)
    private double openTime;

    @Column(name = "close_time", nullable = false)
    private double closeTime;

}
