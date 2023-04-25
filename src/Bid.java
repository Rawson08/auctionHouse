/**
 * represents a bid for the auction
 * @param bidder the Agent that is bidding
 * @param item the item being bid on
 * @param amount the bid amount
 */
 record Bid(Agent bidder, Item item, double amount) {

    @Override
    public String toString() {
        return String.format("%s bid $%.2f on %s", bidder.getName(), amount, item.name());
    }
}

