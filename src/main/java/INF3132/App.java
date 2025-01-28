package INF3132;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

import INF3132.attacks.AttackFactory;
import INF3132.combat.Combat;
import INF3132.items.subclasses.MedecineFactory;
import INF3132.items.subclasses.PotionFactory;
import INF3132.monsters.MonsterFactory;
import INF3132.parser.AttackParser;
import INF3132.parser.MedecineParser;
import INF3132.parser.MonsterParser;
import INF3132.parser.PotionParser;
import INF3132.trainer.Trainer;
import INF3132.ui.Menu;
import INF3132.ui.MenuItem;

public class App {
    public static MonsterFactory[] monsterFactories;
    public static AttackFactory[] attackFactories;

    public static void main(String[] args) {
        MonsterParser mp;
        AttackParser ap;
        PotionParser pp;
        MedecineParser mep;

        // Padding line
        System.out.println();

        // GAME ASSETS LOADING
        // Loading Monsters
        System.out.print("Chargement des Monstres...                     ");
        List<MonsterFactory> monsterFactories = new ArrayList<>();
        try {
            mp = new MonsterParser("./monstres.txt");
            monsterFactories = mp.parseFull("Monster", "EndMonster");
            System.out.println(String.format("\r%d définitions de monstres chargées.", monsterFactories.size()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("\rErreur lors du chargement de fichier de monstres !");
        }

        System.out.print("Chargement des Attaques...                     ");
        // Loading attacks
        List<AttackFactory> attackFactories = new ArrayList<>();
        try {
            ap = new AttackParser("./attacks.txt");
            attackFactories = ap.parseFull("Attack", "EndAttack");
            System.out.println(String.format("\r%d définitions d'attaques chargées.", attackFactories.size()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("\rErreur lors du chargement du fichier d'attaques !");
        }

        // TODO: passer en factories !
        try {
            pp = new PotionParser("./potions.txt");
            List<PotionFactory> potionList = pp.parseFull("Potion", "EndPotion");
            System.out.println(String.format("\r%d définitions de potions chargées.", potionList.size()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("\rErreur lors du chargement du fichier de potions !");
        }

        // Loading medecines
        try {
            mep = new MedecineParser("./medecines.txt");
            List<MedecineFactory> medicineList = mep.parseFull("Medecine", "EndMedecine");
            System.out.println(String.format("\r%d définitions de médicaments chargées.", medicineList.size()));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreurs lors du chargement du fichier des médicaments !");
        }


        System.out.print("\r                                               ");
        Menu mainMenu = new Menu("Menu principal", null);

        MenuItem[] mainMenuItems = {
            new MenuItem("Démarrer la partie", () -> { startGame(); }),
            new MenuItem("Obtenir de l'aide", () -> { displayHelp(mainMenu); })
        };

        mainMenu.setItems(mainMenuItems);
        mainMenu.prompt();

        /* System.out.println("Hello Dresseur!");


        // Test chargement potions

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

        // Bag Initialization
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

        MenuItem[] items2 = { new MenuItem("Test 2") };
        Menu menu2 = new Menu(items2);

        MenuItem[] items = { new MenuItem("Test 1", menu2) };
        Menu testMenu = new Menu(items);
        menu2.setParent(testMenu);

        testMenu.prompt(); */
    }

    public static void displayHelp(Menu backMenu) {
        System.out.println("MONSTRE DE POCHE - Guide de jeu");
        System.out.println();
        System.out.println("=== Principe du jeu ============================");
        System.out.println();
        System.out.println("Le jeu se joue à deux joueurs.");
        System.out.println("Au début de la partie, le nom du joueur 1 et 2 sont respectivement demandés.");
        System.out.println("Un ensemble de 4 Monstres est assigné aléatoirement à chacun des joueurs.");
        System.out.println("Pour chacun de ces Monstres, des statistiques sont assignées aléatoirement (attaque, vitesse, défense, etc). Ces statistiques respectent les définitions du fichier de définition.");
        System.out.println("Les joueurs jouent tour à tour.");
        System.out.println("Le but est d'être le dernier à avoir au moins un Monstre en vie");
        System.out.println("Vous avez 3 actions possibles à disposition à chaque tour");
        System.out.println();
        System.out.println("=== Comment jouer ? ============================");
        System.out.println();
        System.out.println("À chaque tour vous avez 4 actions à votre disposition.");
        System.out.println();
        System.out.println("\tCharger");
        System.out.println("La charge est votre attaque de base.");
        System.out.println("Elle affecte tous les types de Monstre de la même manière");
        System.out.println();
        System.out.println("\tAttaquer");
        System.out.println("Comme mentionné précédemment, vos Monstres disposent d'un panel d'attaques spéciales selon leur type.");
        System.out.println("Ces attaques sont plus ou moins efficaces selon le type de Monstre. Vous devrez expérimenter pour déterminer quelles attaques sont efficaces contre quels types de Monstre !");
        System.out.println();
        System.out.println("\tUtiliser un objet");
        System.out.println("Vous disposerez d'objets qui peuvent aider vos Monstres à combattre ou se remettre de blessures");
        System.out.println("Leur nom est explicite.");

        backMenu.prompt();
    }

    /**
     * Starts the game loop.
     * This method is only called once.
     */
    public static void startGame() {
        Scanner scanner = new Scanner(System.in);

        String trainer1name = "";
        do {
            System.out.print("Nom du dresseur 1 : ");
            System.out.flush();
            trainer1name = scanner.nextLine();
        } while (trainer1name.trim().length() == 0);
        Trainer trainer1 = new Trainer(trainer1name);

        String trainer2name = "";
        do {
            System.out.print("Nom du dresseur 2 : ");
            System.out.flush();
            trainer2name = scanner.nextLine();
        } while (trainer2name.trim().length() == 0);
        Trainer trainer2 = new Trainer(trainer2name);

        scanner.close();

        // Starting the game
        Combat c = Combat.initCombat(trainer1, trainer2);
        c.start();
    }
}
