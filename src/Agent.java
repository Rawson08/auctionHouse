import java.io.*;
import java.net.Socket;
import java.util.*;

public class Agent {
    private double currentBalance;
    private String accountNumber;
    private Map<String, List<Integer>> auctionHouses;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Agent() {
        this.currentBalance = currentBalance;
        this.auctionHouses = new HashMap<>();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Agent a = new Agent();
        Scanner systemIn = new Scanner(System.in);
        System.out.println("enter bank hostname:");
        String hostname = systemIn.nextLine();
        System.out.println("enter bank port: ");
        int port = systemIn.nextInt();
        a.connectToBank(hostname, port);
        a.connectToAuctionHouse();
    }

    public void connectToAuctionHouse() throws IOException, ClassNotFoundException {
        // Connect to the given auction house and add it to the list of connected auction houses
        Scanner sysin = new Scanner(System.in);
        if(!auctionHouses.isEmpty()) {
            System.out.println("Which auction would you like to connect to?");
            int auctionSelected = sysin.nextInt();
            // connect to the auction house as long as the bank is connected
            while(clientSocket.isConnected()){
                Object object = in.readObject();
                if (object instanceof ClientAddress clientAddress){
                    Socket auctionConnection = new Socket(clientAddress.getipAdress(), clientAddress.getPortNumber());
                    System.out.println("Connected to auction house: " + auctionSelected);
                    System.out.println("\n");

                }
                else{
                    setBalance((double) object);
                }
            }



        }
        else System.out.println("there are no Auctions available");
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

    public void connectToBank(String hostname, int port) throws IOException, ClassNotFoundException {
        clientSocket = new Socket(hostname, port);
        in =new ObjectInputStream(clientSocket.getInputStream());
        out = new ObjectOutputStream(clientSocket.getOutputStream());

        // Send a new account request to the Bank server
        out.writeObject("CREATE_ACCOUNT");

        // Receive response from the Bank server
        String response = (String) in.readObject();
        this.accountNumber = response;
        System.out.println(response);

        //get list of available auction houses from bank
        out.writeObject("GET_AUCTIONS");
        String auctionsString;

        //receive and parse list of available auctions
        auctionsString = (String) in.readObject();
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
        out.writeObject("END");
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

    //TODO: Modify the worker class for Agent (Make each for bank and auctionHouse)
    //For Bank
    public class AgentWorkerForBank implements Runnable {
        public AgentWorkerForBank (Socket clientSocket) {
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
                            System.out.println("How much would you like to bid?");
                        }
                    }
                    messageIn = in.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class AgentWorkerForAH implements Runnable {
        public AgentWorkerForAH (Socket clientSocket) {
        }

        @Override
        public void run() {
            String messageIn;
            int bid = 0;
            try {
                // read the incoming message from the client
                messageIn = (String)in.readObject();
                while(!messageIn.equals("END")) {
                    // process the message and send a response
                    System.out.println("the message to auctionHouse: " + messageIn);
                    switch (messageIn) {
                        case "PLACE_BID" -> {
                            out.writeObject("How much would you like to bid?");
                        }
                    }
                    messageIn = (String) in.readObject();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

