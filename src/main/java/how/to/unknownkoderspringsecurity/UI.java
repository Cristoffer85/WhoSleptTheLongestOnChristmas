package how.to.unknownkoderspringsecurity;

import how.to.unknownkoderspringsecurity.controller.AdminController;
import how.to.unknownkoderspringsecurity.model.LoginResponseDTO;
import how.to.unknownkoderspringsecurity.model.Role;
import how.to.unknownkoderspringsecurity.model.User;
import how.to.unknownkoderspringsecurity.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class UI implements CommandLineRunner {

    @Autowired
    private AdminController adminController;

    private final AuthenticationService authenticationService;

    public UI(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the How long did you sleep last christmas application!\n");

            System.out.println("1. Login");
            System.out.println("2. Create User");

            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    handleLogin(scanner);
                    break;
                case 2:
                    createUser(scanner);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleLogin(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        LoginResponseDTO loginResponse = authenticationService.loginUser(username, password);

        if (loginResponse.getUser() != null) {
            System.out.println("\nLogin successful.\n");
            System.out.println("User: " + loginResponse.getUser().getUsername());

            if (loginResponse.getUser().getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"))) {
                handleAdminMenu(scanner);
            } else {
                handleUserMenu(scanner);
            }
        } else {
            System.out.println("\nLogin failed. Bad credentials. Try again.\n");
        }
    }

    private void handleAdminMenu(Scanner scanner) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. View One User");
            System.out.println("3. Create User");
            System.out.println("4. Update User");
            System.out.println("5. Delete User");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    viewOneUser(scanner);
                    break;
                case 3:
                    createUser(scanner);
                    break;
                case 4:
                    updateUser(scanner);
                    break;
                case 5:
                    deleteUser(scanner);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void handleUserMenu(Scanner scanner) {
        while (true) {
            System.out.println("User Menu:");
            System.out.println("1. View All Users");
            System.out.println("2. View One User");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    viewOneUser(scanner);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void viewAllUsers() {
        List<User> users = adminController.getAllUsers();
        if (!users.isEmpty()) {
            System.out.println("All Users:");
            for (User user : users) {
                System.out.println("Username: " + user.getUsername());
                System.out.println("Authorities: " + user.getAuthorities());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No users found.");
        }
    }

    private void viewOneUser(Scanner scanner) {
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        User user = adminController.getOneUser(userId);
        if (user != null) {
            System.out.println("User Details:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Authorities: " + user.getAuthorities());
        } else {
            System.out.println("User not found.");
        }
    }

    private void createUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        // Assuming that you need to set authorities for the new user
        // You can customize this part based on your requirements
        Role userRole = new Role("USER");
        newUser.setAuthorities(Set.of(userRole));

        User createdUser = adminController.createUser(newUser);
        if (createdUser != null) {
            System.out.println("User created successfully");
        } else {
            System.out.println("Failed to create user");
        }
    }

    private void updateUser(Scanner scanner) {
        System.out.print("Enter user ID to update: ");
        String userId = scanner.nextLine();
        User existingUser = adminController.getOneUser(userId);

        if (existingUser != null) {
            System.out.print("Enter new username: ");
            String newUsername = scanner.nextLine();
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();

            existingUser.setUsername(newUsername);
            existingUser.setPassword(newPassword);

            User updatedUser = adminController.updateUser(userId, existingUser);

            if (updatedUser != null) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("Failed to update user");
            }
        } else {
            System.out.println("User not found.");
        }
    }

    private void deleteUser(Scanner scanner) {
        System.out.print("Enter user ID to delete: ");
        String userId = scanner.nextLine();
        adminController.deleteOneUser(userId);
        System.out.println("User deleted successfully");
    }
}