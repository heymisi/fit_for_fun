package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.SportType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "shop_item")
public class ShopItem extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    /*
    @OneToOne
    @JoinColumn(name = "sport_type_id")
    private SportType sportType;
    */
    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date lastUpdated;

    private Integer unitsInStock;

    @Lob
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    /*
    @OneToMany(mappedBy = "shopItem")
    private List<TransactionItem> transactionItems;

    @ElementCollection
    private List<String> availableSizes;
*/
}
