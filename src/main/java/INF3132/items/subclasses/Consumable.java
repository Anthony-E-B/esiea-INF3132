package INF3132.items.subclasses;

import INF3132.items.Item;
import INF3132.items.exception.UnusableItemException;
import INF3132.monsters.Monster;

abstract public class Consumable extends Item {
    boolean isUsed;

    public Consumable(String name){
        super(name);
        this.isUsed = false;
    }

    /**
     * Uses a consumable Item on a Monster. 
     * @param m The monster that we want to use the item on. 
     */
    public abstract int use(Monster m) throws UnusableItemException;

    /**
     * Checks if an Item can be used on a Monster.
     * @param m The monster that is being evaluated with the item.
     */
    public abstract boolean checkIfUsable(Monster m);

    public boolean isUsed() {
        return isUsed;
    }

    protected void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
