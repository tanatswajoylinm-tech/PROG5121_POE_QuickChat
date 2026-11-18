/*
 * References
 * Leary, S. (2023) JSON-java (Version 20231013) [Software].
 * Available at: https://github.com/stleary/JSON-java [Accessed 18 November 2026].
 *
 * Stack Overflow. 2010. Validate South Africa Cell Phone Number [Online].
 * Available at: https://stackoverflow.com/questions/4058001/validate-south-africa-cell-phone-number
 * [Accessed 18 September 2026].
 */
package com.mycompany.prog5121_poe_quickchat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
// JSON library used to save and read messages (Leary, 2023)
import org.json.JSONObject;

public class Message {
    private String messageID;
    private int messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // ===== Part 3 arrays (static so all Message objects share them) =====
    // Part 3 lists. They are static so every Message object uses the same lists.
    public static ArrayList<String> sentMessages = new ArrayList<>();
    public static ArrayList<String> disregardedMessages = new ArrayList<>();
    public static ArrayList<String> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();

    // extra lists so I can search by recipient: same index = same message
    // Extra lists so I can search by recipient. The same index in every list belongs to the same message.
    public static ArrayList<String> messageRecipients = new ArrayList<>();
    public static ArrayList<String> messageTexts = new ArrayList<>();
    public static ArrayList<String> messageFlags = new ArrayList<>(); // Sent, Stored, Disregard

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
        // Regular expression for a South African number with +27 (Stack Overflow, 2010)
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

    // CHANGED: every choice now saves the message into the Part 3 arrays
    // Saves the message depending on the choice: 1 = sent, 2 = disregarded, 3 = stored.
    public String SentMessage(String option) {
        switch (option) {
            case "1":
                addRecord(messageID, messageHash, recipient, messageText, "Sent");
                return "Message successfully sent.";
            case "2":
                addRecord(messageID, messageHash, recipient, messageText, "Disregard");
                return "Press 0 to delete the message.";
            case "3":
                storeMessage();
                addRecord(messageID, messageHash, recipient, messageText, "Stored");
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

    // CHANGED: only writes to the JSON file now (the array is filled in SentMessage)
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
    }

    // ===================== PART 3 =====================

    // Adds one message to all the arrays
    // Adds one message to all the lists. The flag says if it was Sent, Stored or Disregard.
    public static void addRecord(String id, String hash, String recipient, String text, String flag) {
        messageIDs.add(id);
        messageHashes.add(hash);
        messageRecipients.add(recipient);
        messageTexts.add(text);
        messageFlags.add(flag);

        if (flag.equals("Sent")) {
            sentMessages.add(text);
        } else if (flag.equals("Stored")) {
            storedMessages.add(text);
        } else {
            disregardedMessages.add(text);
        }
    }

    // Empties every array (used by the unit tests)
    // Empties all the lists so each unit test starts with a clean slate.
    public static void clearAll() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        messageRecipients.clear();
        messageTexts.clear();
        messageFlags.clear();
    }

    // Reads messages.json (one JSON object per line) into the arrays
    // Reads messages.json line by line and puts each stored message back into the lists.
    public static void loadStoredMessagesFromJSON(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                JSONObject obj = new JSONObject(line);
                addRecord(obj.getString("MessageID"), obj.getString("MessageHash"),
                          obj.getString("Recipient"), obj.getString("Message"), "Stored");
            }
        } catch (IOException e) {
            System.out.println("No stored messages file found yet.");
        }
    }

    // a. sender and recipient of all stored messages
    // Loops through the messages and shows the recipient of each one flagged Stored.
    public static String displaySenderAndRecipient() {
        String output = "";
        for (int i = 0; i < messageTexts.size(); i++) {
            if (messageFlags.get(i).equals("Stored")) {
                output += "Sender: You | Recipient: " + messageRecipients.get(i) + "\n";
            }
        }
        if (output.equals("")) {
            return "No stored messages found.";
        }
        return output;
    }

    // b. longest stored message
    // Keeps the longest stored message found so far and replaces it when a longer one appears.
    public static String getLongestMessage() {
        String longest = "";
        for (int i = 0; i < messageTexts.size(); i++) {
            if (messageFlags.get(i).equals("Stored") && messageTexts.get(i).length() > longest.length()) {
                longest = messageTexts.get(i);
            }
        }
        if (longest.equals("")) {
            return "No stored messages.";
        }
        return longest;
    }

    // c. search by message ID
    // Loops until the message ID matches, then returns that recipient and message.
    public static String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id)) {
                return "Recipient: " + messageRecipients.get(i) + " | Message: " + messageTexts.get(i);
            }
        }
        return "Message ID not found.";
    }

    // d. all sent or stored messages for one recipient
    // Collects every sent or stored message for this recipient. Disregarded ones are skipped.
    public static ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < messageRecipients.size(); i++) {
            boolean notDisregarded = !messageFlags.get(i).equals("Disregard");
            if (messageRecipients.get(i).equals(recipient) && notDisregarded) {
                found.add(messageTexts.get(i));
            }
        }
        return found;
    }

    // e. delete using the message hash
    // Finds the message with this hash and removes it from every list so the lists stay lined up.
    public static String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equalsIgnoreCase(hash)) {
                String text = messageTexts.get(i);
                String flag = messageFlags.get(i);

                if (flag.equals("Sent")) {
                    sentMessages.remove(text);
                } else if (flag.equals("Stored")) {
                    storedMessages.remove(text);
                } else {
                    disregardedMessages.remove(text);
                }

                messageIDs.remove(i);
                messageHashes.remove(i);
                messageRecipients.remove(i);
                messageTexts.remove(i);
                messageFlags.remove(i);

                return "Message: \"" + text + "\" successfully deleted.";
            }
        }
        return "Message hash not found.";
    }

    // f. full report (sent and stored messages)
    // Builds a report with the ID, hash, recipient and message of every sent or stored message.
    public static String displayFullReport() {
        String report = "";
        for (int i = 0; i < messageTexts.size(); i++) {
            if (!messageFlags.get(i).equals("Disregard")) {
                report += "Message ID   : " + messageIDs.get(i) + "\n";
                report += "Message Hash : " + messageHashes.get(i) + "\n";
                report += "Recipient    : " + messageRecipients.get(i) + "\n";
                report += "Message      : " + messageTexts.get(i) + "\n";
                report += "--------------------------------------\n";
            }
        }
        if (report.equals("")) {
            return "No messages to display.";
        }
        return report;
    }
   
}
