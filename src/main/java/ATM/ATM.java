package ATM;

import java.util.*;
import java.io.*;

public class ATM {

    private Double availableFund;
    private Boolean sufficientFund;
    private Money[] moneyList;
    private Card currentCard;
    private ArrayList<Card> cardDatabase;
    private int receiptAccount;

    public ATM() {
        this.availableFund = 0.0;
        this.sufficientFund = true;
        this.currentCard = null;
        this.moneyList = new Money[2];
        this.cardDatabase= new ArrayList<Card>();
        this.receiptAccount = 1000;
    }

    /* Ziyi
     * Setup is for initializing ATM function:
        1.Create two Money classes, add them to this.moneyList
        2.Get the sum of two Money classes, assign to this.AvailableFund
     */
    public void setupMoney(double Note, double Coin){
        Money N = new Money("Note", Note);
        Money C = new Money("Coin", Coin);
        moneyList[0] = N;
        moneyList[1] = C;
        availableFund += Note + Coin;
        periodicCheck();
    }

    /*
     * Write cardInfo into Card class, then store into cardDatabase
     */
    public void setDatabase(String pathname) {
        try{
            File filename = new File(pathname);
            FileInputStream filestream = new FileInputStream(filename);
            InputStreamReader streamReader = new InputStreamReader(filestream,"UTF-8");
            BufferedReader br = new BufferedReader(streamReader);
            String lineTxt = null;

            while ((lineTxt = br.readLine()) != null){
                if (lineTxt.equals("")){
                    continue;
                }else {
                    String[] cardInfo = lineTxt.split(",");

                    int cardNumber = Integer.parseInt(cardInfo[0]);
                    int cardPin = Integer.parseInt(cardInfo[1]);
                    String cardStatus = cardInfo[2];
                    double balance = Double.parseDouble(cardInfo[3]);
                    String BeginDate = cardInfo[4];
                    String end = cardInfo[5];

                    Card newCard = new Card(cardNumber,cardPin,cardStatus,balance,BeginDate,end);
                    this.cardDatabase.add(newCard);
                }
            }
        } catch (IOException e) {
            System.err.println("read errors :" + e);
        }
    }

    /* Jeff
     * Find cardNumber in cardDatabase
        * return true or false
        * if true, extract the information in database, then assign it to this.Card
     */
    public Boolean checkCardValid(String checkedNumber){
        int inputNumber = Integer.parseInt(checkedNumber);
        for (Card card: this.cardDatabase){
            if (card.getCardNumber() == inputNumber){
                this.currentCard = card;
                this.cardDatabase.remove(card);
                return true;
            }
        }
        System.out.println("This card number is invalid.\n");
        return false;
    }

    public void updateToCardList(){
        this.cardDatabase.add(this.currentCard);
        this.currentCard = null;
    }

    /* Zuoliang
     * operation based on the status (active, lost, invalid)
     * if exceptional, clear the currentCard
     */
    public Boolean checkConfiscate(){
       if (!this.currentCard.InDate()){
           System.out.println("This card cannot be used as its status is expired.");
       } else if(this.currentCard.getCardStatus().equals("invalid")){
           System.out.println("This card cannot be used as its status is invalid.");
       } else if(this.currentCard.getCardStatus().equals("lost")){
           System.out.println("This card cannot be used as its status is lost.");
       } else if(this.currentCard.getCardStatus().equals("active")){
           return true;
       }
       this.updateToCardList();
       return false;
    }

    /* Zuoliang
     * input the password and compare
     */
    public Boolean checkPin(String InputPin){
        int pin = Integer.parseInt(InputPin);
        if (this.currentCard.getCardPin() == pin){
            return true;
        }
        return false;
    }

    /* Rachel
     * Conditions:
        1. money insufficient
        2. deposit: money in card -, atm +
     */
    public Boolean deposit(Double note){
        if (note <= 0.00) {
            System.out.println("Invalid amount.");
            return false;
        } else {
            this.currentCard.setBalance(this.currentCard.getBalance() + note);
            this.moneyList[0].setAmount(this.moneyList[0].getAmount() + note);
            return true;
        }
    }

    /* Yupeng
     */
    public Boolean  withdraw(double note, double coin){
        // Check the parameters are valid.
        if(note == 0.00 && coin == 0.00) {
            System.out.println("Invalid amount.");
            return false;

        } else if(note < 0.00 || coin < 0.00) {
            System.out.println("Invalid amount.");
            return false;
        }

        Money wdrNote = new Money("note", note);
        Money wdrCoin = new Money("coin", coin);
        double totalWdr = note + coin;

        if(wdrNote.getAmount() > this.moneyList[0].getAmount()) {
            System.out.println("Sorry, there is no sufficient note in this ATM at the moment. " +
                    "Please re-enter the amount or comeback later.");
            return false;

        } else if(wdrCoin.getAmount() > this.moneyList[1].getAmount()) {
            System.out.println("Sorry, there is no sufficient coin in this ATM at the moment. " +
                    "Please re-enter the amount or comeback later.");
            return false;

        } else if(this.currentCard.getBalance() < totalWdr) {
            System.out.println("Sorry, there is no sufficient money in your account. " +
                    "Please re-enter the amount or comeback later.");
            return false;
        }

        double leftNote = this.moneyList[0].getAmount() - wdrNote.getAmount();
        double leftCoin = this.moneyList[1].getAmount() - wdrCoin.getAmount();
        double leftBalance = this.currentCard.getBalance() - totalWdr;
        this.moneyList[0].setAmount(leftNote);
        this.moneyList[1].setAmount(leftCoin);
        this.currentCard.setBalance(leftBalance);
        return true;
    }

    /* Yupeng
     */
    public double checkBalance(){
        System.out.println("Remaining Amount: " + this.currentCard.getBalance());
        return this.currentCard.getBalance();
    }

    /* Jeff
     * Update Card back to the database
     */
    public void txtUpdater(String pathname){
        try {
            // The path of .txt
            PrintWriter pw=new PrintWriter(pathname);
            int size = this.cardDatabase.size();
            // Info to write
            for (Card card :this.cardDatabase){
                pw.write(card.getCardNumber()+","+card.getCardPin()+","+card.getCardStatus()
                        +","+card.getBalance()+","+card.getBeginDate()+","+card.getEndDate()+"\r\n");
            }
            pw.flush();
            pw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *ATM
     */
    // check if funds in ATM is sufficient
    public void periodicCheck() {
        // minimum quantity: 1000$ notes, 100$ coins
        if (moneyList[0].getAmount() < 1000 || moneyList[1].getAmount() < 100) {
            sufficientFund = false;
            System.out.println("Cash insufficient, maintenance required.");
        }
    }
    // maintain the funds in ATM
    public void maintenance() {
        periodicCheck();
        if (!sufficientFund) {
            if (moneyList[0].getAmount() < 1000) {
                moneyList[0].setAmount(moneyList[0].getAmount() + 1000);
                setAvailableFund(getAvailableFund() + 1000);
            }
            if (moneyList[1].getAmount() < 100) {
                moneyList[1].setAmount(moneyList[1].getAmount() + 100);
                setAvailableFund(getAvailableFund() + 100);
            }
            sufficientFund = true;
            System.out.println("Maintenance succeeded.");
        }
    }

    /*
     * Getter and setter methods
     */
    public Boolean getSufficientFund() {
        return sufficientFund;
    }

    public ArrayList<Card> getCardList(){
        return this.cardDatabase;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public Double getAvailableFund() {
        return availableFund;
    }

    public Money[] getMoneyList() {
        return moneyList;
    }

    public void setAvailableFund(Double availableFund) {
        this.availableFund = availableFund;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public void setSufficientFund(Boolean sufficientFund) {
        this.sufficientFund = sufficientFund;
    }

    public int getReceiptAccount() {
        return receiptAccount;
    }

    public void setReceiptAccount(int a){
        this.receiptAccount = a;
    }
}
