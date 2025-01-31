package INF3132.combat.move;

import INF3132.combat.Combat;
import INF3132.items.exception.UnusableItemException;
import INF3132.items.subclasses.Consumable;
import INF3132.monsters.Monster;
import INF3132.trainer.Trainer;

public class UseConsumableMove implements CombatMove {

    private Trainer trainer;
    private Consumable consumable;
    private Monster monster;

    public UseConsumableMove(Trainer trainer, Consumable consumable, Monster monster) {
        this.trainer = trainer;
        this.consumable = consumable;
        this.monster = monster;
    }

	@Override
	public int getPriority() {
        return 2;
	}

	@Override
	public void execute() {
        try {
            consumable.use(monster);
            Combat.getCurrentCombat().sendMessage(String.format("%s utilise %s sur %s !", trainer.getName(), consumable.getName(), monster.getName()));
        } catch (UnusableItemException e) {
            Combat.getCurrentCombat().sendMessage(String.format("%s n'a eu aucun effet sur %s...", consumable.getName(), monster.getName()));
        }

        trainer.getBag().removeItem(consumable);
	}

    @Override
    public Monster getTarget() {
        return monster;
    }
}
