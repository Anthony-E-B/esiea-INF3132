package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class NormalMonster extends Monster {

    public NormalMonster(String name, int hpMax, int attack, int defense, int speed, List<Attack> attacks) {
        super(name, MonsterType.NORMAL, hpMax, attack, defense, speed, attacks);
    }

    @Override  
    public void doAfterAttack(){
        // TODO Implémenter la mécanique de régénération
    }
}
