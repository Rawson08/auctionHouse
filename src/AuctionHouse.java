import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * represents an auction house that contains a list of Items to be bid on
 *  and bids made on the items
 */
public class AuctionHouse {

    private int port;
    //will hold a list of items up for auction
    private List<Item> items;
    //list of bids in auction house
    private List<Bid> bids;

    public AuctionHouse(int port) {
        this.port = port;
        this.items = new ArrayList<>();
        //TODO: decide if we will track all bids or if we will just need to keep the highest bid
        this.bids = new ArrayList<>();
    }

    /**
     * meant to handle network communication
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Auction house is running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(new AuctionHouseWorker(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class AuctionHouseWorker implements Runnable {

        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public AuctionHouseWorker(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.in = new ObjectInputStream(clientSocket.getInputStream());
                this.out = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
//            try {
//                // TODO: handle client requests
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }
}
