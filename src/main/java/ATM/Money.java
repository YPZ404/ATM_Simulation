package ATM;

public class Money {

    private String type;
    private double amount;

    public Money(String type,double Amount) {
        this.type = type;
        this.amount = Amount;
    }

    public String getType(){
        return type;
    }

    public double getAmount(){
        return amount;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }
}
