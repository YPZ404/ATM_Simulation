package ATM;

import ATM.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class ATMTest {

    @Test
    public void setupTest() { // test setup() method in ATM
        ATM atm_tester = new ATM();
        atm_tester.setupMoney(5000, 200);
        // check moneyList info
        assertEquals(5000, atm_tester.getMoneyList()[0].getAmount());
        assertEquals("Note", atm_tester.getMoneyList()[0].getType());
        assertEquals(200, atm_tester.getMoneyList()[1].getAmount());
        assertEquals("Coin", atm_tester.getMoneyList()[1].getType());
        // check availableFund
        assertEquals(5200, atm_tester.getAvailableFund());
    }

    @Test
    public void testPeriodicCheck() {
        ATM atm_tester = new ATM();
        atm_tester.setupMoney(10, 5);
        atm_tester.setSufficientFund(true); // Note: 10, Coin: 5, sufficientFund: true
        atm_tester.periodicCheck(); // Note/Coin unchanged, sufficientFund: false
        // check sufficientFund
        assertFalse(atm_tester.getSufficientFund());
    }

    @Test
    public void testMaintenance() {
        ATM atm_tester = new ATM();
        atm_tester.setupMoney(10, 5);
        atm_tester.maintenance(); // Note: 10+1000, Coin: 5+100, sufficientFund: false->true
        // check moneyList info
        assertEquals(1010, atm_tester.getMoneyList()[0].getAmount());
        assertEquals("Note", atm_tester.getMoneyList()[0].getType());
        assertEquals(105, atm_tester.getMoneyList()[1].getAmount());
        assertEquals("Coin", atm_tester.getMoneyList()[1].getType());
        // check availableFund
        assertEquals(1115, atm_tester.getAvailableFund());
        // check sufficientFund
        assertTrue(atm_tester.getSufficientFund());
    }

    @Test
    public void testConfiscate() {
        Card card1 = new Card(47893, 123456, "inactive", 10.0,
                "08/18", "08/21");
        Card card2 = new Card(88888, 678901, "lost", 10.0,
                "11/20", "11/24");
        Card card3 = new Card(12345, 666666, "active", 243.19998,
                "11/20", "11/24");
        ATM atm_tester = new ATM();
        atm_tester.setCurrentCard(card1);
        assertFalse(atm_tester.checkConfiscate(), "false");
        atm_tester.setCurrentCard(card2);
        assertFalse(atm_tester.checkConfiscate(), "false");
        atm_tester.setCurrentCard(card3);
        assertTrue(atm_tester.checkConfiscate(), "true");
    }

    @Test
    public void testPin() {
        Card card3 = new Card(12345, 666666, "active", 243.19998,
                "11/20", "11/24");
        ATM atm_tester = new ATM();
        atm_tester.setCurrentCard(card3);
        String pin = "123456";
        assertFalse(atm_tester.checkPin(pin));
        String pin1 = "666666";
        assertTrue(atm_tester.checkPin(pin1));
    }

    @Test
    public void testWithdraw() {
        Card card = new Card(12345, 666666, "active", 50000.50,
                "11/20", "11/24");
        ATM atm_tester = new ATM();
        atm_tester.setupMoney(50000000, 200);
        atm_tester.setCurrentCard(card);

        double before = atm_tester.getCurrentCard().getBalance();
        atm_tester.withdraw(50.0, 0.5);
        assertEquals(before, atm_tester.getCurrentCard().getBalance() +50 + 0.5,
                "The balances are not equal.");

        before = atm_tester.getCurrentCard().getBalance();
        atm_tester.withdraw(500000,5000000);
        assertEquals(before, atm_tester.getCurrentCard().getBalance() ,
                "The balances are not equal.");

        atm_tester.withdraw(0,0);
        assertEquals(before, atm_tester.getCurrentCard().getBalance(),
                "Invalid withdraw amount.");

        atm_tester.withdraw(-400,-0.58);
        assertEquals(before, atm_tester.getCurrentCard().getBalance(),
                "Invalid withdraw amount.");

        atm_tester.withdraw(0, 500);
        assertEquals(before, atm_tester.getCurrentCard().getBalance(),
                "Sorry, there is no sufficient coin in this ATM at the moment." +
                        " Please re-enter the amount or comeback later.");

        atm_tester.withdraw(60000000, 500);
        assertEquals(before, atm_tester.getCurrentCard().getBalance(),
                "Sorry, there is no sufficient note in this ATM at the moment." +
                        " Please re-enter the amount or comeback later.");

        atm_tester.withdraw(100000,0.58);
        assertEquals(before, atm_tester.getCurrentCard().getBalance(),
                "Sorry, there is no sufficient money in your account." +
                        " Please re-enter the amount or comeback later.");
    }

    @Test
    public void testCheckBalance() {
        Card card = new Card(12345, 666666, "active", 50000.50,
                "11/20", "11/24");
        ATM atm_tester = new ATM();
        atm_tester.setCurrentCard(card);
        double before = atm_tester.getCurrentCard().getBalance();
        double after = atm_tester.checkBalance();
        assertEquals(before, after, "The balances are not equal.");
    }

    @Test
    public void testDeposit() {
        Card card = new Card(12345, 666666, "active", 243.19,
                "11/20", "11/24");
        ATM atm_tester = new ATM();
        atm_tester.setupMoney(5000, 200);
        atm_tester.setCurrentCard(card);
        atm_tester.deposit(40.0);
        assertEquals(283.19, card.getBalance());
        atm_tester.deposit(-60.80);
        assertEquals(283.19, card.getBalance(), "Invalid amount.");
    }

    @Test
    public void testUpdateToCardList() {
        ATM atm_tester = new ATM();
        Card card1 = new Card(47893, 123456, "inactive", 10.0,
                "08/18", "08/21");
        atm_tester.setCurrentCard(card1);
        atm_tester.updateToCardList();
        assertNull(atm_tester.getCurrentCard());
        MatcherAssert.assertThat(atm_tester.getCardList(), CoreMatchers.hasItems(card1));
    }

    @Test
    public void testGetCardList() {
        ATM atm_tester = new ATM();
        Card card1 = new Card(47893, 123456, "inactive", 10.0,
                "08/18", "08/21");
        atm_tester.setCurrentCard(card1);
        atm_tester.updateToCardList();
        assertEquals(card1.getCardNumber(), atm_tester.getCardList().get(0).getCardNumber());
    }

    @Test
    public void testGetCurrentCard() {
        ATM atm_tester = new ATM();
        Card card1 = new Card(47893, 123456, "inactive", 10.0,
                "08/18", "08/21");
        atm_tester.setCurrentCard(card1);
        assertEquals(card1.getCardNumber(), atm_tester.getCurrentCard().getCardNumber());
    }

    @Test
    public void testSetupMoney() {
        ATM atm_tester = new ATM();
        atm_tester.setupMoney(1000, 1000);
        assertEquals(2000, atm_tester.getAvailableFund());
    }

    @Test
    public void testGetAvailableFund() {
        ATM atm_tester = new ATM();
        atm_tester.setAvailableFund(1000.0);
        assertEquals(1000.0, atm_tester.getAvailableFund());
    }

    @Test
    public void testSetCurrentCard() {
        ATM atm_tester = new ATM();
        Card card1 = new Card(47893, 123456, "inactive", 10.0,
                "08/18", "08/21");
        atm_tester.setCurrentCard(card1);
        assertEquals(card1.getCardNumber(), atm_tester.getCurrentCard().getCardNumber());
    }

    @Test
    public void testSetSufficientFund() {
        ATM atm_tester = new ATM();
        atm_tester.setSufficientFund(true);
        assertTrue(atm_tester.getSufficientFund());
    }

    @Test
    public void testGetReceiptAccount() {
        ATM atm_tester = new ATM();
        assertEquals(1000, atm_tester.getReceiptAccount());
    }

    @Test
    public void testSetReceiptAccount() {
        ATM atm_tester = new ATM();
        atm_tester.setReceiptAccount(2000);
        assertEquals(2000, atm_tester.getReceiptAccount());
    }

    @Test
    public void testCheckCardValid(){
        ATM atm_tester = new ATM();
        Card card1 = new Card(47893, 123456, "inactive", 10.0,
                "08/18", "08/21");
        atm_tester.setDatabase("CardDatabase.txt");
        atm_tester.setCurrentCard(card1);
        assertTrue(atm_tester.checkCardValid("47893"));
        assertFalse(atm_tester.checkCardValid(("345789")));
    }

    @Test
    public void testTxtUpdater(){
        ATM atm_tester_1 = new ATM();
        atm_tester_1.setDatabase("CardDatabase.txt");
        atm_tester_1.txtUpdater("EnptytestDatabase.txt");

        ATM atm_tester_2= new ATM();
        atm_tester_2.setDatabase("EnptytestDatabase.txt");
        assertEquals(atm_tester_1.getCardList().get(0).getCardNumber(),atm_tester_2.getCardList().get(0).getCardNumber());
    }
}


