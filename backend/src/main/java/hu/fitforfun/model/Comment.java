package hu.fitforfun.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.facility.SportFacility;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.shop.ShopItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "comment_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Comment extends BaseEntity {

    @Column(name = "commenter")
    private String commenterName;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;

    @Lob
    @Column(name = "text")
    private String text;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sport_facility_id")
    private SportFacility sportFacility;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_item_id")
    private ShopItem shopItem;
}
