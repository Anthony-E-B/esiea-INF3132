package INF3132;

import java.io.IOException;
import java.util.List;

import INF3132.attacks.Attack;
import INF3132.items.Stats;
import INF3132.items.subclasses.Medecine;
import INF3132.items.subclasses.Potion;
import INF3132.monsters.Monster;
import INF3132.parser.AttackParser;
import INF3132.parser.MedecineParser;
import INF3132.parser.MonsterParser;
import INF3132.parser.PotionParser;
import INF3132.trainer.Bag;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Dresseur!");
        MonsterParser mp;
        AttackParser ap;
        PotionParser pp;
        MedecineParser mep;

        // Test chargement monstres
        try {
            mp = new MonsterParser("./monstres.txt");
            List<Monster> list = mp.parseFull("Monster", "EndMonster");
            for(Monster m : list){
                System.out.println(m.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de fichier de monstres !");
        }

        // Test chargement attaques
        try {
            ap = new AttackParser("./attacks.txt");
            List<Attack> listA = ap.parseFull("Attack", "EndAttack");
            for(Attack a : listA){
                System.out.println(a.getName() + " - " +  a.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier d'attaques !");
        }

        // Test chargement potions
        try {
            pp = new PotionParser("./potions.txt");
            List<Potion> listP = pp.parseFull("Potion", "EndPotion");
            for(Potion p : listP){
                System.out.println(p.getName() + " - " + p.getStatAffected() + " " + p.getItemPower());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier de potions !");
        }

        // Test chargement des médicaments
        try {
            mep = new MedecineParser("./medecines.txt");
            List<Medecine> listMe = mep.parseFull("Medecine", "EndMedecine");
            for(Medecine me : listMe){
                System.out.println(me.getName() + " - " + me.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreurs lors du chargement du fichier des médicaments !");
        }

        // Test création d'un bag
        Bag bag = new Bag();
        bag.addItem(new Potion("Potion", 50, Stats.HP));
        bag.addItem(new Potion("Super Potion", 100, Stats.HP));
        bag.addItem(new Potion("Hyper Potion", 200, Stats.HP));
        bag.showItems();
    }
}
