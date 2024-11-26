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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void bfs (ArrayList<ArrayList<String>> adv, ArrayList<ArrayList<String>> visited, Integer a) {
        if (visited.get(a).size() == 0) {
            return;
        }
        visited.add(new ArrayList<>());
        for (int i = 0; i < visited.get(a).size(); i++) {
            for (int j = 0; j < adv.size(); j++) {
                if (adv.get(j).get(0).equals(visited.get(a).get(i))) {
                    if (adv.get(j).size() == 1) {
                        break;
                    }
                    else {
                        for (int k = 1; k < adv.get(j).size(); k++) {
                            visited.get(a+1).add(adv.get(j).get(k));
                        }
                    }
                }
            }
        }
        bfs(adv, visited, a+1);
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
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
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

        String target = InputCourses.get(0);
        ArrayList<ArrayList<String>> prereq = new ArrayList<ArrayList<String>>();
        prereq.add(new ArrayList<>());
        for (int i = 0; i < abc.size(); i++) {
            if (abc.get(i).get(0).equals(target)) {
                for (int j = 0; j < abc.get(i).size(); j++) {
                    prereq.get(0).add(abc.get(i).get(j));
                }
            }
        }
        prereq.get(0).remove(0);
        bfs(abc, prereq, 0);

        ArrayList<String> finish = new ArrayList<>();
        for (int j = 2; j < InputCourses.size(); j++) {
            finish.add(InputCourses.get(j));
            dfs(abc, finish, InputCourses.get(j));
        }
        
        ArrayList<ArrayList<String>> newPrereq = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < prereq.size(); i++) {
            newPrereq.add(new ArrayList<>());
            for (int j = 0; j < prereq.get(i).size(); j++) {
                if (!finish.contains(prereq.get(i).get(j))) {
                    newPrereq.get(i).add(prereq.get(i).get(j));
                }
            }
        }

        ArrayList<ArrayList<String>> finalPrereq = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < newPrereq.size(); i++) {
            if (newPrereq.get(i).size() != 0) {
                finalPrereq.add(new ArrayList<String>());
                for (int j = 0; j < newPrereq.get(i).size(); j++) {
                    finalPrereq.get(i).add(newPrereq.get(i).get(j));
                }
            }
        }

        StdOut.setFile(args[2]);
        StdOut.println(finalPrereq.size());
        ArrayList<String> marked = new ArrayList<>();
        for (int i = finalPrereq.size()-1; i >= 0; i--) {
            for (int j = 0; j < finalPrereq.get(i).size(); j++) {
                if (!marked.contains(finalPrereq.get(i).get(j))) {
                    StdOut.print(finalPrereq.get(i).get(j) + " ");
                    marked.add(finalPrereq.get(i).get(j));
                }
            }
            StdOut.println();
        }
    }
}
