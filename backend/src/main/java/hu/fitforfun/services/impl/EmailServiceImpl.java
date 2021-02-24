package hu.fitforfun.services.impl;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@ConfigurationProperties(prefix = "email")
@Service
@Data
public class EmailServiceImpl {
    public String sender;

    @Override
    public String toString(){
        return sender;
    }
}
