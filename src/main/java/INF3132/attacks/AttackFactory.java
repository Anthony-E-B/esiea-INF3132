package INF3132.attacks;

import INF3132.monsters.MonsterType;

public class AttackFactory {
    private String name;
    private int power;
    private final float failRate;
    private final AttackType type;
    private int nbUseMax;

    public AttackFactory(
        String name,
        int power,
        int nbUseMax,
        float failRate,
        AttackType type
    ) {
        this.name = name;
        this.power = power;
        this.failRate = failRate;
        this.type = type;
        this.nbUseMax = nbUseMax;
    }

    public Attack create() {
        return new Attack(name, type, power, nbUseMax, failRate);
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

    public AttackType getType() {
        return type;
    }

    public int getNbUseMax() {
        return nbUseMax;
    }

    public void setNbUseMax(int nbUseMax) {
        this.nbUseMax = nbUseMax;
    }
}

