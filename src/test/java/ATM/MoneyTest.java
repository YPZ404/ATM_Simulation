package ATM;

import ATM.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {

    @Test
    public void initializeTest() {
        Money money = new Money("Note", 500);
        assertEquals(500, money.getAmount());
        assertEquals("Note", money.getType());
    }

    @Test
    public void getTypeTest() {
        Money coin = new Money("Coin", 500);
        assertEquals("Coin", coin.getType());
        Money note = new Money("Note", 500);
        assertEquals("Note", note.getType());
    }

    @Test
    public void getAmountTest() {
        Money large = new Money("Note", 114514.19);
        Money middle = new Money("Note", 1150.75);
        Money small = new Money("Note", 333.25);
        assertEquals(114514.19, large.getAmount());
        assertEquals(1150.75, middle.getAmount());
        assertEquals(333.25, small.getAmount());
    }

    @Test
    public void setAmountTest() {
        Money money = new Money("Note", 100);
        assertEquals(100, money.getAmount());
        money.setAmount(500);
        assertEquals(500, money.getAmount());
    }

}
