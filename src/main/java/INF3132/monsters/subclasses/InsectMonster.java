package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class InsectMonster extends Monster {

    public InsectMonster(String name, int hpMax, int attack, int defense, int speed, List<Attack> attacks) {
        super(name, MonsterType.INSECT, hpMax, attack, defense, speed, attacks);
    }

    @Override  
    public void doAfterAttack(){
        // TODO Implémenter la mécanique de régénération
    }
}
