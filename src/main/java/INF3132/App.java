package INF3132;

import java.io.IOException;
import java.util.List;

import INF3132.attacks.Attack;
import INF3132.monsters.Monster;
import INF3132.parser.AttackParser;
import INF3132.parser.MonsterParser;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Dresseur!");
        MonsterParser mp;
        AttackParser ap;

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
    }   
}
