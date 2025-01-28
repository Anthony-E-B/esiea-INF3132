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
import INF3132.trainer.exception.TeamFullException;
import INF3132.ui.Menu;
import INF3132.ui.MenuItem;


public class Trainer {
    public static final int TEAM_MAX_SIZE = 6;

    public EventPublisher<VoidEvent> giveUp;
    public EventPublisher<VoidEvent> turnEnded;

    private List<Monster> team = new ArrayList<Monster>();
    private Bag bag;
    private Monster currentFightingMonster = null;

    private String name;

	public Trainer(String name) {
        this.name = name;
        this.giveUp = new EventPublisher<VoidEvent>();
        this.turnEnded = new EventPublisher<VoidEvent>();
    }

    public void playTurn() {
        // "Swap monster" menu
        List<MenuItem> monstersMenuItem = new ArrayList<>();
        team.forEach(monster -> monstersMenuItem.add(new MenuItem(monster.getName())));
        Menu monstersMenu = new Menu("Monstres", monstersMenuItem.toArray(MenuItem[]::new));

        // "Attack" menu
        List<MenuItem> attacksMenuItems = new ArrayList<>();
        currentFightingMonster.getAttacks().forEach(attack ->
            attacksMenuItems.add(new MenuItem(attack.getName(), () -> {
                orderMonsterToAttack(
                    currentFightingMonster,
                    Combat.getCurrentCombat().getOpponent().getCurrentFightingMonster(),
                    attack
                );
            }))
        );
        Menu attacksMenu = new Menu("Attaques", attacksMenuItems.toArray(MenuItem[]::new));

        // "Items" menu
        List<MenuItem> itemsMenuItems = new ArrayList<>();
        bag.getItems().forEach(item ->
            itemsMenuItems.add(new MenuItem(item.getName(), () -> {
                try {
                    useConsumable((Consumable) item, currentFightingMonster);
                } catch (UnownedItemException e) {
                    System.out.println("Vous ne possédez pas cet objet.");
                } catch (UnusableItemException e) {
                    System.out.println("Cet objet ne peut pas être utilisé.");
                }
            }))
        );
        Menu itemsMenu = new Menu("Objets", itemsMenuItems.toArray(MenuItem[]::new));


        MenuItem[] turnMenuItems = {
            new MenuItem(
                "Charger",
                () -> {
                    orderMonsterToAttack(
                        currentFightingMonster,
                        Combat.getCurrentCombat().getOpponent().getCurrentFightingMonster()
                    );
                }
            ),
            new MenuItem("Attaques spé.", attacksMenu),
            new MenuItem("Objets", itemsMenu),
            new MenuItem("Changer de monstre", monstersMenu),
        };
        Menu turnMenu = new Menu("Tour de " + getName(), turnMenuItems);

        // IMPORTANT: Set all menu's parent to the main turn menu
        monstersMenu.setParent(turnMenu);
        attacksMenu.setParent(turnMenu);

    }

    protected void endTurn() {
        turnEnded.notifyListeners(new VoidEvent());
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
    public void orderMonsterToAttack(Monster source, Monster target) {
        try {
            source.attack(target);
        } catch (AttackFailedException e) {
            Combat.getCurrentCombat().sendMessage(
                String.format(
                    "Mince, on dirait que la charge executée par %s de %s a échoué !",
                    source.getName(),
                    this.getName()
                )
            );
        }
    }

    /**
     * Order a Monster to attack with {@link Attack} {@param a}
     * @param source The {@link Monster} to command.
     * @param target The {@link Monster} to attack.
     * @param a The {@link Attack} to use.
     */
    public void orderMonsterToAttack(Monster source, Monster target, Attack a) {
        try {
            source.attack(target, a);
        } catch (AttackFailedException e) {
            Combat.getCurrentCombat().sendMessage(
                String.format(
                    "Mince, on dirait que l'attaque %s lancée par %s de %s a échoué !",
                    a.getName(),
                    source.getName(),
                    this.getName()
                )
            );
        }
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

    public Monster getCurrentFightingMonster() {
        return currentFightingMonster;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

}

