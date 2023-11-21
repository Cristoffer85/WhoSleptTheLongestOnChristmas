package how.to.unknownkoderspringsecurity;

import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.repository.RoleRepository;
import how.to.unknownkoderspringsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class UnknownKoderSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnknownKoderSpringSecurityApplication.class, args);

        UI terminalUI = new UI();
        terminalUI.displayLoginMenu();
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

        return args -> {
            if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role(1, "ADMIN"));
            roleRepository.save(new Role(2, "USER"));

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);

            User admin = new User("1", "admin", passwordEncoder.encode("superadminpassword"), roles);

            userRepository.save(admin);
        };
    }
}