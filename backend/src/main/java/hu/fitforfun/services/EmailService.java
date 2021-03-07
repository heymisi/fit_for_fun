package hu.fitforfun.services;

import hu.fitforfun.model.User;

public interface EmailService {
    void verifyEmail(User user);
    boolean sendPasswordResetRequest(String firstName, String email, String token);
}
