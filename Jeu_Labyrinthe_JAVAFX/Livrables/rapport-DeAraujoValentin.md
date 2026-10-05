# Journal de bord -- Projet de reprise Java (Labyrinthe)
**Etudiant :** Val De Araujo

---

## Seance -- Mardi 1er septembre 2026 (16h00-18h00, duree : 2h)


**Objectifs**
* Exercice 1 , 2 : s'informer du code et le manipuler en creant les diagrammes de classes 

* Exercice 3 : Creer la classe Salle avec hashcode et equals
* Exercice 4 : Complementer la methode charger pour l'etage 1 et 2 
* Exercice 5 : implementer Salle.estAdjacente.


**Realisations**
* Adjacence classique entre deux salles d'un meme etage (distance de Manhattan calculee a la main)
* Adjacence via un escalier montant/descendant entre deux etages successifs.
* equals/hashCode sur Salle, bases sur les coordonnees et le numero d'etage.
* Realisation de tests

**Difficultes rencontrees et solutions**
* Premier oubli : je n'avais code que le cas des escaliers dans estAdjacente, sans le cas d'adjacence classique sur un meme etage 


---

## Seance -- Mercredi 2 septembre 2026 (8h15-10h15, duree : 2h) mais commit apres avoir fini chez moi 

**Objectifs**
* Exercices 6 a 8 : initialisation et affichage du labyrinthe, gestion des salles accessibles.
* Exercices 9 a 11 : logique des personnages (heros) et encapsulation graphique (sprites) 
* Commit des diagrammes de classes

**Realisations**
* Initialisation Labyrinthe (Ex 6, 8) : chargement des etages 1 et 2 dans le constructeur, et ajout de la logique permettant d'identifier les salles accessibles (adjacentes) depuis une position.
* Moteur d'affichage (Ex 7) : modification de Dessin pour dessiner l'etage courant, et developpement de la classe Vue integrant le dessin et le labyrinthe pour afficher le premier etage au lancement.
* Logique Personnages (Ex 9, 10) : creation de la classe abstraite APersonnage et de sa classe fille Heros. Le deplacement est creer par l'attribut salleChoisie, qui n'est retourne par faitSonChoix que s'il correspond a une salle accessible.
* Rendu des entites (Ex 11) : creation de la classe abstraite ASprite. Elle encapsule l'IPersonnage logique tout en gerant de maniere autonome ses coordonnees graphiques et son Image.

---

## Seance -- Jeudi 3 septembre 2026 (14h00-18h00, duree : 4h)

**Objectifs**
* Exercices 12/13 : personnage du heros, deplacement au clavier, affichage des sprites.

**Realisations**
* Classe HerosSprite : chargement de l'image du heros, gestion des touches flechees et des touches M/D pour les escaliers (reutilise estAdjacente pour trouver la salle reliee par un escalier).
* Mise a jour de l'attribut salleChoisie du heros par le gestionnaire clavier, sans court-circuiter la boucle de jeu.

**Difficultes rencontrees et solutions**
* Le heros ne s'affichait pas : l'image etait bien chargee mais jamais appliquee au sprite (setImage non appele) provoquant une exception silencieuse a chaque frame
* Le clavier appelait setPosition directement au lieu de passer par salleChoisie, et ne verifiait pas si la case ciblee existait vraiment (mur/bord de grille) : un appui contre un mur renvoyait null et placait le heros a une position nulle, provoquant un NullPointerException dans Vue.dessiner() au tour suivant. Corrige en ajoutant une verification if (cible != null) avant de mettre a jour salleChoisie.
* Bug d'affichage inverse mur/salle dans Dessin.dessinSalle : le cas ESalle.NORMALE (une salle praticable) dessinait l'image de mur (mur0.gif), donnant l'illusion que les couloirs etaient des murs et inversement. 

---

## Seance -- Vendredi 4 septembre 2026 (8h15-12h15 et 16h00-18h00, duree totale : 6h)

**Objectifs**
* Exercices 14/15 : monstres et polymorphisme.

**Realisations**
* Classe Monstre (heritant de APersonnage) : faitSonChoix tire une salle au hasard parmi les salles accessibles.
* Classe MonstreSprite (heritant de ASprite), sans gestion clavier.
* Completion de Core.initSprites : creation de 10 monstres places aleatoirement sur les salles du labyrinthe (hors entree), chacun encapsule dans un MonstreSprite et ajoute a la vue.

**Difficultes rencontrees et solutions**
* Erreur de compilation Random.nextInt(...) utilisee comme methode statique : correction en instanciant un objet Random.
* Seulement 6 monstres sur 10 visibles a l'ecran : en fait tous etaient bien crees, mais certains etaient places sur l'etage 2, non affiche tant qu'on n'y etait pas monte (Vue.dessiner() ne dessine que l'etage courant) -- pas un bug, mais je le compte comme et j'ai regle en mettant tous les monstres sur l'etage en cours

---

## Seance -- Lundi 7 septembre (14h00-16h00) et Mercredi 9 septembre 2026 (14h00-16h00) (duree totale : 4h)

**Objectifs**
* Exercice 16 : resoudre les problemes d'acces concurrent sur la liste des personnages.
* Exercice 17 : implementer un deplacement visuel fluide pour le heros.
* Exercice 18 : eclairage localise autour du heros.

**Realisations**
* Ajout d'un assombrissement progressif des salles dans Dessin.dessinSalle, en fonction de leur distance de Manhattan a la position du heros 
* Recuperation de la position du heros a partir de la collection de sprites 
* Securisation de la Vue (Ex 16) : remplacement de la structure ArrayList par CopyOnWriteArrayList pour la collection de personnages. 
* Deplacement fluide (Ex 17) : decouplage de la logique de deplacement (qui reste salle par salle) et du rendu graphique (qui se fait desormais pixel par pixel). * Ajout d'un blocage de la saisie : le heros ne peut plus initier un nouveau deplacement logique tant que son animation graphique pixel par pixel n'est pas achevee.

**Difficultes rencontrees et solutions**
* Premier NullPointerException au lancement : getPositionHeros() appelee avant qu'aucun sprite ne soit ajoute a la vue (le dessin initial se fait dans le constructeur de Dessin)
* Apres ajout de l'eclairage, les chemins du labyrinthe ont disparu visuellement : en modifiant le switch de dessinSalle, le default qui dessinait les salles normales avait ete supprime par erreur, donc plus rien n'etait dessine sous le voile d'assombrissement. Corrige en remettant la branche default.

---


## Seance -- Jeudi 10 septembre 2026 (10h00-12h00, duree : 2h)

**Objectifs**
* Resolution d'un conflit git avant remise.

**Realisations**
* Historique local et distant divergents (git push rejete en non-fast-forward, suite a un commit ajoute cote GitLab). 
* Resolution avec git pull --rebase pour garder un historique lineaire, commit par commit, plutot qu'un commit de fusion.

**Difficultes rencontrees et solutions**
* Comprehension de la difference entre merge (cree un commit de fusion, historique en losange) et rebase (rejoue les commits un par un, historique lineaire) -- documentation approfondie sur les differentes strategies de resolution de conflits Git.


## Seance -- Vendredi 11 septembre 2026 

**Objectifs**
* Ajout des derniers tests et finition des diagrammes 


## Difficultes rencontrees et solutions 

Pendant tout le projet, de nombreuses erreurs que je ne comprennais pas apparaissaient, qui etaient du au fait que les switch avaient obligatoirement besoin de break; j'ai donc perdu pas mal de temps dessus
