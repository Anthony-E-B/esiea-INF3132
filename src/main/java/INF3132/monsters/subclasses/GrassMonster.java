package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.monsters.FloodAffectedMonster;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class GrassMonster extends Monster implements FloodAffectedMonster {

    public GrassMonster(
        String name,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        super(name, MonsterType.GRASS, hpMax, attack, defense, speed, attacks);
        this.setWeakType(AttackType.FIRE);
        this.setStrongType(AttackType.GROUND);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack a) {
        super.afterAttack(inflictedDamage, a);
        // TODO Implémenter la mécanique de régénération
    }
}
