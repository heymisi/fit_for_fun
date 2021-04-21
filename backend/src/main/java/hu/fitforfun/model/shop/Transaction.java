package hu.fitforfun.model.shop;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "transaction")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Transaction extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser_id")
    private User purchaser;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "transaction_created")
    @CreationTimestamp
    private Date transactionCreated;

    @Column(name = "sum_total")
    private Double sumTotal;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TransactionItem> transactionItems;

    public Transaction() {
        this.transactionItems = new ArrayList<>();
    }

    public void addItem(TransactionItem transactionItem) {
        if (transactionItem != null) {
            transactionItems.add(transactionItem);
            transactionItem.setTransaction(this);
        }
    }
}
