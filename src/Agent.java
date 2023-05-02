import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Agent extends JFrame {

    private String name;
    private double balance;
    private String accountName;
    private Bank bank;
    private List<AuctionHouse> auctionHouses;

    private JLabel nameLabel, balanceLabel, auctionLabel;
    private JTextField bidField;
    private JButton bidButton;

    public Agent(String name, double balance, Bank bank) {
        this.name = name;
        this.balance = balance;
        this.bank = bank;
        this.auctionHouses = new ArrayList<>();
        this.accountName = bank.createAccount(balance);

        // Initialize GUI components
        initComponents();
    }

    private void initComponents() {
        // Create main window and set layout
        setTitle("Agent: " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        // Add name label
        nameLabel = new JLabel("Name: " + name);
        add(nameLabel);

        // Add balance label
        balanceLabel = new JLabel("Balance: $" + balance);
        add(balanceLabel);

        // Add auction label
        auctionLabel = new JLabel("Connected auction houses: ");
        add(auctionLabel);

        // Add bid field
        bidField = new JTextField();
        add(bidField);

        // Add bid button
        bidButton = new JButton("Place bid");
        bidButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement bid placing functionality
            }
        });
        add(bidButton);

        // Set window size and visibility
        setSize(400, 150);
        setVisible(true);
    }

    public void connectToAuctionHouse(AuctionHouse auctionHouse) {
        // Connect to the given auction house and add it to the list of connected auction houses
        auctionHouses.add(auctionHouse);
        auctionLabel.setText("Connected auction houses: " + auctionHouses.size());
    }

    public void disconnectFromAuctionHouse(AuctionHouse auctionHouse) {
        // Disconnect from the given auction house and remove it from the list of connected auction houses
        auctionHouses.remove(auctionHouse);
        auctionLabel.setText("Connected auction houses: " + auctionHouses.size());
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
        balanceLabel.setText("Balance: $" + balance);
    }

    // Other methods as needed
}
