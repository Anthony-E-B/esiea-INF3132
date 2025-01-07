package INF3132;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import INF3132.attacks.Attack;
import INF3132.attacks.AttackFactory;
import INF3132.items.Stats;
import INF3132.items.exception.UnusableItemException;
import INF3132.items.subclasses.Medecine;
import INF3132.items.subclasses.Potion;
import INF3132.monsters.Monster;
import INF3132.monsters.MonsterFactory;
import INF3132.monsters.Status;
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
        List<AttackFactory> attackFactories = new ArrayList<>();
        try {
            ap = new AttackParser("./attacks.txt");
            attackFactories = ap.parseFull("Attack", "EndAttack");
            for (AttackFactory af : attackFactories){
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
        Trainer t1 = new Trainer("Entraîneur 1");
        Trainer t2 = new Trainer("Entraîneur 2");

        Trainer[] trainers = { t1, t2 };

        // Combat combat = Combat.initCombat(t1, t2);

        for (Trainer trainer : trainers) {
            for (int i = 0; i < 6; i++) {
                try {
                    trainer.addToTeam(
                        monsterFactories.get(
                            (int)Math.floor(Math.random()  * monsterFactories.size())
                        ).create(attackFactories)
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

        // Medicines test
        //
        try {
            System.out.println("Monster medication test");

            // Potion
            System.out.println("Potion : ");
            Monster monsterToMedicate = monsterFactories.get((int)(Math.floor(Math.random() * monsterFactories.size()))).create(attackFactories);
            System.out.print(String.format("Original HP : %d\t", monsterToMedicate.getHp()));
            monsterToMedicate.inflictDamage(20);
            System.out.print(String.format("HP after dmg : %d\t", monsterToMedicate.getHp()));
            Potion p = new Potion("HP", 15, Stats.HP);
            p.use(monsterToMedicate);
            System.out.print(String.format("HP after relieving 15 : %d\n", monsterToMedicate.getHp()));

            // Burned
            System.out.println("Burned medicine : ");
            Medecine m = new Medecine("Anti brûlure", Status.BURNED);
            System.out.print("Original status : " + monsterToMedicate.getStatus() + " \t");
            monsterToMedicate.setStatus(Status.BURNED);
            System.out.print("Burned status : " + monsterToMedicate.getStatus() + " \t");
            m.use(monsterToMedicate);
            System.out.print("After medication : " + monsterToMedicate.getStatus() + " \t");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
