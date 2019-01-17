import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GetShorty {

    public static void main(String[] args) throws Exception {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        GetShorty sol = new GetShorty();

        while (true) {
            String[] firstLine = r.readLine().split(" ");
            int n = Integer.parseInt(firstLine[0]);
            int m = Integer.parseInt(firstLine[1]);

            if (n == 0 && m == 0) {
                return;
            }

            // representing the graph as a hashmap of vertexes to an arraylist of its exiting edges (vertices it has edges to)
            HashMap<Integer, HashSet<Node>> graph = new HashMap<Integer, HashSet<Node>>();

            for (int i = 0; i < m; i++) {
                String[] line = r.readLine().split(" ");
                int source = Integer.parseInt(line[0]);
                int dest = Integer.parseInt(line[1]);
                double weight = Double.parseDouble(line[2]);

                if (!graph.containsKey(source)){
                    graph.put(source, new HashSet<>());
                }
                graph.get(source).add(new Node(dest, weight));

                if (!graph.containsKey(dest)){
                    graph.put(dest, new HashSet<>());
                }
                graph.get(dest).add(new Node(source, weight));
            }

            System.out.println(String.format("%5.4f", sol.dijkstra(0, n - 1, graph)));
        }

    }

    private double dijkstra(int start, int end, HashMap<Integer, HashSet<Node>> graph) throws Exception {
        PriorityQueue<Node> q = new PriorityQueue<>(1, (l, r) -> {
            if (l.factor < r.factor) {
                return 1;
            } else {
                return -1;
            }
        });

        HashSet<Integer> visited = new HashSet<>();
        HashMap<Integer, Double> maxSoFar = new HashMap<>();

        q.add(new Node(start, 1));
        maxSoFar.put(start, 1.0);

        while (!q.isEmpty()) {
            Node curr = q.remove();

            if (visited.contains(curr.id)) {
                continue;
            }
            visited.add(curr.id);

            if (curr.id == end) {
                return curr.factor;
            }

            for (Node n : graph.get(curr.id)) {
                if (!visited.contains(n.id)) {
                    double factor = curr.factor * n.factor;

                    if (!maxSoFar.containsKey(n.id) || maxSoFar.get(n.id) < factor) {
                        q.add(new Node(n.id, factor));
                        maxSoFar.put(n.id, factor);
                    }
                }
            }
        }

        throw new Exception("done fucked up");
    }
}

class Node {
    public double factor;
    public int id;

    public Node(int id, double factor) {
        this.id = id;
        this.factor = factor;
    }
}
