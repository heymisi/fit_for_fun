package hu.fitforfun.model.shop;

import hu.fitforfun.enums.ShopItemType;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.SportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shop_item")
public class ShopItem extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "sport_type_id")
    private SportType sportType;

    @Column(nullable = false)
    private Double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;

    @Lob
    private String description;

    @Column(name = "item_type")
    @Enumerated
    private ShopItemType itemType;

    @OneToMany(mappedBy = "shopItem")
    private List<TransactionItem> transactionItems;

    @ElementCollection
    private List<String> availableSizes;

    private Integer stock;
}
