/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.io.*;
import java.util.*;

/**
 *
 * @author Aziz
 */
public class AiLab {
    public static final int[] GOAL = {1,2,3,4,5,6,7,8,0};
    public static final ArrayList<Node> visited = new ArrayList<Node>();
    
    public static boolean isGoal(int[] a1){
        for (int i = 0; i < a1.length; i++) {
            if(a1[i] != GOAL[i]) 
                return false;
        }
        return true;
    }
    
    public static boolean isEqual(int[] a1,int[] a2){
        for (int i = 0; i < a1.length; i++) {
            if(a1[i] != a2[i]) 
                return false;
        }
        return true;
    }
    
    public static int locateZero(Node a1){
        for (int i = 0; i < a1.state.length; i++) {
            if(a1.state[i] == 0) 
                return i;
        }
        return -1;
    }
    
    public static void  BFS(int[] init){
        
    }
    
    public static ArrayList getMovements(Node node){
        ArrayList<String> direction = new ArrayList<String>();
        int index = locateZero(node) ;
        switch(index){
            case 0: //index 0 goes to 1(right) - 3(down)
                direction.add("right");
                direction.add("down");
                break;
            case 1: // index 1 goes to 0 - 2 - 4
                direction.add("left");
                direction.add("right");
                direction.add("down");
                break;
            case 2: //index 2 goes to 1 - 5
                direction.add("down");
                direction.add("left");
                break;
            case 3: //index 3 goes to 0 - 4 - 6
                direction.add("up");
                direction.add("right");
                direction.add("down");
                break;
            case 4: //index 4 goes to 1 - 5 - 7 - 3
                direction.add("up");
                direction.add("right");
                direction.add("down");
                direction.add("left");
                break;
            case 5: //index 5 goes to 2 - 8 - 4
                direction.add("up");
                direction.add("down");
                direction.add("left");
                break;
            case 6: //index 6 goes to 3 - 7
                direction.add("up");
                direction.add("right");
                break;
            case 7: //index 7 goes to 4 - 8 - 6
                direction.add("up");
                direction.add("right");
                direction.add("left");
                break;
            case 8: //index 8 goes to 5 - 7
                direction.add("up");
                direction.add("left");
                break;
        }
        return direction;
    }
    
    public static ArrayList generateChildren(Node node){
        ArrayList directions =  getMovements(node);
        ArrayList children = new ArrayList();
        for (int i = 0; i < directions.size(); i++) {
            int[] oldState = node.state;
            int[] newState = Arrays.copyOf(oldState, oldState.length);
            int index = locateZero(node);
            Node newNode;
            switch(directions.get(i).toString()){
                case "up": 
                    swap(newState, index, index-3);
                    newNode = new Node(newState, 0);
                    children.add(newNode);
                    break;
                case "right": 
                    swap(newState, index, index+1);
                    newNode = new Node(newState, 0);
                    children.add(newNode);
                    break;
                case "down": 
                    swap(newState, index, index+3);
                    newNode = new Node(newState, 0);
                    children.add(newNode);
                    break;
                case "left": 
                    swap(newState, index, index-1);
                    newNode = new Node(newState, 0);
                    children.add(newNode);
                    break;
            }
        }
        return children;
    }
    
    public static Boolean isVisited(Node node){
        for (int i = 0; i < visited.size(); i++) {
            int[] a1 = visited.get(i).state;
            int[] a2 = node.state;
            if (isEqual(a1,a2))
                return true;
        }
        return false;
    }
    
    public static void swap(int[] state,int first,int second){
        int temp = state[first];
        state[first] = state[second];
        state[second] = temp;
    }
    
    public static void main (String [] args){
        
//        boolean[] visited = new boolean[20];
        Queue<Integer> q = new LinkedList<>();
        
        int[] map = {1,2,3,4,0,6,7,8,5};
        int[] map2 = {1,2,3,4,0,6,7,8,5};
        int[] map3 = {1,2,3,0,4,6,7,8,5};
        Node node = new Node(map, 0);
        Node node2 = new Node(map3, 0);
        visited.add(node);
        System.out.println(isGoal(map));
        System.out.println(locateZero(node));
        System.out.println(getMovements(node));
        generateChildren(node);
        System.out.println(generateChildren(node));
        System.out.println(isVisited(node2));
    }
}

class  Node {
    public int[] state;
    public int cost ;
    
    public Node(int[] state,int cost){
        this.state = state;
        this.cost = cost;
    }
    
    public String toString(){
        String result = "[" ;
        for (int i = 0; i < state.length; i++) {
            result += state[i] + ", ";
        }
        return result + "] \n";
    }
}
