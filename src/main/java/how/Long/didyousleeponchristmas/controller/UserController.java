package how.Long.didyousleeponchristmas.controller;

import how.Long.didyousleeponchristmas.model.User;
import how.Long.didyousleeponchristmas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;  // Assuming you have a UserRepository interface

    @GetMapping("/")
    public String helloUserController() {
        return "User access level";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getOneUser/{userId}")
    public User getOneUser(@PathVariable String userId) {
        return userRepository.findById(userId).orElse(null);
    }
}