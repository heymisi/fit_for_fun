package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
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
    @Column(nullable = true)
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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ItemCategory category;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private SportType sportType;

    /*
    @JsonManagedReference
    @OneToMany(mappedBy = "shopItem")
    private List<TransactionItem> transactionItems;
*/
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shopItem")
    private List<Comment> comments;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    private int rating = 5;

    public ShopItem addComment(Comment comment) {
        comment.setShopItem(this);
        this.comments.add(comment);
        return this;
    }
}
