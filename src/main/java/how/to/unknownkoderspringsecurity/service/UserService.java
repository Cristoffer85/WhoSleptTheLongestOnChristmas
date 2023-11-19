package how.to.unknownkoderspringsecurity.service;

import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user detail service");

        if(!username.equals("Ethan")) throw new UsernameNotFoundException("Not Ethan");

        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, "USER"));

        return new User(1, "Ethan", encoder.encode("password"), roles);
    }
}
