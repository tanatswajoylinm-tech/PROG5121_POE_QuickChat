package com.mycompany.prog5121_poe_quickchat;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private Message msg1;
    private Message msg2;

    @BeforeEach
   public void setUp() {
       Message.clearAll();
        msg1 = new Message("0012345678", 0, "+27718693002", "Hi Mike, can you join us for dinner tonight?");
        msg2 = new Message("1234567890", 1, "08575975889", "Hi Keegan, did you receive the payment?");
    }

    @Test
    public void testMessageLengthSuccess() {
        assertEquals("Message ready to send.", msg1.checkMessageLength());
    }

    @Test
    public void testMessageLengthFailure() {
        String longMsg = "A".repeat(255); 
        Message msgLong = new Message("123", 1, "+27718693002", longMsg);
        assertEquals("Message exceeds 250 characters by 5; please reduce the size.", msgLong.checkMessageLength());
    }

    @Test
    public void testRecipientCellCorrect() {
        assertEquals("Cell phone number successfully captured.", msg1.checkRecipientCell());
    }

    @Test
    public void testRecipientCellIncorrect() {
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", msg2.checkRecipientCell());
    }

    @Test
    public void testMessageHash() {
        assertEquals("00:0:HITONIGHT", msg1.createMessageHash());
    }

    @Test
    public void testMessageID() {
        assertTrue(msg1.checkMessageID());
    }

    @Test
    public void testSentMessageOption1() {
        assertEquals("Message successfully sent.", msg1.SentMessage("1"));
    }

    @Test
    public void testSentMessageOption2() {
        assertEquals("Press 0 to delete the message.", msg2.SentMessage("2"));
    }

    @Test
    public void testSentMessageOption3() {
        assertEquals("Message successfully stored.", msg1.SentMessage("3"));
    }
    // puts the 5 assignment test messages into the arrays
private void loadTestData() {
    Message.addRecord("0000000001", "00:1:DIDCAKE", "+27834557896", "Did you get the cake?", "Sent");
    Message.addRecord("0000000002", "00:2:WHERETIME", "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
    Message.addRecord("0000000003", "00:3:YOHOOGATE", "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
    Message.addRecord("0838884567", "08:4:ITTIME", "0838884567", "It is dinner time!", "Sent");
    Message.addRecord("0000000005", "00:5:OKYOU", "+27838884567", "Ok, I am leaving without you.", "Stored");
}
@Test
public void testSentMessagesArrayCorrectlyPopulated() {
    loadTestData();
    assertEquals("Did you get the cake?", Message.sentMessages.get(0));
    assertEquals("It is dinner time!", Message.sentMessages.get(1));
}

@Test
public void testDisplayLongestMessage() {
    loadTestData();
    assertEquals("Where are you? You are late! I have asked you to be on time.",
                 Message.getLongestMessage());
}

@Test
public void testSearchForMessageID() {
    loadTestData();
    assertEquals("Recipient: 0838884567 | Message: It is dinner time!",
                 Message.searchByMessageID("0838884567"));
}

@Test
public void testSearchByRecipient() {
    loadTestData();
    ArrayList<String> found = Message.searchByRecipient("+27838884567");
    assertEquals("Where are you? You are late! I have asked you to be on time.", found.get(0));
    assertEquals("Ok, I am leaving without you.", found.get(1));
}

@Test
public void testDeleteMessageUsingHash() {
    loadTestData();
    assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                 Message.deleteByHash("00:2:WHERETIME"));
}

@Test
public void testDisplayReport() {
    loadTestData();
    String report = Message.displayFullReport();
    assertTrue(report.contains("00:1:DIDCAKE"));
    assertTrue(report.contains("+27834557896"));
    assertTrue(report.contains("Did you get the cake?"));
}
}
