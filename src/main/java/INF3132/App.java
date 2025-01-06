package INF3132;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackFactory;
import INF3132.items.Stats;
import INF3132.items.subclasses.Medecine;
import INF3132.items.subclasses.Potion;
import INF3132.monsters.MonsterFactory;
import INF3132.parser.AttackParser;
import INF3132.parser.MedecineParser;
import INF3132.parser.MonsterParser;
import INF3132.parser.PotionParser;
import INF3132.parser.exception.UnhandledMonsterTypeException;
import INF3132.trainer.Trainer;
import INF3132.trainer.exception.TeamFullException;
import INF3132.trainer.Bag;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Dresseur!");
        MonsterParser mp;
        AttackParser ap;
        PotionParser pp;
        MedecineParser mep;

        // Test chargement monstres
        List<MonsterFactory> monsterFactories = new ArrayList<>();
        try {
            mp = new MonsterParser("./monstres.txt");
            monsterFactories = mp.parseFull("Monster", "EndMonster");
            for (MonsterFactory m : monsterFactories){
                System.out.println(m.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de fichier de monstres !");
        }

        // Test chargement attaques
        List<AttackFactory> attackList = new ArrayList<>();
        try {
            ap = new AttackParser("./attacks.txt");
            attackList = ap.parseFull("Attack", "EndAttack");
            for (AttackFactory af : attackList){
                System.out.println(af.getName() + " - " +  af.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier d'attaques !");
        }

        // Test chargement potions
        try {
            pp = new PotionParser("./potions.txt");
            List<Potion> potionList = pp.parseFull("Potion", "EndPotion");
            for (Potion p : potionList){
                System.out.println(p.getName() + " - " + p.getStatAffected() + " " + p.getItemPower());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier de potions !");
        }

        // Test chargement des médicaments
        try {
            mep = new MedecineParser("./medecines.txt");
            List<Medecine> medicineList = mep.parseFull("Medecine", "EndMedecine");
            for (Medecine me : medicineList){
                System.out.println(me.getName() + " - " + me.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreurs lors du chargement du fichier des médicaments !");
        }

        // Initialize combat
        Trainer t1 = new Trainer();
        Trainer t2 = new Trainer();

        Trainer[] trainers = { t1, t2 };

        // Combat combat = Combat.initCombat(t1, t2);

        for (Trainer trainer : trainers) {
            for (int i = 0; i < 6; i++) {
                try {
                    trainer.addToTeam(
                        monsterFactories.get(
                            (int)Math.floor(Math.random()  * monsterFactories.size())
                        ).create(attackList)
                    );
                } catch (UnhandledMonsterTypeException e) {
                    e.printStackTrace();
                } catch (TeamFullException e) {
                    e.printStackTrace();
                }
            }
        }

        t1.getTeam().forEach(m -> {
            System.out.println(m.getName());
            m.getAttacks().forEach(a -> {
                System.out.println(a.getName());
            });
        });

        // Test création d'un bag
        Bag bag = new Bag();
        bag.addItem(new Potion("Potion", 50, Stats.HP));
        bag.addItem(new Potion("Super Potion", 100, Stats.HP));
        bag.addItem(new Potion("Hyper Potion", 200, Stats.HP));
        bag.showItems();
    }
}
