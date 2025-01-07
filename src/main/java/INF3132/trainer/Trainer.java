package INF3132.trainer;

import java.util.List;
import java.util.ArrayList;

import INF3132.monsters.Monster;
import INF3132.monsters.exception.UnownedMonsterException;
import INF3132.monsters.exception.MonsterUnableToFightException;
import INF3132.attacks.Attack;
import INF3132.attacks.exception.AttackFailedException;
import INF3132.combat.Combat;
import INF3132.events.EventPublisher;
import INF3132.events.VoidEvent;
import INF3132.items.exception.UnownedItemException;
import INF3132.items.exception.UnusableItemException;
import INF3132.items.subclasses.Consumable;
import INF3132.trainer.exception.NoCurrentCombat;
import INF3132.trainer.exception.TeamFullException;

public class Trainer {
    public static final int TEAM_MAX_SIZE = 6;

    public EventPublisher<VoidEvent> giveUp;

    private List<Monster> team = new ArrayList<Monster>();
    private Bag bag;
    private Monster currentFightingMonster = null;

    private String name;

	public Trainer(String name) {
        this.name = name;
    }

    /**
     * Use a consumable on a monster
     */
    public void useConsumable(Consumable i, Monster m) throws UnownedItemException, UnusableItemException {
        if (!bag.itemIsOwn(i)) throw new UnownedItemException();

        i.use(m);
    }

    /**
     * Order a Monster to attack with its base attack.
     * @param source The {@link Monster} to command.
     * @param target The {@link Monster} to attack.
     */
    public void orderMonsterToAttack(Monster source, Monster target) throws AttackFailedException {
        source.attack(target);
    }

    /**
     * Order a Monster to attack with {@link Attack} {@param a}
     * @param source The {@link Monster} to command.
     * @param target The {@link Monster} to attack.
     * @param a The {@link Attack} to use.
     */
    public void orderMonsterToAttack(Monster source, Monster target, Attack a) throws AttackFailedException {
        source.attack(target, a);
    }

    /**
     * Swap the current fighting monster to {@param m}
     * @param m The monster to make fighting in place of the current monster.
     */
    public void swapPokemon(Monster m) throws UnownedMonsterException, MonsterUnableToFightException {
        if (!monsterIsOwned(m)) throw new UnownedMonsterException();
        if (m.getHp() <= 0) throw new MonsterUnableToFightException();

        currentFightingMonster = m;
    }

    public void giveUp() {
        giveUp.notifyListeners(new VoidEvent());
    }

    /**
     * Check if monster {@param m} is owned by this {@link Trainer}
     * @param m The monster to check ownership of.
     * @return {@code true} if it is, {@code false} otherwise.
     */
    public boolean monsterIsOwned(Monster m) {
        return team.contains(m);
    }

    /**
     * Adds a monster to the team.
     * @param m The monster to add to this trainer's team.
     * @throws TeamFullException if the team already contains the maximum amount of Monsters.
     */
    public void addToTeam(Monster m) throws TeamFullException {
        if (team.size() < TEAM_MAX_SIZE) {
            team.add(m);
            return;
        }

        throw new TeamFullException();
    }

    public List<Monster> getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

