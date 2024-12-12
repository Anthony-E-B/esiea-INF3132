package INF3132;

import java.io.IOException;
import java.util.List;

import INF3132.monsters.Monster;
import INF3132.parser.MonsterParser;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello Dresseur!");
        MonsterParser mp;
        try {    
            mp = new MonsterParser("./ressources/monstres.txt");
            List<Monster> list = mp.parseFull("Monster", "EndMonster");
            for(Monster m : list){
                System.out.println(m.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de fichier de monstres !");
        }
    }   
}
