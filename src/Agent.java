import java.util.ArrayList;
import java.util.List;

public class Agent {

    private String name;
    private double balance;
    private String accountName;
    private Bank bank;
    private List<AuctionHouse> auctionHouses;

    public Agent(String name, double balance, Bank bank) {
        this.name = name;
        this.balance = balance;
        this.bank = bank;
        this.auctionHouses = new ArrayList<>();
        this.accountName = bank.createAccount(name, balance);
    }

    public void connectToAuctionHouse(AuctionHouse auctionHouse) {
        // Connect to the given auction house and add it to the list of connected auction houses
    }

    public void disconnectFromAuctionHouse(AuctionHouse auctionHouse) {
        // Disconnect from the given auction house and remove it from the list of connected auction houses
    }

    public void bidOnItem(AuctionHouse auctionHouse, Item item, double amount) {
        // Place a bid on the given item at the given auction house
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountName() {
        return accountName;
    }

    public List<AuctionHouse> getAuctionHouses() {
        return auctionHouses;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Other methods as needed
}

