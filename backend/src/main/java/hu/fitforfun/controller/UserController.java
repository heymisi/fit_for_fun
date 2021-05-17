package hu.fitforfun.controller;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.request.UserRegistrationModel;
import hu.fitforfun.model.request.UserUpdateDuringTransactionRequestModel;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.request.PasswordResetModel;
import hu.fitforfun.model.request.PasswordResetRequestModel;
import hu.fitforfun.repositories.AddressRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.TrainingSessionService;
import hu.fitforfun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TrainingSessionService trainingSessionService;

    @GetMapping("")
    public List<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return userService.listUsers(page, limit);
    }

    @GetMapping("/{id}")
    public Response getUser(@PathVariable Long id) {
        try {
            return Response.createOKResponse(userService.getUserById(id));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PostMapping({"", "/"})
    public Response saveUser(@RequestBody UserRegistrationModel user) {
        try {
            return Response.createOKResponse(userService.createUser(user, Roles.ROLE_USER.name()));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.createErrorResponse("error during registration");
        }
    }

    @PutMapping("/{id}")
    public Response updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return Response.createOKResponse(userService.updateUser(id, user));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @PutMapping("/{id}/updateDuringTransaction")
    public Response updateUserDuringTransaction(@PathVariable Long id, @RequestBody UserUpdateDuringTransactionRequestModel user) {
        try {
            return Response.createOKResponse(userService.updateUserDuringTransaction(id, user));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Long id, @RequestParam(value = "pass") String pass) {
        try {
            return userService.deleteUser(id, pass);
        } catch (FitforfunException e) {
            return false;
        }
    }

    @GetMapping("/email-verification")
    public Response verifyEmailToken(@RequestParam(value = "token") String token) {
        if (userService.verifyEmailToken(token)) {
            return Response.createOKResponse("Successful email verification");
        } else {
            return Response.createErrorResponse("Error during email verification");
        }
    }

    @PostMapping("/password-reset-request")
    public Response requestPasswordReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        try {
            userService.requestPasswordReset(passwordResetRequestModel.getEmail());
            return Response.createOKResponse("Successful password reset request");
        } catch (Exception e) {
            return Response.createErrorResponse("Error during password reset request");
        }

    }

    @PostMapping("/password-reset")
    public Response resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
        boolean operationResult = userService.resetPassword(passwordResetModel.getToken(), passwordResetModel.getPassword());
        if (operationResult) {
            return Response.createOKResponse("password reset");
        } else {
            return Response.createErrorResponse("Error during password reset");
        }
    }

    @GetMapping("/email-check/{email}")
    public Boolean isEmailAlreadyUsed(@PathVariable String email) {
        if (userRepository.findByContactDataEmail(email).isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/get-address")
    public List<Address> getAddresses() {
        return this.addressRepository.findAll();
    }

    @GetMapping("/{id}/addTrainingSession")
    public Response addTrainingSession(@PathVariable Long id, @RequestParam(value = "sessionId") Long sessionId) {
        try {
            trainingSessionService.addTrainingSessionToClient(id, sessionId);
            return Response.createOKResponse("Successfully added this training session");
        } catch (Exception e) {
            return Response.createErrorResponse("Couldn't add this training session");
        }
    }

    @GetMapping("/{id}/changePass")
    public Boolean changePassword(@PathVariable Long id, @RequestParam(value = "oldPass") String oldPass, @RequestParam(value = "newPass") String newPass) {
        try {
            return userService.changePassword(id, oldPass, newPass);
        } catch (FitforfunException e) {
            return false;
        }
    }
}
