import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Agent {
    private double currentBalance;
    private String accountNumber;
    private Map<String, List<Integer>> auctionHouses;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public Agent() {
        this.currentBalance = currentBalance;
        this.auctionHouses = new HashMap<>();
    }

    public static void main(String[] args) throws IOException {
        Agent a = new Agent();
        Scanner systemIn = new Scanner(System.in);
        System.out.println("enter bank hostname:");
        String hostname = systemIn.nextLine();
        System.out.println("enter bank port: ");
        int port = systemIn.nextInt();
        a.connectToBank(hostname, port);
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

    public double getBalance() {
        return currentBalance;
    }

    public String getAccountName() {
        return accountNumber;
    }

    public Map<String, List<Integer>> getAuctionHouses() {
        return auctionHouses;
    }

    public void setBalance(double balance) {
        this.currentBalance = balance;
    }

    public void connectToBank(String hostname, int port) throws IOException {
        clientSocket = new Socket(hostname, port);
        in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);

        // Send a new account request to the Bank server
        out.println("CREATE_ACCOUNT");

        // Receive response from the Bank server
        String response = in.readLine();
        this.accountNumber = response;
        System.out.println("Received message from Bank server: " + response);

        //get list of available auction houses from bank
        out.println("GET_AUCTIONS");
        String auctionsString;

        //receive and parse list of available auctions
        auctionsString = in.readLine();
        auctionsString = auctionsString.replace("[", "").replace("]", "");
        String[] pairs = auctionsString.split(", ");
        for (String pair : pairs) {
            // Split the pair into its key and value components
            String[] components = pair.split(": ");
            String key = components[0];
            Integer value = Integer.parseInt(components[1]);
            // Check if the key already exists in the map
            if (auctionHouses.containsKey(key)) {
                // If the key exists, add the value to its corresponding List
                auctionHouses.get(key).add(value);
            } else {
                // If the key does not exist, create a new List and add the value
                List<Integer> values = new ArrayList<>();
                values.add(value);
                auctionHouses.put(key, values);
            }
        }
        System.out.println("auctions available: " + auctionHouses);
        out.println("END");
    }
}

