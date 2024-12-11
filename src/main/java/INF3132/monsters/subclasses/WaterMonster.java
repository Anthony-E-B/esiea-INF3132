package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class WaterMonster extends Monster {
    double flood;
    double fall;

    public WaterMonster(String name, int hpMax, int attack, int defense, int speed, List<Attack> attacks, double flood, double fall) {
        super(name, MonsterType.WATER, hpMax, attack, defense, speed, attacks);
        this.flood = flood;
        this.fall = fall;
    }

    @Override  
    public void doAfterAttack(){
        // TODO Implémenter la mécanique de pluie 
    }
}
