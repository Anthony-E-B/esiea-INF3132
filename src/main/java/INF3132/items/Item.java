package INF3132.items;

public abstract class Item {
    private String name;
    private int quantity;

    public Item(
        String name,
        int quantity
    ) {
        this.name = name;
        this.quantity = quantity;
    }

    public abstract int use();

    // GETTERS & SETTERS

    // Name, getter only
    public String getName() {
        return name;
    }

    // Quantity, protected set
    public int getQuantity() {
        return quantity;
    }

    protected void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
