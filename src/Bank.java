import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {

    // instance variables
    private Map<String, Account> accounts;

    //possible list for auctionHouses class
    //private List<AuctionHouse> auctionHouses;

    // constructor
    public Bank() {
        this.accounts = new HashMap<>();
        //TODO: implement auctionHouse class
        //this.auctionHouses = new ArrayList<>();
    }

    // methods
    public String createAccount(String name, double initialBalance) {
        // creates a new account and adds it to the accounts map
        Account account = new Account(name, initialBalance);
        accounts.put(name,account);
        // returns the account name
        return account.getName();
    }

    //TODO: this class will add an auction house to the bank
//    public void addAuctionHouse(AuctionHouse auctionHouse) {
//        // adds the auction house to the auctionHouses list
//        auctionHouses.add(auctionHouse);
//    }

    //getter for auctionHouses
//    public List<AuctionHouse> getAuctionHouses() {
//        // returns the list of auction houses
//        return auctionHouses;
//    }

    public boolean transfer(String fromAccountName, String toAccountName, double amount) throws InsufficientFundsException {
        // transfers the specified amount from one account to another
        accounts.get(toAccountName).deposit(accounts.get(fromAccountName).withdraw(amount));
        // returns true if successful, false otherwise
        return true;
    }

    public double getBalance(String accountName) {
        // returns the balance of the specified account
        return accounts.get(accountName).balance;
    }

    public double getAvailableBalance(String accountName) {
        // returns the available balance of the specified account
        return accounts.get(accountName).balance - accounts.get(accountName).blockedFunds;
    }

    public void blockFunds(String accountName, double amount) {
        // blocks the specified amount in the account
        accounts.get(accountName).blockedFunds += amount;
    }

    public void unblockFunds(String accountName, double amount) {
        // unblocks the specified amount in the account
        accounts.get(accountName).blockedFunds -= amount;
    }

    public Account getAccount(String accountName) {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            String name = entry.getKey();
            if (name.equals(accountName)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public class Account {
        private String name;
        private double balance;
        private double blockedFunds;


        public Account(String name, double balance) {
            this.name = name;
            this.balance = balance;
            this.blockedFunds = 0;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            balance += amount;
            System.out.println("deposited " + amount);
        }

        public double withdraw(double amount) throws InsufficientFundsException {
            if (amount > balance) {
                throw new InsufficientFundsException();
            }
            balance -= amount;
            System.out.println("withdrew " + amount);
            return amount;
        }

        public String getName() {
            return name;
        }

    }
    public class InsufficientFundsException extends Exception {
        public InsufficientFundsException() {
            super("Insufficient funds");
        }
    }
}

