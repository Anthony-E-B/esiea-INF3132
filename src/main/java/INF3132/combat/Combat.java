package INF3132.combat;

import INF3132.combat.move.CombatMove;
import INF3132.combat.terrain.Terrain;
import INF3132.events.EventPublisher;
import INF3132.trainer.Trainer;
import INF3132.monsters.Monster;

import java.util.ArrayList;
import java.util.List;

public class Combat {
    private int currentTurn;
    private Trainer currentTrainer;
    private Trainer opponent;

    private Terrain terrain;

    public EventPublisher<Integer> turnChanged;

    private static Combat currentCombat = null;

    public Trainer t1;
    public Trainer t2;

    private List<CombatMove> trainersMoves;

    public Combat(Trainer t1, Trainer t2) {
        turnChanged =  new EventPublisher<Integer>();
        trainersMoves = new ArrayList<>();
        currentTurn = 0;

        this.t1 = t1;
        this.t2 = t2;

        this.terrain = new Terrain(this);
    }

    private boolean oppositeTrainerCanFight() {
        List<Monster> oppositeTrainerTeam = opponent.getTeam();

        if (oppositeTrainerTeam.size() == 0) return false;

        for (Monster m : oppositeTrainerTeam) {
            if (m.getHp() > 0) return true;
        }

        return false;
    }

    public void onMoveSelected(CombatMove move) {
        trainersMoves.add(move);
    }

    public void start() {
        t1.giveUp.addListener(ve -> onGiveUp(t1));
        t2.giveUp.addListener(ve -> onGiveUp(t2));

        t1.turnEnded.addListener(this::onMoveSelected);
        t2.turnEnded.addListener(this::onMoveSelected);

        sendMessage(String.format(
            "%s et %s veulent se battre !",
            t1.getName(),
            t2.getName()
        ));

        currentTrainer = Math.random() > .5 ? t1 : t2;
        opponent = currentTrainer == t1 ? t2 : t1;

        // When this int reaches 2, it means everybody chose their move :
        // Execute the moves in order of priority, and reset to 0.
        int movesSelected = 0;

        while (true) {
            // Checking if the opposite trainer has Monster able to fight left.
            if (!oppositeTrainerCanFight()) {
                sendMessage("L'adversaire n'a plus de monstres en état de combattre !");
                setWinner(currentTrainer);
                break; // Combat ended - Exit out of the loop.
            }

            // Swap trainer
            currentTrainer = opponent;
            opponent = currentTrainer == t1 ? t2 : t1;

            // Play next turn
            currentTrainer.playTurn();
            movesSelected++;

            // Every player chose their move : play them in order of priority, and reset.
            if (movesSelected == 2) {
                currentTurn++;
                turnChanged.notifyListeners(currentTurn);
                movesSelected = 0;

                // Sort moves by priority
                trainersMoves.sort((a, b) -> a.getPriority() < b.getPriority() ? -1 : 1);
                trainersMoves.forEach(move -> move.execute());

                // Execute all moves in order
                for (CombatMove move : trainersMoves)
                    move.execute();

                for (CombatMove move : trainersMoves)
                    trainersMoves.remove(move);
            }
        }
    }

    /**
     * Handle one of the player giving up.
     * @param trainerGivingUp The trainer giving up combat.
     */
    public void onGiveUp(Trainer trainerGivingUp) {
        sendMessage(String.format("%s abandonne !", trainerGivingUp.getName()));
        if (trainerGivingUp == t1) setWinner(t2);
        else setWinner(t1);
    }

    /**
     * Defines the winner and ends combat.
     * @param t The trainer who has won the combat.
     */
    public void setWinner(Trainer t) {
        sendMessage(String.format("%s gagne le combat!", t.getName()), 4000);
        Combat.currentCombat = null;
        // TODO: exit ?
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }

    public void sendMessage(String message) {
        this.sendMessage(message, 1500);
    }

    /**
     * Show a status message for the fight.
     * @param message The message to show.
     * @param ttl The minimum time to live of the message.
     */
    public void sendMessage(String message, int ttl) {
        for (int i = 0; i < message.length(); i++) {
            System.out.print(message.charAt(i));
            System.out.flush();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println();

        try {
            Thread.sleep(Math.max(0, ttl - message.length() * 10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Combat initCombat(Trainer t1, Trainer t2) {
        Combat.currentCombat = new Combat(t1, t2);
        return Combat.currentCombat;
    }

    // Getters / Setters
    public static Combat getCurrentCombat() {
        return Combat.currentCombat;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public Trainer getCurrentTrainer() {
        return this.currentTrainer;
    }

    public Trainer getOpponent() {
        return this.opponent;
    }
}
