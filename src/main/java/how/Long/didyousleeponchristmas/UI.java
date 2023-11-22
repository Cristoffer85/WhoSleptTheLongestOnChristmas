package how.Long.didyousleeponchristmas;

import how.Long.didyousleeponchristmas.controller.AuthenticationController;
import how.Long.didyousleeponchristmas.controller.UserController;
import how.Long.didyousleeponchristmas.model.RegistrationDTO;
import how.Long.didyousleeponchristmas.model.User;
import how.Long.didyousleeponchristmas.service.AuthenticationService;
import how.Long.didyousleeponchristmas.controller.AdminController;
import how.Long.didyousleeponchristmas.model.LoginResponseDTO;
import how.Long.didyousleeponchristmas.model.Role;
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

    @Autowired
    private UserController userController;

    @Autowired
    private AuthenticationController authenticationController;

    private final AuthenticationService authenticationService;

    public UI(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nWelcome to the How long did you sleep last Christmas? application!\n");

            System.out.println("1. Login");
            System.out.println("2. Create User");

            System.out.print("\nEnter your choice: ");

            // Read the choice as a trimmed string
            String choiceInput = scanner.nextLine().trim();

            // Check if the choice is blank or empty
            if (choiceInput.isEmpty()) {
                System.out.println("\nInvalid choice. Please enter a valid number.");
                continue;
            }

            // Validate the choice to ensure it's an integer
            try {
                int choice = Integer.parseInt(choiceInput);

                if (choice == 0 || choice > 2) {
                    System.out.println("\nInvalid choice. Please enter a valid number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        handleLogin(scanner);
                        break;
                    case 2:
                        AdminCreateUser(scanner);
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please enter a valid number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid choice. Please enter a valid number.");
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
            System.out.println("""
            Admin Menu:
            1. View All Users
            2. View One User
            3. Create User
            4. Update User
            5. Delete User
            0. Exit""");

            System.out.print("\nEnter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid choice. Please enter a valid integer.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> AdminViewAllUsers();
                case 2 -> AdminViewOneUser(scanner);
                case 3 -> AdminCreateUser(scanner);
                case 4 -> AdminUpdateUser(scanner);
                case 5 -> AdminDeleteUser(scanner);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void AdminViewAllUsers() {
        List<User> users = adminController.getAllUsers();
        if (!users.isEmpty()) {
            System.out.println("All Users:");
            for (User user : users) {
                System.out.println("Username: " + user.getUsername());
                System.out.println("Authorities: " + user.getAuthorityStrings());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No users found.");
        }
    }

    private void AdminViewOneUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String userId = scanner.nextLine();
        User user = adminController.getOneUser(userId);
        if (user != null) {
            System.out.println("User Details:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Authorities: " + user.getAuthorityStrings());
        } else {
            System.out.println("User not found.");
        }
    }

    private void AdminCreateUser(Scanner scanner) {
        String username;
        String password;

        // Loop for username validation
        while (true) {
            System.out.print("Enter username: ");
            username = scanner.nextLine();

            // Validate username
            if (isValidString(username)) {
                break; // Exit the loop if the username is valid
            } else {
                System.out.println("\nInvalid username. Please enter a non-empty string.");
            }
        }

        // Loop for password validation
        while (true) {
            System.out.print("Enter password: ");
            password = scanner.nextLine();

            // Validate password
            if (isValidString(password)) {
                break; // Exit the loop if the password is valid
            } else {
                System.out.println("\nInvalid password. Please enter a non-empty string.");
            }
        }

        // Create RegistrationDTO
        RegistrationDTO registrationDTO = new RegistrationDTO(username, password);

        // Call the registerUser method in AuthenticationController
        User createdUser = authenticationController.registerUser(registrationDTO);

        if (createdUser != null) {
            System.out.println("\nUser created successfully");
        } else {
            System.out.println("\nFailed to create user");
        }
    }


    private void AdminUpdateUser(Scanner scanner) {
        String userId;
        User existingUser;

        // Loop for user ID validation
        while (true) {
            System.out.print("Enter username to update: ");
            userId = scanner.nextLine();
            existingUser = adminController.getOneUser(userId);

            // Validate user ID
            if (existingUser != null) {
                break; // Exit the loop if the user ID is valid
            } else {
                System.out.println("User not found. Please enter a valid user ID.");
            }
        }

        String newUsername;
        String newPassword;

        // Loop for new username validation
        while (true) {
            System.out.print("Enter new username: ");
            newUsername = scanner.nextLine();

            // Validate new username
            if (isValidString(newUsername)) {
                break; // Exit the loop if the new username is valid
            } else {
                System.out.println("Invalid username. Please enter a non-empty string.");
            }
        }

        // Loop for new password validation
        while (true) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine();

            // Validate new password
            if (isValidString(newPassword)) {
                break; // Exit the loop if the new password is valid
            } else {
                System.out.println("Invalid password. Please enter a non-empty string.");
            }
        }

        existingUser.setUsername(newUsername);
        existingUser.setPassword(newPassword);

        User updatedUser = adminController.updateUser(userId, existingUser);

        if (updatedUser != null) {
            System.out.println("User updated successfully");
        } else {
            System.out.println("Failed to update user");
        }
    }

    private void AdminDeleteUser(Scanner scanner) {
        System.out.print("Enter username to delete: ");
        String userId = scanner.nextLine();
        adminController.deleteOneUser(userId);
        System.out.println("User deleted successfully");
    }

    private void handleUserMenu(Scanner scanner) {
        while (true) {
            System.out.println("""
            User Menu:
            1. View All Users
            2. View One User
            0. Exit""");

            System.out.print("\nEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> UserViewAllUsers();
                case 2 -> UserViewOneUser(scanner);
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void UserViewAllUsers() {
        List<User> users = userController.getAllUsers();
        if (!users.isEmpty()) {
            System.out.println("All Users:");
            for (User user : users) {
                System.out.println("Username: " + user.getUsername());
                System.out.println("Authorities: " + user.getAuthorityStrings());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No users found.");
        }
    }

    private void UserViewOneUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String userId = scanner.nextLine();
        User user = userController.getOneUser(userId);
        if (user != null) {
            System.out.println("User Details:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Authorities: " + user.getAuthorityStrings());
        } else {
            System.out.println("User not found.");
        }
    }

    private boolean isValidString(String input) {
        return !input.isEmpty();
    }
}