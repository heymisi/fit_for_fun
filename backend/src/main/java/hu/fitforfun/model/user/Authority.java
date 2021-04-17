package hu.fitforfun.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "authorities")
@JsonIdentityInfo(scope = Authority.class,generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Authority extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private List<Role> roles;

    public Authority(String name) {
        this.name = name;
    }
}
