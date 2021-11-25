package ATM;

import ATM.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterResolutionException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testGetBalance() {
        Receipt r = new Receipt(1000, 500.00, "Deposit", 1000.00);
        assertEquals(1000.00, r.getBalance(), "The balances are not equal.");
    }

    @Test
    public void testGetTranNumber() {
        Receipt r = new Receipt(1000, 500.00, "Deposit", 1000.00);
        assertEquals(1000, r.getTranNumber(), "The transaction numbers are not matched.");
    }

    @Test
    public void testGetOperationType() {
        Receipt r = new Receipt(1000, 500.00, "Deposit", 1000.00);
        assertEquals("Deposit", r.getOperationType(), "The operation types are not consistent.");
    }

}