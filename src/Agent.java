import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class Agent implements Runnable{
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String username;

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    private double totalBalance;
    private double availableBalance;
    private String accountNumber;
    private Map<String, List<Integer>> auctionHouses;

    private Socket clientSocket;
    private int auctionSelected;
    private Socket auctionSocket;
    private BufferedReader in;
    private PrintWriter out;

    public Agent() {
        this.username = username;
        this.totalBalance = totalBalance;
        this.auctionHouses = new HashMap<>();
    }

    public static void main(String[] args) throws IOException {
//        Agent a = new Agent();
//        Scanner systemIn = new Scanner(System.in);
//        System.out.println("enter bank hostname:");
//        String hostname = systemIn.nextLine();
//        System.out.println("enter bank port: ");
//        int port = systemIn.nextInt();
//        a.connectToBank(hostname, port);
//        a.connectToAuctionHouse();
    }

    public void connectToAuctionHouse() throws IOException {
        // Connect to the given auction house and add it to the list of connected auction houses
        Scanner sysin = new Scanner(System.in);
        if(!auctionHouses.isEmpty()) {
            System.out.println("Which auction would you like to connect to?");
            auctionSelected = sysin.nextInt();
        }
        else System.out.println("there are no Auctions available");

//        while (clientSocket.isConnected()){
//            Object ob = in.read();
//            if (ob instanceof ClientAddress client){
//                clientSocket = new Socket(client.getipAdress(), client.getPortNumber());
//                System.out.println("Connected to Auction House" + (auctionSelected) + ".");
//                ItemWatcher itemWatcher = new ItemWatcher(auctionSocket, auctionSelected, Integer.parseInt(accountNumber));
//                auctionSelected++;
//                auctionHouse.put(itemWatcher.getItemNumber(), itemWatcher);
//                (new Thread(itemWatcher)).start();
//            }
//            else {
//                setTotalBalance((double) ob);
//            }
//        }
    }

    public void disconnectFromAuctionHouse(AuctionHouse auctionHouse) {
        // Disconnect from the given auction house and remove it from the list of connected auction houses
    }

    public void bidOnItem(AuctionHouse auctionHouse, Item item, double amount) {
        // Place a bid on the given item at the given auction house
    }

    public String getAccountName() {
        return accountNumber;
    }

    public Map<String, List<Integer>> getAuctionHouses() {
        return auctionHouses;
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
        System.out.println(response);

        //get list of available auction houses from bank
        out.println("GET_AUCTIONS");
        String auctionsString;

        //receive and parse list of available auctions
        auctionsString = in.readLine();
        auctionsString = auctionsString.replace("[", "").replace("]", "");
        String[] pairs = auctionsString.split(", ");
        for (String pair : pairs) {
            // Split the pair into its hostName and value components
            String[] components = pair.split(": ");
            String hostName = components[0];
            Integer value = Integer.parseInt(components[1]);
            // Check if the hostName already exists in the map
            if (auctionHouses.containsKey(hostName)) {
                // If the hostName exists, add the value to its corresponding List
                auctionHouses.get(hostName).add(value);
            } else {
                // If the hostName does not exist, create a new List and add the value
                List<Integer> auctionHousePortNumber = new ArrayList<>();
                auctionHousePortNumber.add(value);
                auctionHouses.put(hostName, auctionHousePortNumber);
            }
        }
        System.out.println("auctions available: ");
        printAuctions();
        out.println("END");
    }

    public void printAuctions(){
        int i = 1;
        for (Map.Entry<String, List<Integer>> entry : auctionHouses.entrySet()) {
            String key = entry.getKey();
            List<Integer> values = entry.getValue();
            for (Integer value : values) {
                System.out.println(i + ". location: " + key + " port: " + value);
                i++;
            }
        }
    }

    @Override
    public void run() {
        Agent a = new Agent();
        Scanner systemIn = new Scanner(System.in);
        System.out.println("enter bank hostname:");
        String hostname = systemIn.nextLine();
        System.out.println("enter bank port: ");
        int port = systemIn.nextInt();
        try {
            a.connectToBank(hostname, port);
            a.connectToAuctionHouse();
        } catch (SocketException | EOFException e){
            System.out.println("Connection Closed");
            System.exit(0);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //TODO: Modify the worker class for Agent (Make each for bank and auctionHouse)
    public class AgentWorker implements Runnable {
        public AgentWorker (Socket clientSocket) {
        }

        @Override
        public void run() {
            String messageIn;
            int bid = 0;
            try {
                // read the incoming message from the client
                messageIn = in.readLine();
                while(!messageIn.equals("END")) {
                    // process the message and send a response
                    System.out.println("the message to auctionHouse: " + messageIn);
                    switch (messageIn) {
                        case "PLACE_BID" -> {
                            out.println("How much would you like to bid?");
                        }
                    }
                    messageIn = in.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
