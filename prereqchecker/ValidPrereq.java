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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq {
    
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
            StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
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

    ArrayList<String> inputCourses = new ArrayList<String>();
    StdIn.setFile(args[1]);
    while(!StdIn.isEmpty()) {
        String k = StdIn.readString();
        inputCourses.add(k);
        
    }
    String course1 = inputCourses.get(0);
    String course2 = inputCourses.get(1);
    ArrayList<String> visited1 = new ArrayList<>();//new ArrayList<String> = new ArrayList<>
    DFS(abc, visited1, course2);

    StdOut.setFile(args[2]);
    if (visited1.contains(course1)) {
        StdOut.println("NO");
    }
    else {
        StdOut.println("YES");
    }

    }
}
