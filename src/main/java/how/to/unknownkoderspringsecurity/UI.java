package how.to.unknownkoderspringsecurity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UI {

    private final AuthenticationManager authenticationManager;

    public UI(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                """
                \nWelcome to the How long did you sleep last christmas application!
                 
                1. Login
                2. Create User
                """);

        System.out.print("Choose an option: ");

        int option = scanner.nextInt();

        switch (option) {
            case 1:
                login(scanner);
                break;
            case 2:
                createUser(scanner);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            System.out.println("Login successful!");

            // Implement logic to display menu based on authorities (ADMIN or USER)
            displayMenu();
        } catch (Exception e) {
            System.out.println("Login failed. Please try again.");
        }
    }

    private void createUser(Scanner scanner) {
        // Implement logic to create a new user
        // This may involve capturing user details and making a request to your UserController
        // Example: userService.createUser(newUser);
    }

    private void displayMenu() {
        // Implement logic to display the appropriate menu based on user authorities
        // You can use SecurityContextHolder.getContext().getAuthentication() to get the current authentication details
        // Example: Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Set<User> authorities = (Set<User>) authentication.getAuthorities();
        // Then, based on the authorities, display the corresponding menu options
    }
}