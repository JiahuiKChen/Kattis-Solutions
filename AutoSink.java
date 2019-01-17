import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class AutoSink {
    // representing the graph as a hashmap of vertext to an arraylist of its edges (vertices it has edges to)
    public static HashMap<String, HashSet<String>> outgoingEdges = new HashMap<String, HashSet<String>>();
    
    public static HashMap<String, HashSet<String>> incomingEdges = new HashMap<String, HashSet<String>>();

    // maps vertex to cost
    public static HashMap<String, Integer> vertexCosts = new HashMap<>();

    // maps vertex to visited status
    public static HashSet<String> visited = new HashSet<>();

    // maps vertex to post time
    public static HashMap<String, Integer> postTimes = new HashMap<>();

    // maps vertex to minCost
    public static HashMap<String, Integer> minCost = new HashMap<>();

    public static ArrayList<Pair> trips = new ArrayList<>();

    public static void main(String[] args) {

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        try {
            // getting all cities/ vertices
            String first = r.readLine();
            int cityNum = Integer.parseInt(first);
            for (int i = 0; i < cityNum; i++) {
                String line = r.readLine();
                String[] info = line.split("\\s+");
                String name = info[0];
                int cost = Integer.parseInt(info[1]);
                // add vertex to graph, with no edges
                outgoingEdges.put(name, new HashSet<String>());
                incomingEdges.put(name, new HashSet<String>());
                vertexCosts.put(name, cost);
            }

            // getting edges in graph
            String edges = r.readLine();
            int edgesNum = Integer.parseInt(edges);
            for (int i = 0; i < edgesNum; i++) {
                String line = r.readLine();
                String[] info = line.split("\\s+");
                String source = info[0];
                String dest = info[1];
                // adding edge to outgoing and ingoing edge trackers
                outgoingEdges.get(source).add(dest);
                incomingEdges.get(dest).add(source);
            }

            // getting trips to test
            String t = r.readLine();
            int tripsNum = Integer.parseInt(t);
            for (int i = 0; i < tripsNum; i++) {
                String line = r.readLine();
                String[] info = line.split("\\s+");
                String source = info[0];
                String dest = info[1];
                // adding trip to trip list
                Pair p = new Pair(source, dest);
                trips.add(p);
            }
        } catch (Exception potato) {
        }
        List<String> topoOrdered = topoSort();

        // get segment of topologically ordered nodes between source and destination node
        findMinCost(topoOrdered);
    }

    // topological sort
    public static List<String> topoSort() {
        // pre time for source starts at 0
        int time = -1;

        // find all source vertices, vertices with no edges into it
        for (String city : incomingEdges.keySet()) {
            if (incomingEdges.get(city).isEmpty() && !visited.contains(city)) {
                time = DFS(city, time);
            }
        }

        ArrayList<Map.Entry<String, Integer>> sorted = new ArrayList<>(postTimes.entrySet());
        Collections.sort(sorted, (a, b) -> b.getValue() - a.getValue());
        return sorted.stream().map(e -> e.getKey()).collect(Collectors.toList());
    }

    // DFS that assigns post time to vertices
    public static int DFS(String vertex, int time) {
        // pre time and marking vertex as visited
        visited.add(vertex);

        // traverse all edges
        for (String edge : outgoingEdges.get(vertex)) {
            if (!visited.contains(edge)) {
                time = DFS(edge, time);
            }
        }

        time++;
        // setting post time
        postTimes.put(vertex, time);
        return time;
    }

    // finding min cost path for each trip
    public static void findMinCost(List<String> ordered) {

        triploop:
        for (Pair trip : trips) {
            String source = trip.x;
            String dest = trip.y;

            if (source.equals(dest)) {
                System.out.println("0");
                continue triploop;
            }
            minCost.clear();
            int currInd = 0;

            // find where the source is in the topologically ordered list, assign all nodes before with inf min cost
            for (int i = 0; i < ordered.size(); i++) {
                // if destination shows up before source in list, it's not a possible trip
                if (ordered.get(i).equals(dest)) {
                    System.out.println("NO");
                    continue triploop;
                }
                if (ordered.get(i).equals(source)) {
                    currInd = i;
                    minCost.put(source, 0);
                    break;
                } else {
                    minCost.put(ordered.get(i), Integer.MAX_VALUE);
                }
            }

            // go through topological ordered list, assigning min cost to each vertex
            // first vertex/source's cost isn't counted, start right after source
            currInd++;
            while (currInd < ordered.size()) {
                String currVertex = ordered.get(currInd);

                // min cost is the min cost of vertices that have an entering edge, plus current vertex cost
                int min = Integer.MAX_VALUE;
                for (String v : incomingEdges.get(currVertex)){
                    if (minCost.get(v) < min) {
                        min = minCost.get(v);
                    }
                }
                // if min is still max val, then no edges were encountered, if this was the dest then it's unreachable
                if (min == Integer.MAX_VALUE && currVertex.equals(dest)) {
                    System.out.println("NO");
                    continue triploop;
                }
                // min cost at this vertex is min of min costs of all edges plus this vertex's cost
                if (min != Integer.MAX_VALUE) {
                    min += vertexCosts.get(currVertex);
                }

                // if destination has been reached, print it and continue to next trip
                if (currVertex.equals(dest)) {
                    System.out.println(min);
                    continue triploop;
                }
                // if last node is reached and destination hasn't occurred, it's not possible
                else if (currInd == ordered.size() - 1) {
                    System.out.println("NO");
                    continue triploop;
                } else {
                    minCost.put(currVertex, min);
                    currInd++;
                }
            }
        }
    }
}

class Pair {
    public String x;
    public String y;

    Pair(String x, String y) {
        this.x = x;
        this.y = y;
    }
}
