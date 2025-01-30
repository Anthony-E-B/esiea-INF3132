package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.combat.Combat;
import INF3132.monsters.FloodAffectedMonster;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class GroundMonster extends Monster implements FloodAffectedMonster {
    private boolean underground;
    private int digDuration;

    public GroundMonster(
        String name,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        super(name, MonsterType.GROUND, hpMax, attack, defense, speed, attacks);
    }

    @Override
    public void afterAttack(float inflictedDamage) {
        this.afterAttack(inflictedDamage, null);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack a) {
        super.afterAttack(inflictedDamage, a);
        Combat combat = Combat.getCurrentCombat();
        digDuration--;
        if(digDuration <= 0 && underground && a.getType() != AttackType.GROUND){
            combat.sendMessage(String.format(
                        "%s sort de sous terre !", this.getName()
            ));
            this.improveDefense(-this.getDefense()/2);
            underground = false;
        }

        if (a == null || a.getType() != AttackType.GROUND) return;
        if (!underground){
            combat.sendMessage(String.format(
                        "%s se cache sous terre !", this.getName()
            ));
            this.improveDefense(this.getDefense());
        }
        this.underground = true;
        this.digDuration = Math.max(digDuration, (int)(Math.random()*3)+1);
    }
}
