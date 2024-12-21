package INF3132.items.subclasses;

import INF3132.items.Stats;
import INF3132.items.exception.UnusableItemException;
import INF3132.monsters.Monster;

public class Potion extends Consumable {
    private int itemPower;
    private Stats statAffected;

    public Potion(String name, int itemPower, Stats statAffected) {
        super(name);
        this.itemPower = itemPower;
        this.statAffected = statAffected;
    }

    @Override
    public int use(Monster m) throws UnusableItemException {
        if(checkIfUsable(m)){
            this.setUsed(true);
            return this.itemPower;
        } else throw new UnusableItemException();
    }

    @Override
    public boolean checkIfUsable(Monster m) {
        // TODO Implémenter une vérification pour savoir si l'objet est utilisable en l'état ou non
        return true;
    }

    public Stats getStatAffected() {
        return this.statAffected;
    }

    public int getItemPower() {
        return this.itemPower;
    }
}
