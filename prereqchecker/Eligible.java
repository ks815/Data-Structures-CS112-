package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
 public class Eligible {
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
            StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
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
        ArrayList<String> FinishedCourses = new ArrayList<>();
        for (int i = 1; i < InputCourses.size(); i++) {
            DFS(abc, FinishedCourses, InputCourses.get(i));
        }

        ArrayList<String> eligible = new ArrayList<String>();
        for (int i = 0; i < abc.size(); i++) {
            if (abc.get(i).size() == 1) {
                eligible.add(abc.get(i).get(0));
            }
            else {
                for (int j = 1; j < abc.get(i).size(); j++) {
                    if (!FinishedCourses.contains(abc.get(i).get(j))) {
                        break;
                    }
                    else {
                        if (j == abc.get(i).size() - 1) {
                            eligible.add(abc.get(i).get(0));
                        }
                    }
                }
            }
        }

        StdOut.setFile(args[2]);
        for (int i = 0; i < eligible.size(); i++) {
            if (!FinishedCourses.contains(eligible.get(i))) {
                StdOut.println(eligible.get(i));
            }
        }
    }
}
