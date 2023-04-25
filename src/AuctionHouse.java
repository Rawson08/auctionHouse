import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
/**
 * represents an auction house that contains a list of Items to be bid on
 *  and bids made on the items
 */
public class AuctionHouse {

    //will hold a list of items up for auction
    private List<Item> items;
    //list of bids in auction house
    private List<Bid> bids;

    //tests
    private final String BANK_IP = "localhost";
    private final int BANK_PORT = 6060;


    public AuctionHouse() {
        this.items = new ArrayList<>();
        this.bids = new ArrayList<>();
    }

    // Connect to Bank server
    public void connectToBank() throws IOException {
        Socket socket = new Socket(BANK_IP, BANK_PORT);
        ObjectInputStream in =new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        // Send a test message to the Bank server
        out.writeChars("Hello from the Auction House");

        // Receive response from the Bank server
        String response = in.readLine();
        System.out.println("Received message from Bank server: " + response);

        // Close the socket connection
        socket.close();
    }

    // Add an item to the auction house
    public void addItem(Item item) {
        this.items.add(item);
    }

    // Place a bid on an item in the auction house
    public void placeBid(Item item, Bid bid) {
        // TODO: Implement the logic for placing a bid
    }
}
