package hu.fitforfun.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import hu.fitforfun.model.BaseEntity;
import hu.fitforfun.model.ContactData;
import hu.fitforfun.model.instructor.Instructor;
import hu.fitforfun.model.instructor.TrainingSession;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.shop.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "user_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
public class User extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;

    @Column(name = "email_verification_status")
    private Boolean emailVerificationStatus = false;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "client")
    private List<TrainingSession> trainingSessions;

    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "purchaser")
    private List<Transaction> transactions;

    @OneToOne(cascade = CascadeType.ALL)
    private Address shippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    private ContactData contactData;

    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date created;
 /*

    @Column(name = "email_address", nullable = false)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private String birthDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;*/

    public User addTrainingSession(TrainingSession session) {
        session.setClient(this);
        this.trainingSessions.add(session);
        return this;
    }
    public User(){
        this.contactData = new ContactData();
    }

}
