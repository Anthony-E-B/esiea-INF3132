package INF3132.trainer;

import java.util.List;
import java.util.ArrayList;

import INF3132.monsters.Monster;

public class Trainer {
    public static final int TEAM_MAX_SIZE = 6;

    List<Monster> team = new ArrayList<Monster>();

    public Trainer() {
    }

    public boolean addToTeam(Monster m) {
        if (team.size() < TEAM_MAX_SIZE) {
            team.add(m);
            return true;
        }

        return false;
    }
}

