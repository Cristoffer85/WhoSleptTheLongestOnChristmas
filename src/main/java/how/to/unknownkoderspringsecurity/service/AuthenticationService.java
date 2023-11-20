package how.to.unknownkoderspringsecurity.service;

import how.to.unknownkoderspringsecurity.model.LoginResponseDTO;
import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.repository.RoleRepository;
import how.to.unknownkoderspringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
public class AuthenticationService {

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

    public User registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow(() -> new RuntimeException("USER role not found"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodedPassword);
        newUser.setAuthorities(authorities);

        // Generate a unique userId (you can use UUID for this)
        String userId = UUID.randomUUID().toString();
        newUser.setUserId(userId);

        return userRepository.save(newUser);
    }

    public LoginResponseDTO loginUser(String username, String password){

        try{
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = tokenService.generateJwt(auth);

            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        }catch(AuthenticationException e){
            return new LoginResponseDTO(null,"");
        }
    }
}