package INF3132.items.subclasses;

import INF3132.items.Stats;

public class PotionFactory {
    private String name;
    private int itemPower;
    private Stats affectedStat;

    public PotionFactory(
        String name,
        int itemPower,
        Stats affectedStat
    ) {
        this.name = name;
        this.itemPower = itemPower;
        this.affectedStat = affectedStat;
    }

    public Potion create() {
        return new Potion(
            name,
            itemPower,
            affectedStat
        );
    }
}
