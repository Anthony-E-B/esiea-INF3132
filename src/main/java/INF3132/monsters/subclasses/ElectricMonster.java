package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.combat.Combat;
import INF3132.combat.negativestatus.Paralysis;
import INF3132.monsters.FloodAffectedMonster;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class ElectricMonster extends Monster implements FloodAffectedMonster {
    float paralysis;

    public ElectricMonster(
        String name,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks,
        float paralysis
    ) {
        super(name, MonsterType.ELECTRIC, hpMax, attack, defense, speed, attacks);
        this.paralysis = paralysis;
        this.setWeakType(AttackType.GROUND);
        this.setStrongType(AttackType.WATER);
    }

    @Override
    public void afterAttack(float inflictedDamage) {
        this.afterAttack(inflictedDamage, null);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack a) {
        super.afterAttack(inflictedDamage, a);

        if (a == null || a.getType() != AttackType.ELECTRIC) return;

        Combat c = Combat.getCurrentCombat();
        Monster m = c.getCurrentMove().getTarget();

        if (m.getNegativeStatus() == null) {

            if ((float)Math.random() <= paralysis) {

                Paralysis p = new Paralysis(m, c);
                boolean newNegativeStatusSet = m.trySetNegativeStatus(p);

                if (newNegativeStatusSet) {
                    c.sendMessage(String.format(
                        "%s est paralysé !", m.getName()
                    ));
                }
            }
        }
    }
}
