package com.mycompany.prog5121_poe_quickchat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    private Message msg1;
    private Message msg2;

    @BeforeEach
    public void setUp() {
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
}
