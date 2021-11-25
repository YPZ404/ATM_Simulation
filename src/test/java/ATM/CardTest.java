package ATM;

import ATM.*;
import org.junit.jupiter.api.*;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void testGetCardNumber(){
        Card card_tester = new Card(12345,66666,"lost",150.50,
                "10/19","10/23");
        assertEquals(12345, card_tester.getCardNumber());
    }

    @Test
    public void testGetCardPin(){
        Card card_tester = new Card(12345,666666,"lost",150.50,
                "10/19","10/23");
        assertEquals(666666, card_tester.getCardPin());
    }

    @Test
    public void testGetCardStatus(){
        Card card_tester = new Card(12345,666666,"lost",150.50,
                "10/19","10/23");
        assertEquals("lost", card_tester.getCardStatus());
    }

    @Test
    public void testGetBalance(){
        Card card_tester = new Card(12345,666666,"lost",150.50,
                "10/19","10/23");
        assertEquals(150.50, card_tester.getBalance());
    }

    @Test
    public void testGetBeginDate(){
        Card card_tester = new Card(12345,666666,"lost",150.50,
                "10/19","10/23");
        assertEquals("10/19", card_tester.getBeginDate());
    }

    @Test
    public void testGetEndDate(){
        Card card_tester = new Card(12345,666666,"lost",150.50,
                "10/19","10/23");
        assertEquals("10/23", card_tester.getEndDate());
    }

    @Test
    public void testInDate(){
        Card card_tester_1 = new Card(12345,666666,"lost",150.50,
                "10/19","10/23");
        Card card_tester_2 = new Card(67890,31244,"active",550.50,
                "05/17","05/20");
        Card card_tester_3 = new Card(89563,58763,"inactive",534.50,
                "09/17","09/20");
        assertEquals(true, card_tester_1.InDate());
        assertEquals(false, card_tester_2.InDate());
        assertEquals(false, card_tester_2.InDate());
    }

}
