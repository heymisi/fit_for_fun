package hu.fitforfun.model.address;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="cities_table")
@JsonIdentityInfo(scope= City.class,generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class City extends BaseEntity {
    @Column(name = "city_name")
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "county_id")
    private County county;
}
