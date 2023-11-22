package how.Long.didyousleeponchristmas.controller;

import how.Long.didyousleeponchristmas.model.User;
import how.Long.didyousleeponchristmas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String helloAdminController() {
        return "Admin level access";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/getOneUser/{userId}")
    public User getOneUser(@PathVariable String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/updateUser/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User user) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser != null) {
            // Update user fields as needed
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setAuthorities(user.getAuthorities());
            return userRepository.save(existingUser);
        }
        return null; // User not found
    }

    @DeleteMapping("/deleteOneUser/{userId}")
    public void deleteOneUser(@PathVariable String userId) {
        userRepository.deleteById(userId);
    }
}