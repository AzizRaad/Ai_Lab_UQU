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
public class QueueArray {
    int capacity;
    int front = 0;
    int rear = -1;
    int nElems = 0;
    Node[] a;
    public QueueArray(int s) {
        capacity = s;
        front = 0;
        rear = -1;
        nElems = 0;
        a = new Node[capacity];
    }
    public void enqueue(Node node) {
        if(!isFull()){
        rear = (rear + 1) % a.length;
        a[rear] = node;
        nElems++;
        }
        else{
            System.out.println("Queue full !");
            System.out.println(" ");
        }
    }
    public Node dequeue() {
        if (!isEmpty()){
        Node temp = a[front];
        front = (front + 1) % a.length;
        nElems--;
        return temp;
        }
        else{
            return null;
        }
    }// end of method
    
    public boolean isEmpty() {
        return (nElems == 0);
    } // end of method
    
    public boolean isFull() {
        return (nElems == a.length);
    }//end of method
    
    public int size() {
        return nElems;
    } // end of method

}
