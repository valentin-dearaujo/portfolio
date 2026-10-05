/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info.iut.sae2.complexity;
import info.iut.sae2.algorithm.Algorithm;
/**
 *
 * @author vrioux
 */
public class PlotLoader {
    Algorithm<Object> algo;
    public PlotLoader(Algorithm viewOn) {
        this.algo = viewOn;
    }
    
    public void start(String file){
        File fileDycho = File.open(file, ModeOpening.WRITING);
                fileDycho.write("nbNoeud nbComparaisons", true);
                
    }
    
}
