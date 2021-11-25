package ATM;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Set up ATM
        ATM atm = new ATM();
        atm.setupMoney(5000, 200);
        String pathname = "CardDatabase.txt";
        atm.setDatabase(pathname);

        labelA:
        while (true) {
            String cardNum = null;

            Scanner scan = new Scanner(System.in);
            System.out.println("Welcome to the XYZ Bank ATM!\nPlease enter your bank card number:");

            try {
                cardNum = scan.next();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }

            // Check card number
            try {
                if (!atm.checkCardValid(cardNum)) {
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("This card number is invalid.\n");
                continue;
            }

            // Check Status
            if (!atm.checkConfiscate()) {
                continue;
            }


            // Check pin 3 times
                for (int i = 0; true; i ++){
                    int num = 3 - i;
                    System.out.println("Enter Pin (" + num + " more chances):");
                    String inputPin = scan.next();    
                try {
                    if (atm.checkPin(inputPin)) {
                        break;
                    }
                } catch (NumberFormatException e) {
                }
                    // If run out all chance
                    if (num == 1) {
                        System.out.println("You have ran out all chances. Your card will be locked.");
                        continue labelA;
                    }
                }

            // Option while loop

            labelB:
            while (true) {
                System.out.println("Please select operation(Deposit,Withdraw,CheckBalance,Quit):");
                String operation = scan.next();

                try {
                    // deposit
                    switch (operation.toUpperCase()) {

                        case "DEPOSIT":
                        case "D": {
                            if (!deposit(scan, atm)) {
                                continue labelB;
                            }
                            break;
                        }

                        // withdraw
                        case "WITHDRAW":
                        case "W": {
                            if (!withdraw(scan, atm)) {
                                continue labelB;
                            }
                            break;
                        }

                        // Check Balance
                        case "CHECKBALANCE":
                        case "B":
                            atm.checkBalance();
                            break;

                        // Quit
                        case "QUIT":
                        case "Q":
                            System.out.println("Thank you for using the ATM service.\n");
                            break labelB;

                        default:
                            System.out.println("Please enter a correct operation.");
                    }

                } catch (Exception e) {
                    System.out.println("Please select your operation again, pay attention to correct input!\r\n");
                }
            }

            // Update to Atm card list
            atm.updateToCardList();
            // Update to txt data base
            atm.txtUpdater(pathname);
            // ATM Check
            atm.maintenance();
        }
    }

    public static Boolean deposit(Scanner scan, ATM atm) {
        System.out.println("Please enter the amount you want to deposit(Only notes are accepted):");
        String a = scan.next();


        if (a.toUpperCase().equals("CANCEL") || a.toUpperCase().equals("C")) {
            return false;
        }

        double note = Double.parseDouble(a);

        try {
            if (note % 1 != 0) {
                System.out.println("The ATM can only deposit notes.");
                return false;
            }
            if (atm.deposit(note)) {
                Receipt receipt = new Receipt(atm.getReceiptAccount(), note, "DEPOSIT", atm.getCurrentCard().getBalance());
                atm.setReceiptAccount(atm.getReceiptAccount() + 1);
                receipt.PrintReceipt();
                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Please enter a numeral input.\r\n");
            return false;
        }

    }

    public static Boolean withdraw(Scanner scan, ATM atm) {
        System.out.println("Please enter the amount of notes you want to withdraw.");
        String Note = scan.next();

        if (Note.toUpperCase().equals("CANCEL") || Note.toUpperCase().equals("C")) {
            return false;
        }
        
        if (Double.parseDouble(Note) % 1 != 0) {
            System.out.println("Please enter a whole number.");
            return false;
        }

        System.out.println("Please enter the amont of coins you want to withdraw(E.g. 0.5 for 50 cents).");
        String Coin = scan.next();
        
        if (Double.parseDouble(Coin) > 1) {
            System.out.println("Please enter a number smaller than one.");
            return false;
        }
        
        if (Coin.toUpperCase().equals("CANCEL") || Coin.toUpperCase().equals("C")) {
            return false;
        }

        try {
            double note = Double.parseDouble(Note);
            double coin = Double.parseDouble(Coin);
            double sum = note + coin;


            if (atm.withdraw(note, coin)) {

                Receipt receipt = new Receipt(atm.getReceiptAccount(), sum, "WITHDRAW", atm.getCurrentCard().getBalance());
                atm.setReceiptAccount(atm.getReceiptAccount() + 1);
                receipt.PrintReceipt();
                return true;

            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Please enter a numeral input.\r\n");
            return false;
        }
    }
}



