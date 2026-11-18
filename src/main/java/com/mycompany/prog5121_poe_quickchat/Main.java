package com.mycompany.prog5121_poe_quickchat;

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to QuickChat.");
        // Load any messages saved earlier in the JSON file when the program starts.
          Message.loadStoredMessagesFromJSON("messages.json");
        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    System.out.print("How many messages do you wish to enter? ");
                    int numMessages = Integer.parseInt(scanner.nextLine());
                    
                    for (int i = 1; i <= numMessages; i++) {
                        System.out.println("\n--- Message " + i + " ---");
                        System.out.print("Enter recipient cell number (+27...): ");
                        String recipient = scanner.nextLine();
                        
                        System.out.print("Enter message (max 250 chars): ");
                        String text = scanner.nextLine();
                        
                        if (text.length() > 250) {
                            int excess = text.length() - 250;
                            System.out.println("Message exceeds 250 characters by " + excess + "; please reduce the size.");
                            i--; 
                            continue;
                        }
                        
                        Random rand = new Random();
                        String messageID = String.format("%010d", rand.nextInt(1000000000));
                        
                        Message msg = new Message(messageID, i, recipient, text);
                        
                        if (!msg.checkRecipientCell().contains("successfully")) {
                            System.out.println(msg.checkRecipientCell());
                            i--; 
                            continue;
                        }
                        
                        System.out.println("Message ready to send.");
                        System.out.println("1) Send  2) Disregard  3) Store");
                        System.out.print("Choice: ");
                        String sendChoice = scanner.nextLine();
                        
                        System.out.println(msg.SentMessage(sendChoice));
                        
                        if (sendChoice.equals("1")) {
                            System.out.println("\n--- Message Details ---");
                            System.out.println(msg.printMessages());
                        }
                    }
                    System.out.println("\nTotal messages sent: " + new Message().returnTotalMessagess());
                    break;
                    
                case "2":
                    System.out.println("Coming Soon.");
                    break;
                    
                case "3":
                    System.out.println("Exiting QuickChat. Goodbye!");
                    running = false;
                    break;
                // Option 4: Stored Messages sub-menu.    
                case "4":
                    boolean backToMenu = false;
                    // The sub-menu keeps showing until the user chooses g (back to main menu).
                    while (!backToMenu) {
                        System.out.println("\n--- Stored Messages Menu ---");
                        System.out.println("a) Display sender and recipient of all stored messages");
                        System.out.println("b) Display the longest stored message");
                        System.out.println("c) Search for a message ID");
                        System.out.println("d) Search for messages by recipient");
                        System.out.println("e) Delete a message using the message hash");
                        System.out.println("f) Display full report");
                        System.out.println("g) Back to main menu");
                        System.out.print("Choose: ");

                        String storedChoice = scanner.nextLine().toLowerCase();

                        switch (storedChoice) {
                            case "a":
                                System.out.println(Message.displaySenderAndRecipient());
                                break;
                            case "b":
                                System.out.println(Message.getLongestMessage());
                                break;
                            case "c":
                                System.out.print("Enter Message ID: ");
                                System.out.println(Message.searchByMessageID(scanner.nextLine()));
                                break;
                           // Show every message found for the recipient, or say that none were found.     
                           case "d":
                                System.out.print("Enter recipient: ");
                                ArrayList<String> found = Message.searchByRecipient(scanner.nextLine());
                                if (found.isEmpty()) {
                                System.out.println("No messages found for this recipient.");
                                } else {
                                    for (String m : found) {
                                        System.out.println(m);
                                    }
                                }
                                break;
                            // Delete a message by typing its message hash.    
                            case "e":
                                System.out.print("Enter Message Hash: ");
                                System.out.println(Message.deleteByHash(scanner.nextLine()));
                                break;
                            case "f":
                                System.out.println(Message.displayFullReport());
                                break;
                            case "g":
                                backToMenu = true;
                                break;
                            default:
                                System.out.println("Invalid option.");
        }
    }
    break;
                    
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3 or 4");
            }
        }
        scanner.close();
    }
}
