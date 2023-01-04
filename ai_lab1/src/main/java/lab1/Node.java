/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

/**
 *
 * @author Aziz
 */
public class  Node {
    public int[] state;
    public int depth ;
    
    public Node(int[] state,int cost){
        this.state = state;
        this.depth = cost;
    }
    
    public String toString(){
        String result = "\n" ;
        for (int i = 1; i < state.length+1; i++) {
            result += state[i-1] + " ";
            if(i % 3 == 0) result += "\n";
        }
        return result + "cost: " + this.depth + "\n";
    }
}
