package INF3132.monsters.subclasses;

import java.util.List;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackType;
import INF3132.combat.Combat;
import INF3132.combat.negativestatus.Poison;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterType;

public class InsectMonster extends NatureMonster {

    public InsectMonster(
        String name,
        int hpMax,
        int attack,
        int defense,
        int speed,
        List<Attack> attacks
    ) {
        super(name, MonsterType.INSECT, hpMax, attack, defense, speed, attacks);
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
        Monster opponent = c.getOpponent().getCurrentFightingMonster();

        if (opponent.getNegativeStatus() == null){
            Poison poisonStatus = new Poison(opponent, c);
            boolean newNegativeStatusSet = opponent.trySetNegativeStatus(poisonStatus);

            if (newNegativeStatusSet) {
                c.sendMessage(String.format(
                    "%s est empoisonné !", opponent.getName()
                ));
            }
        }
    }
}
