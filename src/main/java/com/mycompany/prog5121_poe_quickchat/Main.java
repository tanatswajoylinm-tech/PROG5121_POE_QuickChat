package com.mycompany.prog5121_poe_quickchat;

import java.util.Scanner;
import java.util.Random;
// Oct 17
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to QuickChat.");
        
        while (running) {
            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
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
                    
                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
        scanner.close();
    }
}
