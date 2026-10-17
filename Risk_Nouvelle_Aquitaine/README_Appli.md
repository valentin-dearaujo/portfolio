## (le fichier zip est long a dézip, le .exe est dedans)

## **Trie et insertion des données (fichier dataTreat.py)**

Pour utiliser ce fichier, il faut installer l'extension jupyter sur Visual Studio Code et les librairies pythons qu'il faut (pandas, geopandas, os, pymssql, shapely). Après il n'y a qu'à lancer toutes les cellules du script 1 par 1

## **Utilisation de l’application et divers script python pour les graphiques**

**Génération de graphiques** (fichier generateImage.py)  
Pour utiliser ce fichier vous devez remplir les paramètres; 



En faisant cela, le script va générer un graphique avec le nom renseigné en paramètre.

Les Scripts Pythons sont dans le dossier **PythonLinkCSharp**  
Les Images générés par le code sont dans **/C/Temp**  
**Comment utiliser l’application** : 

Tout d'abord, pour récupérer toutes les données de la base de données, vous devez cliquer sur le bouton « Récupérer les données », et vous pourrez ensuite utiliser l'application.

Ensuite, si vous souhaitez voir une carte concernant le « cumul de précipitation, rafale max quotidienne, rafale max sur 1 heure, rafale max sur 2 heure, rafale max sur 3 heure, température maximale, température minimale, température moyenne », vous devez cliquer sur le bouton « Même mois » et vous pouvez choisir la date que vous souhaitez consulter. Puis, cliquez sur le bouton « Générer ».

 

**Courbe :** 

Si vous voulez voir une courbe de température ou toutes autres données, vous devez choisir un type de donnée ainsi qu’un département, en choisissant un intervalle de date, il est possible qu’une messagebox apparaisse “There are no data” si les dates sont trop éloignés et qu’il manque des données dans l’intervalle.  Puis cliquez sur le bouton “Générer”. 

Il est possible d’avoir des courbes de données croisées, notamment avec les données de température maximum / minimum du même département ou pas. Puis cliquezsur le bouton “Générer”.

(ne pas oublier de remettre les défilants à “None” si vous voulez éviter de relancer l’application avant de changer de graphique)

**Événement par département (Aire empilée) :** 

Pour générer le graphique d’aire empilée sur les événements dans un département choisi, il suffit de cocher le bouton “Evenement par département” puis de choisir un département pour voir son graphique.

**Données météo par commune (Coordonées parallèles) :** 

Pour générer le graphique des coordonnées parallèles pour visualiser les données météo par commune, il suffit de cocher le bouton “Données pour une date” puis sélectionnez une date à observer. Puis cliquez sur le bouton “Générer”.

**Régressions linéaires :** 

Pour observer les graphiques de régressions linéaires des :   
“Qualité d’air ,cumul de précipitation, rafale max quotidienne, rafale max sur 1 heure, rafale max sur 2 heure, rafale max sur 3 heure, température maximale , température minimale, température moyenne”, cliquez sur le bouton “Regression linéaire” et choisissez une donnée à observer. Puis cliquez sur le bouton “Générer” 

**Test :**

Pour tester les graphiques, téléchargez le fichier “tests\_fonctionnels” et exécutez la commande : python .\\tests\_fonctionnels.py pour voir les graphiques s’afficher sur des données choisies dans le dossier où le fichier a été placé.

  