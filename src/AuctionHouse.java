import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.net.Socket;
import java.util.Scanner;

/**
 * represents an auction house that contains a list of Items to be bid on
 *  and bids made on the items
 */
public class AuctionHouse implements Runnable {
    private String accountNumber;
    private static int auctionPort = 8000;

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
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public AuctionHouse() {
        this.items = new ArrayList<>();
        this.bids = new ArrayList<>();
        this.auctionPort = ++auctionPort;
    }

    // Connect to Bank server
    public void connectToBank(String hostname, int port) throws IOException {
        clientSocket = new Socket(hostname, port);
        System.out.println("AuctionHouse client: " + clientSocket);
        in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Send a new account request to the Bank server
        out.println("CREATE_AUCTION");
        // Receive response from the Bank server
        String response = in.readLine();
        this.accountNumber = response;
        out.println(clientSocket.getInetAddress());
        response = in.readLine();
        System.out.println("Received message from Bank server: " + response);
    }
    public void closeConnection() throws IOException {
        // Close the BufferedReader, PrintWriter, and Socket objects
        in.close();
        out.close();
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
        a.run();
    }

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(auctionPort, 50, InetAddress.getLocalHost())) {
            System.out.println("Auction server socket created: " + serverSocket.toString());
            Boolean running = true;
            while (running) {
                Socket clientSocket = serverSocket.accept();
                //Thread thread = new Thread(new Bank.BankWorker(clientSocket));
                //thread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
