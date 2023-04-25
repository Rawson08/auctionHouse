/**
 * represents an item to go up for bid
 * @param name is the name of the Item to bid on
 * @param description has a description of the item
 * @param startingPrice is the minimum bid amount to start
 */
public record Item(String name, String description, double startingPrice) {
//TODO: determine how these items will be generated
    /**
     * @return String with name, description, and starting price
     */
    @Override
    public String toString() {
        return String.format("%s: %s ($%.2f)", name, description, startingPrice);
    }
}

