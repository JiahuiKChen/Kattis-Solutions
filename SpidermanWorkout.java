import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class SpidermanWorkout {
    static int[][] memo = new int[40][1001];
    static String[][] path = new String[40][1001];

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        int tests = Integer.parseInt(r.readLine());

        for (int i = 0; i < tests; i ++) {
            // parse each test scenario, and get min path for it
            int steps = Integer.parseInt(r.readLine());
            String[] dists = r.readLine().split(" ");
            int[] distances = new int[steps];
            for (int d = 0; d < steps; d ++) {
                distances[d] = Integer.parseInt(dists[d]);
            }

            // clear memo and path
            for (int row = 0; row < 40; row ++){
                Arrays.fill(memo[row], -1);
                Arrays.fill(path[row], "");
            }

            // find min path for distances
            int answer = findMinHeight(distances, 0, 0);
            if (answer == Integer.MAX_VALUE){
                System.out.println("IMPOSSIBLE");
            }
            else {
                StringBuilder s = new StringBuilder();
                int height = 0;
                for (int step = 0; step < steps; step++){
                    s.append(path[step][height]);
                    if (path[step][height] == "U") {
                        height += distances[step];
                    }
                    else {
                        height -= distances[step];
                    }
                }
                System.out.println(s.toString());
            }

        }
    }

    public static int findMinHeight(int[] distances, int distIndex, int height){
        // invalid solution
        if (height < 0){
            return Integer.MAX_VALUE;
        }
        // base case, last distance has been processed
        if (distIndex == distances.length){
            if (height == 0){
                return height;
            }
            return Integer.MAX_VALUE;
        }
        if (memo[distIndex][height] != -1){
            return memo[distIndex][height];
        }

        // try going up and down
        int down = findMinHeight(distances, distIndex+1, height-distances[distIndex]);
        int up = findMinHeight(distances, distIndex+1, height+distances[distIndex]);
        int min = 0;
        if (down < up){
            min = down;
            path[distIndex][height] = "D";
        }
        else {
            min = up;
            path[distIndex][height] = "U";
        }

        memo[distIndex][height] = Math.max(min, height);
        return memo[distIndex][height];
    }
}