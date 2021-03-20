package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hu.fitforfun.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "item_category")
// @Data --known bug ??
@Getter
@Setter
public class ItemCategory extends BaseEntity {

    @Column(name = "category_name")
    private String categoryName;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Set<ShopItem> items;

}
