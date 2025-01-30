package INF3132.combat.move;

import INF3132.attacks.Attack;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.attacks.exception.SlippedAndFailedException;
import INF3132.combat.Combat;
import INF3132.monsters.Monster;
import INF3132.trainer.Trainer;

/**
 * Move to order a Monster to attack with a specific {@link Attack} or bare-handed.
 */
public class AttackMove implements CombatMove {
    private Trainer trainer;
    private Monster source;
    private Monster target;
    private Attack attack;

    public AttackMove(Trainer t, Monster source, Monster target) {
        this(t, source, target, null);
    }

    public AttackMove(Trainer t, Monster source, Monster target, Attack a) {
        this.trainer = t;
        this.source = source;
        this.target = target;
        this.attack = a;
    }

	@Override
	public int getPriority() {
        return 1; // TODO
	}

	@Override
	public void execute() {
        Combat combat = Combat.getCurrentCombat();

        if (attack != null) attack();
        else charge();
	}

    private void charge() {
        Combat combat = Combat.getCurrentCombat();
        combat.sendMessage(String.format("%s charge %s !", source.getName(), target.getName()));

        try {
            int inflictedDamage = source.attack(target);
            Combat.getCurrentCombat().sendMessage(String.format("%s inflige %d de dommages à %s.", source.getName(), inflictedDamage, target.getName()));
        } catch (SlippedAndFailedException e) {
            Combat.getCurrentCombat().sendMessage(
                String.format(
                    "%s de %s glisse à cause du terrain inondé et rate sa charge !",
                    source.getName(),
                    trainer.getName()
                )
            );
        } catch (AttackFailedException e) {
            Combat.getCurrentCombat().sendMessage(
                String.format(
                    "Mince, on dirait que la charge executée par %s de %s a échoué !",
                    source.getName(),
                    trainer.getName()
                )
            );
        }
    }

    private void attack() {
        Combat combat = Combat.getCurrentCombat();
        combat.sendMessage(String.format("%s attaque %s avec %s !", source.getName(), target.getName(), attack.getName()));

        try {
            int inflictedDamage = source.attack(target, attack);
            Combat.getCurrentCombat().sendMessage(String.format("%s inflige %d de dommages à %s.", source.getName(), inflictedDamage, target.getName()));
        } catch (SlippedAndFailedException e) {
            Combat.getCurrentCombat().sendMessage(
                String.format(
                    "%s de %s glisse à cause du terrain inondé et rate son attaque !",
                    source.getName(),
                    trainer.getName()
                )
            );
        } catch (AttackFailedException e) {
            Combat.getCurrentCombat().sendMessage(
                String.format(
                    "Mince, l'attaque lancée par %s de %s a échoué !",
                    source.getName(),
                    trainer.getName()
                )
            );
        }
    }
}
