package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.User;

import java.util.List;

public interface UserService {

    User getUserById(Long id) throws FitforfunException;

    User deleteUser(Long id) throws FitforfunException;

    List<User> listUsers();

    User createUser(User user) throws FitforfunException;
}
