package hu.fitforfun.controller;

import com.amazonaws.http.HttpResponse;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.services.EmailService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/contact-us")
    public Response contactUs(@RequestParam(value = "email", required=true) String email,
                              @RequestParam(value = "message", required = true) String message) {
        try {
            emailService.sendContactUsEmail(email, message);
            return Response.createOKResponse("Ok");
        } catch (Exception e) {
            return Response.createOKResponse("error");
        }

    }
}
