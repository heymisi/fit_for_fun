package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.User;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User getUserById(Long id) throws FitforfunException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);

        return user.get();
    }


    @Override
    public User deleteUser(Long id) throws FitforfunException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent())
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);

        userRepository.delete(userToDelete.get());
        return userToDelete.get();
    }

    @Override
    public List<User> listUsers() {
        List<User> returnValue = new ArrayList();
        Iterable<User> users = userRepository.findAll();
        users.forEach(returnValue::add);
        return returnValue;
    }

    @Override
    public User createUser(User user) throws FitforfunException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new FitforfunException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new FitforfunException(ErrorCode.USER_ALREADY_EXISTS);
        }

        // user.setRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
