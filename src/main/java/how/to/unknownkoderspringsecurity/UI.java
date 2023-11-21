package how.to.unknownkoderspringsecurity;

import java.util.Scanner;

public class UI {

    private Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayLoginMenu() {
        while (true) {
            System.out.println("Welcome to the Application!");
            System.out.println("1. Login");
            System.out.println("2. Create new user");
            System.out.println("3. Exit");

            int choice = getUserChoice();

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleCreateUser();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private int getUserChoice() {
        System.out.print("Enter your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume the invalid input
        }
        return scanner.nextInt();
    }

    private void handleLogin() {
        System.out.print("Enter your username: ");
        String username = scanner.next();

        System.out.print("Enter your password: ");
        String password = scanner.next();

        // TODO: Call your authentication service and perform login logic here
        // For simplicity, let's assume a successful login for any input
        System.out.println("Login successful! Welcome, " + username + ".");
    }

    private void handleCreateUser() {
        System.out.print("Enter a new username: ");
        String username = scanner.next();

        System.out.print("Enter a password for the new user: ");
        String password = scanner.next();

        // TODO: Call your user creation service and perform user creation logic here
        // For simplicity, let's assume a successful user creation for any input
        System.out.println("User created successfully! Welcome, " + username + ".");
    }
}