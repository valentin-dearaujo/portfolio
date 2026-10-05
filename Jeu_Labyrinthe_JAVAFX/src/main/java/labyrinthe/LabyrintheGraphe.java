/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labyrinthe;

import java.util.Collection;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 *
 * @author vdearaujo
 */
public class LabyrintheGraphe extends Labyrinthe {

    private Graph<ISalle, DefaultEdge> graphe;

    public LabyrintheGraphe() {
        super();
        creerLabyrinthe();
    }

    /**
     * Construit les sommets et les arêtes du graphe en fonction des salles et
     * de leurs adjacences
     */
    public void creerLabyrinthe() {
        this.graphe = new SimpleGraph<>(DefaultEdge.class);

        for (ISalle salle : this) {
            this.graphe.addVertex(salle);
        }

        for (ISalle s1 : this) {
            for (ISalle s2 : this) {
                if (!s1.equals(s2) && s1.estAdjacente(s2)) {
                    this.graphe.addEdge(s1, s2);
                }
            }
        }
/*
        Collection<ISalle> plusCourtChemin = chemin(getEntree(), getSortie());

        for (ISalle s : plusCourtChemin) {
            System.out.println("Salle en X: " + s.getX() + " Y: " + s.getY());
        }
*/
    }

    /**
     * Calcule le plus court chemin entre deux salles à l'aide de l'algorithme
     * de Dijkstra
     *
     * @param u La salle de départ.
     * @param v La salle d'arrivée.
     * @return Une collection de salles représentant le chemin
     */
    @Override
    public Collection<ISalle> chemin(ISalle u, ISalle v) {
        DijkstraShortestPath<ISalle, DefaultEdge> dsp = new DijkstraShortestPath<>(this.graphe);
        return dsp.getPath(u, v).getVertexList();

    }
}
