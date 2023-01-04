/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import java.io.*;
import java.util.*;
import java.lang.Math;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Aziz
 */
public class AiLab {

    public static final int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};  //the goal array that represents the goal 8-puzzle
    public static final ArrayList<Node> visited = new ArrayList<Node>();
    public static Queue<Node> fringe = new LinkedList<Node>();
    public static long nodesCounter = 0;
    public static final int TRUEDEPTH = 15;
    
    // This method should help us make sure if we hit the goal or not yet 
    public static boolean isGoal(int[] a1) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != GOAL[i]) {
                return false;
            }
        }
        return true;
    }
    
    //This method help us in addition to isVisited method to make sure if a node is visited or not
    public static boolean isEqual(int[] a1, int[] a2) {
        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }
        return true;
    }
    
    //This method help us at the initial puzzle to find where is the empty box at
    public static int locateZero(Node a1) {
        for (int i = 0; i < a1.state.length; i++) {
            if (a1.state[i] == 0) {
                return i;
            }
        }
        return -1;
    }
    
    //This method should help us direct the empty box depending on its postion
    public static ArrayList getMovements(Node node) {
        ArrayList<String> direction = new ArrayList<String>();
        int index = locateZero(node);
        switch (index) {
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
    
    //This method helps on generating the available children of the node we are at 
    public static ArrayList<Node> generateChildren(Node node) {
        ArrayList directions = getMovements(node);
        ArrayList<Node> children = new ArrayList<Node>();
        for (int i = 0; i < directions.size(); i++) {
            int[] oldState = node.state;
            int[] newState = Arrays.copyOf(oldState, oldState.length);
            int index = locateZero(node);
            Node newNode;
            switch (directions.get(i).toString()) {
                case "up":
                    swap(newState, index, index - 3);
                    newNode = new Node(newState, node.depth + 1);
                    children.add(newNode);
                    break;
                case "right":
                    swap(newState, index, index + 1);
                    newNode = new Node(newState, node.depth + 1);
                    children.add(newNode);
                    break;
                case "down":
                    swap(newState, index, index + 3);
                    newNode = new Node(newState, node.depth + 1);
                    children.add(newNode);
                    break;
                case "left":
                    swap(newState, index, index - 1);
                    newNode = new Node(newState, node.depth + 1);
                    children.add(newNode);
                    break;
            }
        }
        return children;
    }
    
    //This method help us to make sure of this node has been visited before or no 
    public static Boolean isVisited(Node node) {
        for (int i = 0; i < visited.size(); i++) {
            int[] a1 = visited.get(i).state;
            int[] a2 = node.state;
            if (isEqual(a1, a2)) {
                return true;
            }
        }
        return false;
    }
    
    //This method help us swap between the indexes in the puzzle (array)
    public static void swap(int[] state, int first, int second) {
        int temp = state[first];
        state[first] = state[second];
        state[second] = temp;
    }

    public static Node randomNode() {
        Node node = new Node(GOAL, 0);
        for (int i = 0; i < TRUEDEPTH; i++) {
            ArrayList<Node> children = generateChildren(node);
            int randomIndex = (int) (Math.random() * children.size());
            node = children.get(randomIndex);
        }
        node.depth = 0;
        return node;
    }
    
    //Breadth-First Algorithm
    public static boolean BFS() {
        Node puzzle = randomNode();
        System.out.println("started at:\n" + puzzle);
        visited.add(puzzle);
        fringe.add(puzzle);
        while (!fringe.isEmpty()) {
            puzzle = fringe.remove();
            if (isGoal(puzzle.state)) {
                System.out.println("finished at:");
                System.out.println(puzzle);
                visited.clear();
                fringe.clear();
                return true;
            }
            ArrayList<Node> children = generateChildren(puzzle);
            nodesCounter += children.size();
            for (int i = 0; i < children.size(); i++) {
                Node childNode = children.get(i);
                if (!isVisited(childNode) & childNode.depth <= TRUEDEPTH) {
                    visited.add(childNode);
                    fringe.add(childNode);
                }
            }
        }
        visited.clear();
        fringe.clear();
        return false;
    }
    
    //Deapth-First Algorithm
    public static boolean DFS(Node puzzle) {
        if (isGoal(puzzle.state)) {
            System.out.println("finished at:");
            System.out.println(puzzle);
            return true;
        }
        else if (puzzle.depth == TRUEDEPTH) {
            return false;
        } else {
            ArrayList<Node> children = generateChildren(puzzle);
            nodesCounter += children.size();
            for (int i = 0; i < children.size(); i++) {
                Node childNode = children.get(i);
                if (DFS(childNode)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Main method where all testing
    public static void main(String[] args) {
        
        //calcluating complexity for BFS:
        double timeSum = 0;
        long nodeSum = 0;
        int loopsCounter = 5; // determine how many puzzles we will use before taking average
        for (int i = 1; i <= loopsCounter; i++) {
            nodesCounter = 1;
            long start1 = System.nanoTime();
            System.out.println("Using BFS, Puzzle number: "+i);
            BFS();
            System.out.println("************************");
            long end1 = System.nanoTime();
            long elapsedTime = end1 - start1;
            double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
            timeSum += elapsedTimeInSecond;
            nodeSum += nodesCounter;
        }
        double BFSmeanTime = timeSum / loopsCounter;
        double BFSmeanNodesCounter = nodeSum / loopsCounter;

        //calcluating complexity for DFS:
        timeSum = 0;
        nodeSum = 0;
        for (int i = 1; i <= loopsCounter; i++) {
            nodesCounter = 1;
            long start1 = System.nanoTime();
            Node puzzle = randomNode();
            System.out.println("Using DFS, Puzzle number: "+i);
            System.out.println("started at:\n" + puzzle);
            DFS(puzzle);
            System.out.println("************************");
            long end1 = System.nanoTime();
            long elapsedTime = end1 - start1;
            double elapsedTimeInSecond = (double) elapsedTime / 1_000_000_000;
            timeSum += elapsedTimeInSecond;
            nodeSum += nodesCounter;
        }
        double DFSmeanTime = timeSum / loopsCounter;
        double DFSmeanNodesCounter = nodeSum / loopsCounter;

        //displaying the complexity:
        System.out.println("the average for BFS and DFS for " + loopsCounter + " times");
        System.out.println("BFS took :" + BFSmeanTime + " seconds and created: " + BFSmeanNodesCounter + " nodes.");
        System.out.println("DFS took :" + DFSmeanTime + " seconds and created: " + DFSmeanNodesCounter + " nodes.");
    }
}