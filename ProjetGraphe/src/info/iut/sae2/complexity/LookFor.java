/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author vrioux
 */

package info.iut.sae2.complexity;

public class LookFor {
    public long nbCombination;

    int position;
    boolean find;

    public LookFor(int nbCombinaison, int position, boolean trouve) {
        this.nbCombination = nbCombinaison;
        this.position = position;
        this.find = trouve;
    }

    public LookFor() {
    this.nbCombination=0;
    this.position=-1;
    this.find=false;
    
    }
    
    
    
    
}
