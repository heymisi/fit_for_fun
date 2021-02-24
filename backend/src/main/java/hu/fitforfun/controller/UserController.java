package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.User;
import hu.fitforfun.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${datadir}")
    private String datadir;

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
            return Response.createOKResponse(userService.createUser(user));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e.getErrorCode());
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


}
