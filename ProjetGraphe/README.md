# Projet Graphe

Application Java de visualisation de graphes et d'experimentation d'algorithmes de coloration.

## Prerequis

- JDK 21
- Apache Ant
- Un environnement de bureau pour l'interface graphique Swing

Les dependances JUnit 4 et Hamcrest necessaires aux tests sont fournies dans `libs/`.

## Lancement

Extraire l'archive, ouvrir un terminal dans le dossier extrait (celui qui contient `build.xml`), puis executer :

```powershell
ant run
```

La commande compile le projet et ouvre l'application. Choisir un graphe dans la liste, puis lancer l'algorithme souhaite avec les boutons disponibles.

## Tests

Pour compiler et executer les tests automatises :

```powershell
ant test
```

Etat actuel des tests : deux assertions de `NodeTest` echouent, et `GraphLoaderTest` ainsi que `SizeTest` ne contiennent pas de methode de test executable. Les autres tests passent. Ces tests sont inclus tels quels.

## Contenu

- `src/` : code source Java
- `test/` : tests JUnit
- `dataset/` : graphes d'exemple requis par l'application et les tests
- `libs/` : dependances JUnit et Hamcrest
- `build.xml`, `manifest.mf`, `nbproject/` : configuration Ant

Les resultats de compilation, reglages propres a une machine, le fichier UML et l'archive de test imbriquee ne sont pas inclus.
