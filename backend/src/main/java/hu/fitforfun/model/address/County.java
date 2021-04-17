package hu.fitforfun.model.address;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "counties_table")
@JsonIdentityInfo(scope= County.class,generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class County extends BaseEntity {
    @Column(name = "county_name")
    private String countyName;

    @OneToMany(mappedBy = "county")
    List<City> cities;

    @Override
    public String toString() {
        return "County{" +
                "countyName='" + countyName + '\'' +
                ", cities=" + cities +
                '}';
    }
}
