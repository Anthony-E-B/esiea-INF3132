package INF3132.trainer;

import java.util.ArrayList;
import java.util.List;

import INF3132.combat.Combat;
import INF3132.combat.move.AttackMove;
import INF3132.combat.move.CombatMove;
import INF3132.combat.move.SwapMonsterMove;
import INF3132.combat.move.UseConsumableMove;
import INF3132.events.EventPublisher;
import INF3132.events.VoidEvent;
import INF3132.items.exception.UnownedItemException;
import INF3132.items.subclasses.Consumable;
import INF3132.monsters.Monster;
import INF3132.monsters.exception.MonsterUnableToFightException;
import INF3132.monsters.exception.UnownedMonsterException;
import INF3132.trainer.exception.InvalidTeamSetException;
import INF3132.trainer.exception.TeamFullException;
import INF3132.ui.Menu;
import INF3132.ui.MenuItem;


public class Trainer {
    public static final int TEAM_MAX_SIZE = 6;

    public EventPublisher<VoidEvent> giveUp;
    public EventPublisher<CombatMove> turnEnded;

    private List<Monster> team = new ArrayList<Monster>();
    private Bag bag;
    private Monster currentFightingMonster = null;

    private String name;

	public Trainer(String name) {
        this.name = name;
        this.giveUp = new EventPublisher<VoidEvent>();
        this.turnEnded = new EventPublisher<>();
    }

    public void playTurn() {
        Combat combat = Combat.getCurrentCombat();
        Trainer opponent = combat.getOpponent();

        // Current monster is KO : swap monster or
        if (currentFightingMonster.getHp() <= 0) {
            if (canFight()) {
                Combat.getCurrentCombat().sendMessage(String.format("%s a un monstre KO et doit changer de monstre.", getName()));
                forcedMonsterSwap();
            } else {
                Combat.getCurrentCombat().sendMessage(String.format("%s n'a plus de monstre en état de combattre !", getName()));
                giveUp();
                return;
            }
        }

        System.out.println();
        combat.sendMessage(
            String.format(
                "%s joue avec %s contre le %s de %s.",
                getName(),
                currentFightingMonster.getSummary(),
                opponent.getCurrentFightingMonster().getSummary(),
                opponent.getName()
            )
        );

        // "Swap monster" menu
        List<MenuItem> monstersMenuItem = new ArrayList<>();
        team.forEach(monster -> monstersMenuItem.add(
            new MenuItem(
                (monster == currentFightingMonster ? "(ACTUEL) " : "") + monster.getSummary(),
                () -> {
                    endTurn(new SwapMonsterMove(this, monster));
                }
            )
        ));
        Menu monstersMenu = new Menu("Monstres", monstersMenuItem.toArray(MenuItem[]::new));

        // "Attack" menu
        Menu attacksMenu = new Menu("Attaques");
        List<MenuItem> attacksMenuItems = new ArrayList<>();
        currentFightingMonster.getAttacks().forEach(attack -> {
                attacksMenuItems.add(new MenuItem(
                    String.format("%s (%d/%d)", attack.getName(), attack.getNbUse(), attack.getNbUseMax()),
                    () -> {
                        if (attack.getNbUse() > 0) {
                            endTurn(new AttackMove(this, currentFightingMonster, attack));
                        } else {
                            combat.sendMessage(String.format("L'attaque %s n'est plus disponible !", attack.getName()));
                            attacksMenu.prompt();
                        }
                    })
                );
        });
        attacksMenu.setItems(attacksMenuItems.toArray(MenuItem[]::new));

        // "Items" menu
        List<MenuItem> itemsMenuItems = new ArrayList<>();
        Menu itemsMenu = new Menu("Objets");
        bag.getItems().forEach(item ->
            itemsMenuItems.add(new MenuItem(item.getName(), () -> {
                try {
                    promptConsumable((Consumable) item, itemsMenu);
                } catch (UnownedItemException e) {
                    System.out.println("Vous ne possédez pas cet objet.");
                }
            }))
        );
        itemsMenu.setItems(itemsMenuItems.toArray(MenuItem[]::new));


        MenuItem[] turnMenuItems;
        if (currentFightingMonster.getHp() > 0) {
            turnMenuItems = new MenuItem[]{
                new MenuItem(
                    "Charger",
                    () -> {
                        endTurn(new AttackMove(this, currentFightingMonster));
                    }
                ),
                new MenuItem("Attaques spé.", attacksMenu),
                new MenuItem("Objets", itemsMenu),
                new MenuItem("Changer de monstre", monstersMenu),
            };
        } else {
            // If monster is Knocked Out
            turnMenuItems = new MenuItem[]{
                new MenuItem("Changer de monstre", monstersMenu)
            };
        }
        Menu turnMenu = new Menu("Tour de " + getName(), turnMenuItems);

        // IMPORTANT: Set all menu's parent to the main turn menu, to be able to go back to it if needed.
        monstersMenu.setParent(turnMenu);
        attacksMenu.setParent(turnMenu);
        itemsMenu.setParent(turnMenu);

        turnMenu.prompt();
    }

    /**
     * Checks if this trainer has monsters left to fight.
     * @return {@code true} if they can fight, {@code false} otherwise.
     */
    public boolean canFight() {
        for (Monster m : team) {
            if (m.getHp() >= 0) {
                return true;
            }
        }

        return false;
    }

    protected void endTurn(CombatMove move) {
        turnEnded.notifyListeners(move);
    }

    public void promptConsumable(Consumable i, Menu backMenu) throws UnownedItemException {
        if (!bag.itemIsOwn(i)) throw new UnownedItemException();

        List<MenuItem> monstersMenuItem = new ArrayList<>();
        team.forEach(monster -> monstersMenuItem.add(
            new MenuItem((monster == currentFightingMonster ? "(ACTUEL) " : "") + monster.getSummary(), () -> {
                endTurn(new UseConsumableMove(this, i, monster));
            }))
        );
        Menu monstersMenu = new Menu("Utiliser sur quel monstre ?", monstersMenuItem.toArray(MenuItem[]::new), backMenu);

        monstersMenu.prompt();
    }

    /**
     * Swap the current fighting monster to {@param m}.
     * To be used in the context of a strategic move and not a forced swap.
     * @param m The monster to make fighting in place of the current monster.
     */
    public void swapCurrentFightingMonster(Monster m) throws UnownedMonsterException, MonsterUnableToFightException {
        if (!monsterIsOwned(m)) throw new UnownedMonsterException();
        if (m.getHp() <= 0) throw new MonsterUnableToFightException();

        currentFightingMonster = m;
    }

    /**
     * Swap the current monster.
     * Use this when the current fighting monster is unable to fight
     * and you need to switch to one that's OK.
     */
    public void forcedMonsterSwap() {
        List<Monster> availableMonsters = new ArrayList<>();
        for (Monster m : team) {
            if (m.getHp() > 0 && m != currentFightingMonster) availableMonsters.add(m);
        }

        List<MenuItem> monstersMenuItems = new ArrayList<>();
        availableMonsters.forEach(monster -> monstersMenuItems.add(
            new MenuItem(monster.getSummary(), () -> {
                try {
                    swapCurrentFightingMonster(monster);
                    Combat.getCurrentCombat().sendMessage(String.format(
                        "%s envoie %s sur le terrain.",
                        getName(),
                        monster.getName()
                    ));
                } catch (MonsterUnableToFightException | UnownedMonsterException e) {
                    e.printStackTrace();
                }
            })
        ));
        Menu monstersMenu = new Menu("Choisissez un nouveau monstre", monstersMenuItems.toArray(MenuItem[]::new));

        monstersMenu.prompt();
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
            if (team.size() == 1) {
                currentFightingMonster = m;
            }
            return;
        }

        throw new TeamFullException();
    }

    /**
     * @implNote This method checks the size of the team but not the type rules.
     */
    public void setTeam(List<Monster> newTeam) throws InvalidTeamSetException {
        if (newTeam.size() > 3 || newTeam.size() < 1) {
            throw new InvalidTeamSetException();
        }

        this.team = newTeam;
        currentFightingMonster = team.get(0);
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

    public Bag getBag() {
        return this.bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }
}

