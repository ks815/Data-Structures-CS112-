package prereqchecker;

import java.util.*;

public class Graph {
    private final int v; // total number of vertices
    private ArrayList<ArrayList<String>> abc; //adjlist for every vertex

    public Graph(int v) { //constructor
        this.v = v;
        ArrayList<ArrayList<String>> abc = new ArrayList<ArrayList<String>>(v); //outer arraylist
        for (int i = 0; i < v; i++) {
            abc.add(new ArrayList<String>());
        } // inner arraylist
    }

    public static void addEdge (ArrayList<ArrayList<String>> abc, String u, String v) {
        for (int i = 0; i < abc.size(); i++) {
            if (abc.get(i).get(0).equals(u)) {
                abc.get(i).add(v);
            }
        }
    }

    public static void printGraph(ArrayList<ArrayList<String> > abc)
    {
        for (int i = 0; i < abc.size(); i++) {
            for (int j = 0; j < abc.get(i).size(); j++) {
                StdOut.print(abc.get(i).get(j) + " ");
            }
            StdOut.println();
        }
    }
  
    
    
}
