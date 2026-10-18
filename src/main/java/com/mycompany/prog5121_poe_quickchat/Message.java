package com.mycompany.prog5121_poe_quickchat;
// Oct 15
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.json.JSONObject;
// Oct 16 
public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;
    // Oct 18 
    // Arrays for Part 3
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    
    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash();
    }
    
    public Message() {}

    public boolean checkMessageID() {
        return this.messageID != null && this.messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        String regex = "^\\+27[0-9]{9}$";
        if (this.recipient != null && this.recipient.matches(regex)) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    public String checkMessageLength() {
        if (this.messageText.length() > 250) {
            int excess = this.messageText.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }

    public String createMessageHash() {
        String idPrefix = this.messageID.substring(0, 2);
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words[0].replaceAll("[^a-zA-Z]", "");
        String lastWord = words[words.length - 1].replaceAll("[^a-zA-Z]", "");
        return (idPrefix + ":" + this.messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(String option) {
        switch (option) {
            case "1":
                sentMessages.add(this.messageText);
                return "Message successfully sent.";
            case "2":
                disregardedMessages.add(this.messageText);
                return "Press 0 to delete the message.";
            case "3":
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public String printMessages() {
        return "Message ID: " + this.messageID + 
               "\nMessage Hash: " + this.messageHash + 
               "\nRecipient: " + this.recipient + 
               "\nMessage: " + this.messageText;
    }

    public int returnTotalMessagess() {
        return sentMessages.size();
    }

    public void storeMessage() {
        JSONObject obj = new JSONObject();
        obj.put("MessageID", this.messageID);
        obj.put("MessageHash", this.messageHash);
        obj.put("Recipient", this.recipient);
        obj.put("Message", this.messageText);

        try (FileWriter file = new FileWriter("messages.json", true)) {
            file.write(obj.toString() + "\n");
            file.flush();
        } catch (IOException e) {
            System.out.println("Error storing message to JSON.");
        }
        storedMessages.add(this.messageText);
    }
}
