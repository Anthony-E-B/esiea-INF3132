package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.combat.Combat;
import INF3132.monsters.MonsterType;

public class GrassMonster extends NatureMonster {

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
    public void afterAttack(float inflictedDamage){
        this.afterAttack(inflictedDamage, null);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack attack) {
        super.afterAttack(inflictedDamage, attack);

        if (attack == null || attack.getType() != AttackType.NATURE) return;

        Combat c = Combat.getCurrentCombat();
        boolean isTFlooded = c.getTerrain().isFlooded();

        if (isTFlooded && getNegativeStatus() != null) {
            boolean shouldHeal = Math.random() < .2; // 1/5 odd

            if (shouldHeal) {
                c.sendMessage(String.format(
                    "%s se soigne du statut %s !", getName(), getNegativeStatus()
                ));

                disposeNegativeStatus(getNegativeStatus());
            }
        }
    }
}
