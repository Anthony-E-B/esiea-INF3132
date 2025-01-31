package INF3132.attacks;

import INF3132.monsters.MonsterType;

public class Attack {
    private String name;

    private int power;
    private int nbUse;
    private int nbUseMax;

	private final float failRate;

    private final AttackType type;

    public Attack(
        String name,
        AttackType type,
        int power,
        int nbUse,
        float fail
    ) {
        this.name = name;
        this.type = type;
        this.power = power;
        this.nbUseMax = nbUse;
        this.nbUse = nbUse;
        this.failRate = fail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AttackType getType() {
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

    public int getNbUseMax() {
		return nbUseMax;
	}

    public void useOnce() {
        nbUse--;
    }
}
