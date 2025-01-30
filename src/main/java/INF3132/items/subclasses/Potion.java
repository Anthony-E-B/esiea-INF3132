package INF3132.items.subclasses;

import INF3132.items.Stats;
import INF3132.items.exception.UnusableItemException;
import INF3132.monsters.Monster;

public class Potion extends Consumable {
    private int itemPower;
    private Stats affectedStat;

    public Potion(String name, int itemPower, Stats affectedStat) {
        super(name);
        this.itemPower = itemPower;
        this.affectedStat = affectedStat;
    }

    @Override
    public void use(Monster m) throws UnusableItemException {
        if(checkIfUsable(m)){
            setUsed(true);

            switch (getAffectedStat()) {
                case HP:
                m.restoreHealth(getItemPower());
                break;
                case ATTACK:
                m.improveAttack(getItemPower());
                break;
                case DEFENSE:
                m.improveDefense(getItemPower());
                break;
                case SPEED:
                m.improveSpeed(getItemPower());
                break;
            }
        } else {
            throw new UnusableItemException();
        }
    }

    @Override
    public boolean checkIfUsable(Monster m) {
        if (isUsed()) return false;

        switch (getAffectedStat()) {
            case HP:
            return m.getHp() < m.getMaxHp();
            // TODO: cap those
            case ATTACK:
            case DEFENSE:
            case SPEED:
            default:
            return true;
        }
    }

    public Stats getAffectedStat() {
        return this.affectedStat;
    }

    public int getItemPower() {
        return this.itemPower;
    }
}
