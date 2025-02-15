package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.combat.Combat;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class WaterMonster extends Monster {
    private float flood;
    private float fall;

    public WaterMonster(
        String name,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks,
        float flood,
        float fall
    ) {
        super(name, MonsterType.WATER, hpMax, attack, defense, speed, attacks);
        this.flood = flood;
        this.fall = fall;
        this.setWeakType(AttackType.ELECTRIC);
        this.setStrongType(AttackType.FIRE);
    }

    @Override
    public void afterAttack(float inflictedDamage) {
        this.afterAttack(inflictedDamage, null);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack a) {
        super.afterAttack(inflictedDamage, a);

        if (a == null || a.getType() != AttackType.WATER) return;

        // 1/4 odd of flooding the terrain
        if ((float)Math.random() <= flood) {
            Combat.getCurrentCombat().getTerrain().flood(this);
        }
    }

    public float getFlood() {
        return flood;
    }

    public float getFall() {
        return fall;
    }
}
