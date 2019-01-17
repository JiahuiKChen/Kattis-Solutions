import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class RumorMill {
    // representing the graph as a hashmap of vertext to an arraylist of its exiting edges (vertices it has edges to)
    static HashMap<String, HashSet<String>> graph = new HashMap<String, HashSet<String>>();

    // tracks visited nodes in BFS
    static HashSet<String> visited = new HashSet<>();

    // tracks nodes in each level in its own list so it can be lexicographically sorted
    static ArrayList<ArrayList<String>> levels = new ArrayList<>();

    // all the rumor starters, rumor reports must be generated for each element in this list
    static ArrayList<String> rumorStarters = new ArrayList<>();

    public static void main(String[] args) {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        try {
            // getting all students/ vertices
            String first = r.readLine();
            int numNodes = Integer.parseInt(first);
            for (int i = 0; i < numNodes; i++) {
                String student = r.readLine();
                // add vertex to graph, with no edges
                graph.put(student, new HashSet<String>());
            }

            // getting edges in graph
            String edges = r.readLine();
            int edgesNum = Integer.parseInt(edges);
            for (int i = 0; i < edgesNum; i++) {
                String line = r.readLine();
                String[] friendPairs = line.split("\\s+");
                String source = friendPairs[0];
                String dest = friendPairs[1];
                // adding edge to graph between both nodes
                graph.get(source).add(dest);
                graph.get(dest).add(source);
            }

            // getting number of rumor starters/reports
            String rumors = r.readLine();
            int rumorNum = Integer.parseInt(rumors);
            for (int i = 0; i < rumorNum; i++) {
                String rumorSource = r.readLine();
                // adding each rumor starter to list
                rumorStarters.add(rumorSource);
            }
        } catch (Exception potato) {
        }

        // generate reporst for each rumor starter
        for (String starter : rumorStarters){
            RumorReport(starter);

            // clear visited tracker and levels tracker after every report
            visited.clear();
            levels.clear();
        }
    }

    public static void BFS(String node){
        LinkedList<String> q = new LinkedList<>();
        q.addLast(node);

        HashMap<String, Integer> depths = new HashMap<>();
        depths.put(node, 0);
        visited.add(node);

        while (!q.isEmpty()) {
            String curr = q.removeFirst();
            int depth = depths.get(curr);

            for (String neighbor : graph.get(curr)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    q.addLast(neighbor);
                    depths.put(neighbor, depth + 1);
                }
            }
        }

        int maxDepth = 0;
        for (String n : depths.keySet()) {
            maxDepth = Math.max(depths.get(n), maxDepth);
        }

        for (int i = 0; i < maxDepth + 1; i++) {
            levels.add(new ArrayList<>());
        }

        for (String n : depths.keySet()) {
            levels.get(depths.get(n)).add(n);
        }
    }

    public static void RumorReport(String starter){
        // BFS to populate list of nodes at each level/day
        BFS(starter);

        // if any unvisited nodes, add those to last list/day
        if (visited.size() < graph.size()){
            Set<String> nonvisted = new HashSet<>(graph.keySet());
            nonvisted.removeAll(visited);
            levels.add(new ArrayList<>(nonvisted));
        }

        // sorting all days/levels and appending to printed rumor report
        StringBuilder rumorReport = new StringBuilder();
        for (ArrayList<String> day : levels){

            Collections.sort(day);

            for (String name : day){
                rumorReport.append(name);
                rumorReport.append(" ");
            }
        }
        String report = rumorReport.toString().trim();
        System.out.println(report);
    }
}
