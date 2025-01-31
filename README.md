# README 

Ce projet est un jeu "Pokémon-like" développé par Anthony BERTRAND et Perrin LE LOUARN. Il permet de faire s'affronter deux dresseurs possédant une équipe de trois monstres dans un duel au tour par tour. Chacun des dresseurs peut choisir d'attaquer avec son monstre, d'utiliser un objet ou de changer de monstre présent sur le terrain. Le dernier dresseur à avoir un monstre en vie remporte le duel. 

## Comment jouer ?

Pour jouer au jeu, il faut d'abord le compiler à l'aide de la commande "javac -encoding utf-8 -d target/classes -sourcepath src/main/java src/main/Java/INF3132/App.java", puis copier/coller les ressources présentes dans ./src/main/resources/ dans ./target/classes/. Enfin, le programme est lançable depuis ce dossier (./target/classes/) en faisant "java -cp ./ INF3132.App". 

Il est ensuite possible de lancer le jeu en entrant 1 dans le terminal qui s'affiche, ou bien d'afficher les différentes informations propres au jeu avec 2.

Les ressources utilisées pour lancer le jeu permettent d'avoir une base de données suffisantes pour faire plusieurs parties sans qu'elles ne se ressemblent. Pour les remplacer ou les compléter, il suffit d'éditer les fichiers correspondants aux différents types.

## Particularités

Dans le cadre de notre projet, nous avons fait des choix quant à certains points qui pouvaient être ambigus dans le sujet : 

- Les équipes sont tirées au hasard, avec un principe d'équilibrage (pas de possibilité d'avoir 3 monstres de type feu par exemple). 
- Nous avons choisi de garder un type Normal pour certains monstres. 
- Quand un monstre enterré attaque avec une attaque de type Sol, la durée de l'état est recalculée (entre le random et la valeur précédente).
- Nous avons choisi de laisser l'attaque à mains nues disponible à chaque instant, même si elle n'est pas la plus efficace dans toutes les situations.
- Les attaques sont instanciées au "semi-hasard" avec trois attaques du même type et une attaque de type Normal pour chaque Monstre.
- Au niveau du code, nous avons essayé d'implémenter l'event pattern pour solutionner des problèmes structurels complexes (= qui auraient ajouté de la complexité inutile au code). 
- L'ensemble du processus de développement peut être trouvé dans le Github à l'adresse suivante : https://github.com/Anthony-E-B/esiea-INF3132

Merci d'avance pour vos commentaires
