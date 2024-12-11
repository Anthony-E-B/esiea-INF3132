package INF3132.attacks;

import INF3132.monsters.MonsterType;

public abstract class Attack {
    private String name;

    private int power;
    private int nbUse;

    private final float failRate;

    private final MonsterType type;

    public Attack(
        String name,
        MonsterType type,
        int power,
        int nbUse,
        float fail
    ) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.nbUse = nbUse;
        this.failRate = fail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MonsterType getType() {
        return type;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getNbUse() {
        return nbUse;
    }

    public void setNbUse(int nbUse) {
        this.nbUse = nbUse;
    }

    public float getFailRate() {
        return failRate;
    }
}
