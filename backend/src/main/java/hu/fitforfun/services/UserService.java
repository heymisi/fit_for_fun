package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserById(Long id) throws FitforfunException;

    User deleteUser(Long id) throws FitforfunException;

    List<User> listUsers();

    User createUser(User user) throws FitforfunException;
}
