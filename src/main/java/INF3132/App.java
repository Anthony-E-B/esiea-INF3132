package INF3132;

import java.io.IOException;
import java.util.List;

import INF3132.attacks.Attack;
import INF3132.items.subclasses.Medecine;
import INF3132.items.subclasses.Potion;
import INF3132.monsters.Monster;
import INF3132.parser.AttackParser;
import INF3132.parser.MedecineParser;
import INF3132.parser.MonsterParser;
import INF3132.parser.PotionParser;

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
            List<Monster> monsterList = mp.parseFull("Monster", "EndMonster");
            for(Monster m : monsterList){
                System.out.println(m.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de fichier de monstres !");
        }

        // Test chargement attaques
        try {
            ap = new AttackParser("./attacks.txt");
            List<Attack> attackList = ap.parseFull("Attack", "EndAttack");
            for(Attack a : attackList){
                System.out.println(a.getName() + " - " +  a.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement du fichier d'attaques !");
        }

        // Test chargement potions
        try {
            pp = new PotionParser("./potions.txt");
            List<Potion> potionList = pp.parseFull("Potion", "EndPotion");
            for(Potion p : potionList){
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
    }
}
