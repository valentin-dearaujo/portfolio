package info.iut.sae2.algorithm;

import info.iut.sae2.graphs.GraphLoader;
import info.iut.sae2.graphs.IGraph;
import info.iut.sae2.graphs.INode;
import info.iut.sae2.graphs.IEdge;
import info.iut.sae2.graphs.Color;
import java.util.HashMap;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

public class WeshPowellTest {

    @Test
    public void testWeshPowell() {
        IGraph graph = GraphLoader.loadFromFile("dataset/simple_nodes.csv", "dataset/simple_edges.csv");
        WeshPowell wp = new WeshPowell();
        wp.apply(graph, new HashMap<>());
        for (INode i : graph.getNodes()) {
            for (INode j : graph.getNeighbors(i)) {
                assertNotEquals(i.getColor(), j.getColor());
            }
        }
    }

    @Test
     public void testGenGraph() {
        IGraph graph = GraphLoader.loadFromFile("dataset/planar_grid_5x5_nodes.csv", "dataset/planar_grid_5x5_edges.csv");
        Algorithm wp = new WeshPowell();
        wp.apply(graph, new HashMap<>());

        int nbLancements = 5;
        long sommeTemps = 0;


        for (int i = 0; i < 3; i++) {
        IGraph graphChauffe = graph.copy(); // On protège le graphe original
        wp.apply(graphChauffe, new HashMap<>());
    }


        for (int i = 0; i < nbLancements; i++) {

            IGraph graphATester = graph.copy();

            long debut = System.nanoTime();
            wp.apply(graphATester, new HashMap<>());
            long fin = System.nanoTime();

            sommeTemps += (fin - debut);
        }


        double tempsMoyenMs = (sommeTemps / (double) nbLancements) / 1_000_000.0;
        System.out.println("Temps moyen pour ce graphe : " + tempsMoyenMs + " ms");
    }
}
