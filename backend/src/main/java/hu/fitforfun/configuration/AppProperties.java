package hu.fitforfun.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
    @Autowired
    private Environment env;

    public String getEmailSender(){ return env.getProperty("email.sender");}
    public String getEmailVerifyEmailSubject(){ return env.getProperty("email.verify-email.subject");}
    public String getEmailVerifyEmailHtmlBody(){ return env.getProperty("email.verify-email.html-body");}
    public String getEmailVerifyEmailTextBody(){ return env.getProperty("email.verify-email.text-body");}
}
