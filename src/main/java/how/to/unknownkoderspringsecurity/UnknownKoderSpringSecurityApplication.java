package how.to.unknownkoderspringsecurity;

import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.repository.RoleRepository;
import how.to.unknownkoderspringsecurity.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.util.ClassUtils.isPresent;

@SpringBootApplication
public class UnknownKoderSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnknownKoderSpringSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
        return args ->{
            if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
            Role adminRole = roleRepository.save(new Role("ADMIN"));
                             roleRepository.save(new Role("USER"));

                             Set<Role> roles = new HashSet<>();
                             roles.add(adminRole);

                             User admin = new User(1, "admin", passwordEncoder.encode("password"), roles);

                             userRepository.save(admin);
        };
    }
}