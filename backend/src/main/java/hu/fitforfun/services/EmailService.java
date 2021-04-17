package hu.fitforfun.services;

import hu.fitforfun.model.user.User;

public interface EmailService {

    boolean sendPasswordResetRequest(String firstName, String email, String token);

     void sendRegistrationMail(User user) throws Exception;

    void sendContactUsEmail(String email, String message) throws Exception;
}
