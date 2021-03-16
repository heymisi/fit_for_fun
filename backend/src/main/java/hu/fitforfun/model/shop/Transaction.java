package hu.fitforfun.model.shop;

import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "transaction")
public class Transaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchaser_id")
    private User purchaser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "sum_total")
    private Double sumTotal;

    @OneToMany(
            mappedBy = "transaction",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TransactionItem> transactionItems;
}
