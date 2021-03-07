package hu.fitforfun.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Data
@Entity(name = "password_reset_token")
public class PasswordResetTokenEntity extends BaseEntity {

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userDetails;
}
