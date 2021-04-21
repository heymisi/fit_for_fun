package hu.fitforfun.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class ContactData extends BaseEntity{
    private String email;
    private String telNumber;
}
