package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.combat.Combat;
import INF3132.combat.negativestatus.Burned;
import INF3132.monsters.FloodAffectedMonster;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class FireMonster extends Monster implements FloodAffectedMonster {

    public FireMonster(String name, int hpMax, int attack, int defense, int speed, List<Attack> attacks) {
        super(name, MonsterType.FIRE, hpMax, attack, defense, speed, attacks);
        this.setWeakType(AttackType.WATER);
        this.setStrongType(AttackType.NATURE);
    }

    @Override
    public void afterAttack(float inflictedDamage) {
        this.afterAttack(inflictedDamage, null);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack a) {
        super.afterAttack(inflictedDamage, a);

        if (a == null || a.getType() != AttackType.FIRE) return;

        Combat c = Combat.getCurrentCombat();
        Monster m = c.getOpponent().getCurrentFightingMonster();

        if (m.getNegativeStatus() == null) {
            Burned b = new Burned(m, c);
            boolean newNegativeStatusSet = m.trySetNegativeStatus(b);

            if (newNegativeStatusSet) {
                c.sendMessage(String.format(
                    "%s est brûlé !", m.getName()
                ));
            }
        }
    }
}
