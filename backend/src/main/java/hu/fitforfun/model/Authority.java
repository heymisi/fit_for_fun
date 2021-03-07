package hu.fitforfun.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Authority extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "authorities")
    private Collection<Role> roles;

    public Authority(String name) {
        this.name = name;
    }
}
