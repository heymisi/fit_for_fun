package hu.fitforfun.model;

import hu.fitforfun.enums.WeekDays;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "opening_hours_table")
public class OpeningHours extends BaseEntity {

    @Column(name = "day", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private WeekDays day;

    @Column(name = "open_time", nullable = false)
    private Double openTime;

    @Column(name = "close_time", nullable = false)
    private Double closeTime;

    @Column(name = "is_closed_today")
    private Boolean isClosedToday = false;

    @ManyToOne
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;

}
