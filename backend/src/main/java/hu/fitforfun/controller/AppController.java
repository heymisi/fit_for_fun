/*

package hu.fitforfun.controller;

import hu.fitforfun.model.User;
import hu.fitforfun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String print() {
        return "asd";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user")
    public List<User> getAll() {
        return userService.listUsers();
    }

    @GetMapping("/valami")
    public String print2() {
        return "yooo";
    }
}

*/
