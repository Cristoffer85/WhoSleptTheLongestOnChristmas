package how.to.unknownkoderspringsecurity.configuration;

import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.repository.RoleRepository;
import how.to.unknownkoderspringsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class AdminSetup {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        if (roleRepository.findByAuthority("ADMIN").isEmpty()) {
            roleRepository.save(new Role(1, "ADMIN"));
        }

        if (roleRepository.findByAuthority("USER").isEmpty()) {
            roleRepository.save(new Role(2, "USER"));
        }
    }

    private void initializeAdminUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            Role adminRole = roleRepository.findByAuthority("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            Set<Role> roles = new HashSet<>();

            //Create a new Admin----------------
            roles.add(adminRole);
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("superadminpassword"));
            admin.setAuthorities(roles);
            userRepository.save(admin);
            //----------------------------------

            //Create a new Admin----------------
            roles.add(adminRole);
            User admin2 = new User();
            admin2.setUsername("admin2");
            admin2.setPassword(passwordEncoder.encode("superadminpassword2"));
            admin2.setAuthorities(roles);
            userRepository.save(admin2);
            //----------------------------------
        }
    }
}