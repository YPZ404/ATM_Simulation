package ATM;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Card {
    private int CardNumber;
    private int CardPin;
    private String CardStatus;
    private double Balance;
    private String BeginDate;
    private String EndDate;

    public Card(int CardNumber, int CardPin, String CardStatus, double Balance, String BeginDate, String EndDate) {
        this.CardNumber = CardNumber;
        this.CardPin = CardPin;
        this.CardStatus = CardStatus;
        this.Balance = Balance;
        this.BeginDate = BeginDate;
        this.EndDate = EndDate;
    }

    public int getCardNumber() {
        return this.CardNumber;
    }

    public int getCardPin() {
        return this.CardPin;
    }

    public String getCardStatus() {
        return this.CardStatus;
    }

    public double getBalance() {
        return this.Balance;
    }

    public String getBeginDate(){
        return this.BeginDate;
    }

    public String getEndDate() {
        return this.EndDate;
    }

    /*
     * If card is expired
     */
    public Boolean InDate() {
        SimpleDateFormat format = new SimpleDateFormat("MM/yy");
        Date begin = null;
        Date end = null;
        Date Today = null;
        try {
            begin = format.parse(BeginDate);
            end = format.parse(EndDate);
            Today = new Date();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (begin.before(Today) && end.after(Today)) {
            return true;
        }

        return false;
    }

    public void setBalance(double balance){
        this.Balance = balance;
    }

}
