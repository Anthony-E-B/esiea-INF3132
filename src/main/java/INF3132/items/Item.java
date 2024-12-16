package INF3132.items;

public abstract class Item {
    protected String name;

    public Item(
        String name
    ) {
        this.name = name;
    }

    // GETTERS & SETTERS
    // Name, getter only
    public String getName() {
        return name;
    }
}
