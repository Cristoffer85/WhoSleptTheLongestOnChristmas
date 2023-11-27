package how.Long.didyousleeponchristmas.controller;

import how.Long.didyousleeponchristmas.model.LoginResponseDTO;
import how.Long.didyousleeponchristmas.model.RegistrationDTO;
import how.Long.didyousleeponchristmas.model.User;
import how.Long.didyousleeponchristmas.service.AuthenticationService;
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
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getMaxHoursSlept(),
                body.getWeekday());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}