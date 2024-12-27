package INF3132.items.subclasses;

import INF3132.items.Stats;

public enum PotionType {
    POTION("Potion", 20, Stats.HP),
    SUPER_POTION("Super Potion", 50, Stats.HP),
    HYPER_POTION("Hyper Potion", 200, Stats.HP),
    MAX_POTION("Max Potion", 999, Stats.HP);

    private final String name;
    private final int power;
    private final Stats stat;

    PotionType(String name, int power, Stats stat) {
        this.name = name;
        this.power = power;
        this.stat = stat;
    }

    public Potion toPotion() {
        return new Potion(name, power, stat);
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public Stats getStat() {
        return stat;
    }
}
