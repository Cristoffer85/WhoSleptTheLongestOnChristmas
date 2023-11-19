package how.to.unknownkoderspringsecurity.controller;

import how.to.unknownkoderspringsecurity.model.RegistrationDTO;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
}
