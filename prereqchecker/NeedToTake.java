package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static ArrayList<String> DFS (ArrayList<ArrayList<String>> adv, ArrayList<String> visited, String a) {
        visited.add(a);
        dfs(adv, visited, a); //call dfs method to put all finished courses into arrayList(visited)
        return visited;
    }
    public static void dfs (ArrayList<ArrayList<String>> adv, ArrayList<String> visited, String a) {
        for (int i = 0; i < adv.size(); i++) {
            if (adv.get(i).get(0).equals(a)) {
                for (int j = 0; j < adv.get(i).size(); j++) { //some courses do not have prerequisite. To avoid out of bounce, we started from j = 0
                    if(!visited.contains(adv.get(i).get(j))) {
                        visited.add(adv.get(i).get(j));
                        dfs(adv, visited, adv.get(i).get(j));
                    }
                }
            }
        }
    }
    
    
    
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

    // WRITE YOUR CODE HERE
    ArrayList<String> manyDatas = new ArrayList<String>();
        StdIn.setFile(args[0]);
        while(!StdIn.isEmpty()) {
            String k = StdIn.readString();
            manyDatas.add(k);
            
        }

        int v = Integer.parseInt(manyDatas.get(0));//use string double arrayList(abc) to set adjlist graph
        ArrayList<ArrayList<String>> abc = new ArrayList<ArrayList<String>>(v);
        for (int i = 0; i < v; i++) {
            abc.add(new ArrayList<String>());
            abc.get(i).add(manyDatas.get(i+1));//use for loop to create all vertices
        }
        for (int i = v + 2; i < manyDatas.size(); i = i+2) {
            Graph.addEdge(abc, manyDatas.get(i), manyDatas.get(i + 1)); //use graph class to add edge
        }

        ArrayList<String> InputCourses = new ArrayList<>();
        StdIn.setFile(args[1]);
        while(!StdIn.isEmpty()) {
            String k = StdIn.readString();
            InputCourses.add(k);
            
        }

        ArrayList<String> PrereqCourses = new ArrayList<String>();
        DFS(abc, PrereqCourses, InputCourses.get(0));

        ArrayList<String> CoursesIgnore = new ArrayList<String>();
        for (int i = 2; i < InputCourses.size(); i++) {
            DFS(abc, CoursesIgnore, InputCourses.get(i));
        }

        ArrayList<String> CoursesToTake = new ArrayList<>();
            for (int i = 1; i < PrereqCourses.size(); i++) {
               if (!CoursesIgnore.contains(PrereqCourses.get(i))) {
                CoursesToTake.add(PrereqCourses.get(i));
               }
            } 
        

        StdOut.setFile(args[2]);
        for (int i = 0; i < CoursesToTake.size(); i++) {
            StdOut.println(CoursesToTake.get(i));
        }
    }
}
