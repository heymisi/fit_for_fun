package hu.fitforfun.services.impl;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.user.PasswordResetTokenEntity;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.AddressRepository;
import hu.fitforfun.repositories.PasswordResetTokenEntityRepository;
import hu.fitforfun.repositories.RoleRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.security.SecurityConstants;
import hu.fitforfun.security.UserPrincipal;
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

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Autowired
    private PasswordResetTokenEntityRepository passwordResetTokenEntityRepository;

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
    public User createUser(User user, String role) throws Exception {
        if (userRepository.findByContactDataEmail(user.getContactData().getEmail()).isPresent()) {
            throw new FitforfunException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // user.setRole(UserRole.USER);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmailVerificationToken(TokenUtils.generateToken(user.getContactData().getEmail(),SecurityConstants.EXPIRATION_TIME));
        user.setEmailVerificationStatus(false);
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findByName(role))));
        //new EmailServiceImpl().sendRegistrationMail(user);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) throws FitforfunException {

        Optional<User> userToUpdate = userRepository.findById(id);
        if (!userToUpdate.isPresent()) {
            throw new FitforfunException(ErrorCode.USER_NOT_EXISTS);
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

        if (user != null) {
            if (!TokenUtils.hasTokenExpired(token)) {
                user.setEmailVerificationToken(null);
                user.setEmailVerificationStatus(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        boolean returnValue = false;

        Optional<User> optionalUser = userRepository.findByContactDataEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = optionalUser.get();
        String token = TokenUtils.generateToken(user.getContactData().getEmail(), SecurityConstants.PASSWORD_RESET_EXPIRATION_TIME);
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(user);
        passwordResetTokenEntityRepository.save(passwordResetTokenEntity);

        returnValue = new EmailServiceImpl().sendPasswordResetRequest(user.getFirstName(),
                user.getContactData().getEmail(), token);
        return returnValue;

    }

    @Override
    public boolean resetPassword(String token, String password) {
        boolean returnValue = false;
        if(TokenUtils.hasTokenExpired(token)){
            return false;
        }
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenEntityRepository.findByToken(token);
        if(passwordResetTokenEntity != null){
            return false;
        }
        String encodedPass = bCryptPasswordEncoder.encode(password);
        User user = passwordResetTokenEntity.getUserDetails();
        user.setPassword(encodedPass);
        User savedUSer = userRepository.save(user);
        if(savedUSer != null && savedUSer.getPassword().equalsIgnoreCase(encodedPass)){
            returnValue = true;
        }
        passwordResetTokenEntityRepository.delete(passwordResetTokenEntity);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByContactDataEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(email);
        }
        User user = optionalUser.get();
        return new UserPrincipal(user);
       // return new org.springframework.security.core.userdetails.User(user.getEmail(),
         //       user.getPassword(), user.getEmailVerificationStatus(), true, true, true, user.getRoles());
    }
}
