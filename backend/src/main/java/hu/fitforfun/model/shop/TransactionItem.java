package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.*;
import hu.fitforfun.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction_item")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TransactionItem extends BaseEntity {
    /*
    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "shop_item_id")
    private ShopItem shopItem;
*/
    private Integer quantity;

    private Double price;
    @ManyToOne()
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
