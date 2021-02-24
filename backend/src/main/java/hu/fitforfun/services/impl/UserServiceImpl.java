package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.User;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.UserService;
import hu.fitforfun.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<User> listUsers(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<User> users = userRepository.findAll(pageableRequest);
        List<User> returnValue = users.getContent();
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
        user.setEmailVerificationToken(TokenUtils.generateEmailVerificationToken(user.getId()));
        user.setEmailVerificationStatus(false);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) throws FitforfunException {

        Optional<User> userToUpdate = userRepository.findById(id);
        if (!userToUpdate.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_ALREADY_EXISTS);
        }

        if (!user.getFirstName().isEmpty())
            userToUpdate.get().setFirstName(user.getFirstName());
        if (!user.getLastName().isEmpty())
            userToUpdate.get().setLastName(user.getLastName());

        return userRepository.save(userToUpdate.get());
    }

    @Override
    public boolean verifyEmailToken(String token) {
        User user = userRepository.findUserByEmailVerificationToken(token);

        if(user != null){
            if(!TokenUtils.hasTokenExpired(token)){
                user.setEmailVerificationToken(null);
                user.setEmailVerificationStatus(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),user.getEmailVerificationStatus(),true,true,true, new ArrayList<>());
    }
}
