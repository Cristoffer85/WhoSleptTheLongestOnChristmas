package how.to.unknownkoderspringsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }

    @GetMapping("/me")
    public UserDetails getCurrentUser(Authentication authentication) {
        return (UserDetails) authentication.getPrincipal();
    }
}