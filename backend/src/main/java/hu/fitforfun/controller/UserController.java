package hu.fitforfun.controller;

import hu.fitforfun.enums.Roles;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.address.Address;
import hu.fitforfun.model.user.User;
import hu.fitforfun.model.request.PasswordResetModel;
import hu.fitforfun.model.request.PasswordResetRequestModel;
import hu.fitforfun.repositories.AddressRepository;
import hu.fitforfun.repositories.UserRepository;
import hu.fitforfun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Response saveUser(@RequestBody User user) {
        try {
            return Response.createOKResponse(userService.createUser(user, Roles.ROLE_USER.name()));
        } catch (Exception e) {
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

    @PreAuthorize("hasAuthority('DELETE_AUTHORITY') or #id == principal.userId")
    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable Long id) {
        try {
            return Response.createOKResponse(userService.deleteUser(id));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
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
        boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());
        if (operationResult) {
            return Response.createOKResponse("Request password reset");
        } else {
            return Response.createErrorResponse("Error during request password reset");
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
        System.err.println(email);
        if (userRepository.findByEmail(email).isPresent()) {
            System.err.println("true");
            return true;
        } else {
            System.err.println("false");
            return false;
        }
    }
    @GetMapping("/get-address")
    public List<Address> getAddresses(){
        return this.addressRepository.findAll();
    }
}
