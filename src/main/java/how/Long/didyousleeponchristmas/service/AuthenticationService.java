package how.Long.didyousleeponchristmas.service;

import how.Long.didyousleeponchristmas.model.User;
import how.Long.didyousleeponchristmas.model.LoginResponseDTO;
import how.Long.didyousleeponchristmas.model.Role;
import how.Long.didyousleeponchristmas.model.WeekDay;
import how.Long.didyousleeponchristmas.repository.RoleRepository;
import how.Long.didyousleeponchristmas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class AuthenticationService {                // Class that handles Registration of new user, and login of a user (Authenticates that they are valid) Uses LoginResponseDTO among others

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public User registerUser(String username, String password, int maxHoursSlept, WeekDay weekDay) {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            Role userRole = roleRepository.findByAuthority("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found"));

            Set<Role> authorities = new HashSet<>();
            authorities.add(userRole);

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(encodedPassword);
            newUser.setAuthorities(authorities);
            newUser.setMaxHoursSlept(maxHoursSlept);
            newUser.setWeekDay(weekDay);

            // Generate a unique userId (you can use UUID for this)
            String userId = UUID.randomUUID().toString();
            newUser.setUserId(userId);

            return userRepository.save(newUser);

        } catch (DataIntegrityViolationException e) {
            // Catch the exception thrown when there's a unique constraint violation
            throw new RuntimeException("Username '" + username + "' already exists. Please choose a different username.");
        }
    }

    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch (BadCredentialsException e) {
            // Handle bad credentials exception
            return new LoginResponseDTO(null, "Incorrect Credentials");
        } catch (AuthenticationException e) {
            // Handle other authentication exceptions
            return new LoginResponseDTO(null, "Authentication failed");
        }
    }
}