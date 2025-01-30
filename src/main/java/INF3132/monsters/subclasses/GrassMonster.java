package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.combat.Combat;
import INF3132.combat.terrain.Terrain;
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
    }

    @Override
    public void startTurn() {
        Combat c = Combat.getCurrentCombat();
        Terrain t = c.getTerrain();
        if (t.isFlooded()){
            c.sendMessage(String.format(
                "%s se regénère grâce au terrain inondé !",
                this.getName()
            ));
            this.restoreHealth(this.getMaxHp()/20);
        }
    }

    @Override
    public void afterAttack(float inflictedDamage){
        this.afterAttack(inflictedDamage, null);
    }

    @Override
    public void afterAttack(float inflictedDamage, Attack a) {
        super.afterAttack(inflictedDamage, a);
        if (a == null || a.getType() != AttackType.NATURE) return;
        Combat c = Combat.getCurrentCombat();
        Boolean isTFlooded = c.getTerrain().isFlooded();
        if (isTFlooded && this.getNegativeStatus() != null) {
            int roll = (int)(Math.random() * 5) +1;
            if(roll==1){
                c.sendMessage(String.format(
                        "%s se soigne du statut %s !", this.getName(), this.getNegativeStatus()
                ));
                this.disposeNegativeStatus(this.getNegativeStatus());
            }
        }
    }
}
