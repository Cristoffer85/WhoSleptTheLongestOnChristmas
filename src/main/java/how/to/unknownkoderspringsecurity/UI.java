package how.to.unknownkoderspringsecurity;

import how.to.unknownkoderspringsecurity.model.LoginResponseDTO;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UI implements CommandLineRunner {

    private final AuthenticationService authenticationService;

    public UI(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Create User");
            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    createUser(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            LoginResponseDTO loginResponse = authenticationService.loginUser(username, password);

            if (loginResponse.getUser() != null) {
                System.out.println("Login successful");
                System.out.println("User: " + loginResponse.getUser().getUsername());
                System.out.println("JWT: " + loginResponse.getJwt());
            } else {
                System.out.println("Login failed");
            }
        } catch (BadCredentialsException e) {
            System.out.println("Incorrect Credentials. Try again.");
        }
    }

    private void createUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = authenticationService.registerUser(username, password);

        if (newUser != null) {
            System.out.println("User created successfully");
            System.out.println("User: " + newUser.getUsername());
        } else {
            System.out.println("Failed to create user");
        }
    }
}
