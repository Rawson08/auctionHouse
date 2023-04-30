import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
import java.util.Scanner;

/**
 * represents an auction house that contains a list of Items to be bid on
 *  and bids made on the items
 */
public class AuctionHouse {

    private String accountNumber;

    public List<Item> getItems() {
        return items;
    }

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
    public void connectToBank(String hostname, int port) throws IOException {
        Socket clientSocket = new Socket(hostname, port);
        System.out.println("AuctionHouse client: " + clientSocket);
        BufferedReader in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Send a test message to the Bank server
        out.println("CREATE_ACCOUNT");
        System.out.println("sent message");

        // Receive response from the Bank server
        String response = in.readLine();
        this.accountNumber = response;
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
        Scanner systemIn = new Scanner(System.in);
        System.out.println("enter bank hostname:");
        String hostname = systemIn.nextLine();
        System.out.println("enter bank port: ");
        int port = systemIn.nextInt();
        AuctionHouse a = new AuctionHouse();
        a.connectToBank(hostname, port);
    }
}
