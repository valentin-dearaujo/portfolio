package info.iut.sae2.complexity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Quelques fonctions utilitaires pour gérer les fichiers.
 */
class File {

    /**
     * Fichier logique pour l'écriture.
     */
    PrintWriter FileWriting;

    /**
     * Ouverture d'un fichier.
     *
     * @param mode mode d'ouverture du fichier
     * @param nom nom du fichier à ouvrir
     * @return un objet fichier
     */
    static File open(String nom, ModeOpening mode) {
        File file = new File();
        switch (mode) {
            case WRITING, ADDITION -> {
                boolean rajout = (mode == ModeOpening.ADDITION);
                try {
                    file.FileWriting
                            = new PrintWriter(new FileWriter(nom, rajout));
                } catch (IOException e) {
                    System.out.println("Error at opening of the file " + nom);
                }
            }
            default ->
                System.out.println("Mode opening don't managed");
        }
        return file;
    }

    /**
     * Écriture dans un fichier.
     * @param chaine la chaîne à écrire.
     * @param retourLigne indique s'il faut retourner à la ligne après écriture
     */
    void write(String chaine, boolean retourLigne) {
        if (retourLigne) {
            FileWriting.println(chaine);
        } else {
            FileWriting.print(chaine);
        }
    }
    
    /**
     * Fermer le fichier.
     */
    void close() {
        if (FileWriting != null) {
            FileWriting.close();
        }
    }
}
