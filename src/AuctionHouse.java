import java.io.*;
import java.net.InetAddress;
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
    private String bankIP = "localhost";
    private int bankPort = 6061;


    public AuctionHouse() {
        this.items = new ArrayList<>();
        this.bids = new ArrayList<>();
    }

    // Connect to Bank server
    public void connectToBank() throws IOException {
        Socket clientSocket = new Socket(InetAddress.getLocalHost(), bankPort);
        System.out.println("AuctionHouse client: " + clientSocket);
        BufferedReader in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

        // Send a test message to the Bank server
        out.println("Test message from AuctionHouse");
        out.flush();
        System.out.println("sent message");

        // Receive response from the Bank server
        String response = in.readLine();
        System.out.println("Received message from Bank server: " + response);

        // Close the socket connection
        clientSocket.close();
    }

    // Add an item to the auction house
    public void addItem(Item item) {
        this.items.add(item);
    }

    // Place a bid on an item in the auction house
    public void placeBid(Item item, Bid bid) {
        // TODO: Implement the logic for placing a bid
    }

    public static void main(String[] args) throws IOException {
        AuctionHouse a = new AuctionHouse();
        a.connectToBank();
    }
}
