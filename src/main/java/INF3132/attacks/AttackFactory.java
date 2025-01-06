package INF3132.attacks;

import INF3132.monsters.MonsterType;

public class AttackFactory {
    private String name;
    private int power;
    private final float failRate;
    private final MonsterType type;
    private int nbUseMax;

    public AttackFactory(
        String name,
        int power,
        int nbUseMax,
        float failRate,
        MonsterType type
    ) {
        this.name = name;
        this.power = power;
        this.failRate = failRate;
        this.type = type;
        this.nbUseMax = nbUseMax;
    }

    public Attack create() {
        return new Attack(name, type, power, 0, failRate);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public float getFailRate() {
        return failRate;
    }

    public MonsterType getType() {
        return type;
    }

    public int getNbUseMax() {
        return nbUseMax;
    }

    public void setNbUseMax(int nbUseMax) {
        this.nbUseMax = nbUseMax;
    }
}

