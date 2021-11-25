package ATM;

import ATM.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    public void testdeposit() {
        ATM atmtester = new ATM();
        atmtester.setDatabase("CardDatabase.txt");
        Card card_tester = atmtester.getCardList().get(0);
        atmtester.setCurrentCard(card_tester);
        atmtester.setupMoney(100,100);

        String amount1 = "0";
        System.setIn(new ByteArrayInputStream(amount1.getBytes()));
        Scanner scanner_1 = new Scanner(System.in);
        //assertEquals("50",scanner.next());
        assertEquals(false,Main.deposit(scanner_1,atmtester));

        String amount2 = "10";
        System.setIn(new ByteArrayInputStream(amount2.getBytes()));
        Scanner scanner_2 = new Scanner(System.in);
        //assertEquals("50",scanner.next());
        assertEquals(true,Main.deposit(scanner_2,atmtester));

        String amount3 = "10.1";
        System.setIn(new ByteArrayInputStream(amount3.getBytes()));
        Scanner scanner_3 = new Scanner(System.in);
        //assertEquals("50",scanner.next());
        assertEquals(false,Main.deposit(scanner_3,atmtester));

        String amount4 = "C";
        System.setIn(new ByteArrayInputStream(amount4.getBytes()));
        Scanner scanner_4 = new Scanner(System.in);
        //assertEquals("50",scanner.next());
        assertEquals(false,Main.deposit(scanner_4,atmtester));
    }

    @Test
    public void testwithdraw(){
        ATM atmtester = new ATM();
        atmtester.setDatabase("CardDatabase.txt");
        Card card_tester = atmtester.getCardList().get(0);
        atmtester.setCurrentCard(card_tester);
        atmtester.setupMoney(100,100);

        String test_1 = "10" + System.getProperty("line.separator") + "0.20" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_1.getBytes()));
        Scanner scanner_1 = new Scanner(System.in);
        assertEquals(true,Main.withdraw(scanner_1,atmtester));

        String test_2 = "200" + System.getProperty("line.separator") + "0.20" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_2.getBytes()));
        Scanner scanner_2 = new Scanner(System.in);
        assertEquals(false,Main.withdraw(scanner_2,atmtester));

        String test_3 = "100" + System.getProperty("line.separator") + "0.200" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_3.getBytes()));
        Scanner scanner_3 = new Scanner(System.in);
        assertEquals(false,Main.withdraw(scanner_3,atmtester));

        String test_4 = "0" + System.getProperty("line.separator") + "0" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_4.getBytes()));
        Scanner scanner_4 = new Scanner(System.in);
        assertEquals(false,Main.withdraw(scanner_4,atmtester));

        String test_5 = "-1" + System.getProperty("line.separator") + "0.10" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_5.getBytes()));
        Scanner scanner_5 = new Scanner(System.in);
        assertEquals(false,Main.withdraw(scanner_5,atmtester));

        String test_6 = "c" + System.getProperty("line.separator") + "0.10" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_6.getBytes()));
        Scanner scanner_6 = new Scanner(System.in);
        assertEquals(false,Main.withdraw(scanner_6,atmtester));

        String test_7 = "10" + System.getProperty("line.separator") + "-1" + System.getProperty("line.separator");
        System.setIn(new ByteArrayInputStream(test_7.getBytes()));
        Scanner scanner_7 = new Scanner(System.in);
        assertEquals(false,Main.withdraw(scanner_7,atmtester));

    }
}
