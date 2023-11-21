package how.to.unknownkoderspringsecurity.controller;

import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserRepository userRepository;  // Assuming you have a UserRepository interface

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