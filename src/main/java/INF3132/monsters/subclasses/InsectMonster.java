package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.combat.Combat;
import INF3132.combat.negativestatus.Poison;
import INF3132.combat.terrain.Terrain;
import INF3132.monsters.FloodAffectedMonster;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class InsectMonster extends Monster implements FloodAffectedMonster {

    public InsectMonster(
        String name,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        super(name, MonsterType.INSECT, hpMax, attack, defense, speed, attacks);
    }

    @Override
    public void startTurn() {
        Combat c = Combat.getCurrentCombat();
        Terrain t = c.getTerrain();
        if(t.isFlooded()){
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
        Monster m = c.getOpponent().getCurrentFightingMonster();
        if (m.getNegativeStatus() == null){
            Poison p = new Poison(m, c);
            m.setNegativeStatus(p);
            c.sendMessage(String.format(
                    "%s est empoisonné !", m.getName()
            ));
        }
    }
}
