package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item_category")
// @Data --known bug ??
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCategory extends BaseEntity {

    @Column(name = "category_name")
    private String categoryName;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<ShopItem> items;

}
