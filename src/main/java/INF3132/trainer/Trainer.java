package INF3132.trainer;

import java.util.List;
import java.util.ArrayList;

import INF3132.items.subclasses.PotionType;
import INF3132.monsters.Monster;

public class Trainer {
    public static final int TEAM_MAX_SIZE = 6;
    public Bag bag;

    List<Monster> team = new ArrayList<Monster>();

    public Trainer() {
        this.bag = createBag();
    }

    public boolean addToTeam(Monster m) {
        if (team.size() < TEAM_MAX_SIZE) {
            team.add(m);
            return true;
        }

        return false;
    }

    public Bag createBag() {
        Bag bag = new Bag();
        for(PotionType pT : PotionType.values()) {
            bag.addItem(pT.toPotion());
        }
        return bag;
    }

    public Bag getBag() {
        return bag;
    }
}

