package INF3132.monsters;

import INF3132.attacks.Attack;
import INF3132.attacks.exception.AttackFailedException;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class Monster {
    private String name;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int speed;
    private final List<Attack> attacks;
    private final Set<Status> status;
    private MonsterType type;

    public Monster(
        String name,
        MonsterType type,
        int maxHp,
        int attack,
        int defense,
        int speed
    ) {
        this(name, type, maxHp, attack, defense, speed, new ArrayList<Attack>());
    }

    public Monster(
        String name,
        MonsterType type,
        int maxHp,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        this(name, type, maxHp, attack, defense, speed, attacks, new HashSet<Status>());
    }

    public Monster(
        String name,
        MonsterType type,
        int maxHp,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks,
        Set<Status> status
    ) {
        this.name = name;
        this.type = type;
        this.maxHp = maxHp;
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.attacks = attacks;
        this.status = status;
    }

    /**
     * Handle attacks of different types.
     * Takes into account the type of the current Monster instance and the type
     * of the attack
     *
     * @param a The attack to take damage from
     */
    public abstract void receiveAttack(Attack a);

    public abstract void attack(Monster target, Attack a) throws AttackFailedException;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    // Attack : protected set
    public int getAttack() {
        return attack;
    }

    protected void setAttack(int attack) {
        this.attack = attack;
    }

    // Defense : protected set
    public int getDefense() {
        return defense;
    }

    protected void setDefense(int defense) {
        this.defense = defense;
    }

    // Speed : protected set
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    // Attacks
    public List<Attack> getAttacks() {
        return attacks;
    }

    // Status
    public Set<Status> getStatus() {
        return status;
    }

    // Type
    public MonsterType getType() {
        return type;
    }
}
