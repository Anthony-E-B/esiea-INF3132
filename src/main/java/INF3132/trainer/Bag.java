package INF3132.trainer;

import java.util.ArrayList;
import java.util.List;

import INF3132.items.Item;

public class Bag {
    private List<Item> items = new ArrayList<Item>();

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean itemIsOwn(Item i) {
        return items.contains(i);
    }

    public List<Item> getItems() {
        return items;
    }

    private void setItems(List<Item> items) {
        this.items = items;
    }

    public void showItems() {
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }
}
