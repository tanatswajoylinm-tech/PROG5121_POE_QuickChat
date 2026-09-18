package com.mycompany.prog5121_poe_quickchat;

import java.util.Scanner;

/**
 * Main class for the QuickChat application.
 * Handles the console-based registration and login interface.
 * 
 * @author Tanatswa Joylin M
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("========================================");
        System.out.println("     Welcome to QuickChat - Part 1      ");
        System.out.println("========================================");

        // --- REGISTRATION ---
        System.out.println("\n--- Registration ---");
        
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter username (must contain '_' and be max 5 chars): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password (min 8 chars, 1 capital, 1 number, 1 special): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter cell phone number (format: +27XXXXXXXXX): ");
        String cellPhone = scanner.nextLine();

        String registrationMessage = login.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println("\n" + registrationMessage);

        // --- LOGIN (only if registration was successful) ---
        if (registrationMessage.startsWith("Username successfully")) {
            System.out.println("\n--- Login ---");
            
            System.out.print("Enter username: ");
            String loginUsername = scanner.nextLine();
            
            System.out.print("Enter password: ");
            String loginPassword = scanner.nextLine();

            boolean isLoggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println("\n" + login.returnLoginStatus(isLoggedIn));
        } else {
            System.out.println("\nRegistration failed. Please restart and try again.");
        }

        scanner.close();
    }
}