import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class UnderTheRainbow {
//    static ArrayList<Integer> distances = new ArrayList<>();
    static int[] distances;
    static int[] nextBest;

    public static void main(String[] args) {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        try {
            String first = r.readLine();
            int hotelNum = Integer.parseInt(first) + 1;

            distances = new int[hotelNum];
            nextBest = new int[hotelNum];

            for (int i = 0; i < hotelNum; i++) {
                Integer hotel = Integer.parseInt(r.readLine());
                distances[i] = hotel;
            }
        }
        catch (Exception e){        }
        findPath();
    }

    public static void findPath(){
        for (int i = 0; i < nextBest.length; i++){
            nextBest[i] = -1;
        }

        int answer = findPath(0);
        System.out.println(answer);
    }

    public static int findPath(int startInd){
        if (startInd == distances.length-1){
            nextBest[startInd] = 0;
            return 0;
        }
        if (nextBest[startInd] != -1){
            return nextBest[startInd];
        }

        int minCost = Integer.MAX_VALUE;
        for (int j = startInd + 1; j < distances.length; j ++){
            int c = 400 - (distances[j] - distances[startInd]);
            int cost = c*c + findPath(j);
            minCost = Math.min(minCost, cost);
        }
        nextBest[startInd] = minCost;
        return minCost;
    }
}
