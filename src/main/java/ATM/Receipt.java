package ATM;

import java.text.DecimalFormat;

public class Receipt {
    private int TranNumber;
    private double ReceiptAmount;
    private String OperationType;
    private double Balance;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public Receipt(int TranNumber, double ReceiptAmount, String OperationType, double Balance) {
        this.TranNumber = TranNumber;
        this.ReceiptAmount = ReceiptAmount;
        this.OperationType = OperationType;
        this.Balance = Balance;
    }

    public double getBalance() {
        return Balance;
    }

    public int getTranNumber() {
        return TranNumber;
    }

    public String getOperationType() {
        return OperationType;
    }
    
    public double getReceiptAmount() {
        return ReceiptAmount;
    }

    /*
     * Print the information
     */
    public void PrintReceipt(){
        System.out.println("Receipt Track Number: " + this.getTranNumber() +
                "\r\n Transaction Option: " + this.getOperationType() +
                "\r\n Transaction Amount: " + this.getReceiptAmount() +
                "\r\n Transaction Amount: " + this.getTranNumber() +
                "\n Remaining Balance: " + df2.format(this.getBalance()));
    }
}