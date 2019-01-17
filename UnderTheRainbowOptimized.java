import java.io.BufferedReader;
import java.io.InputStreamReader;

// bottom up implementation!!! No recursion!!
public class UnderTheRainbowUltra {
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

        nextBest[nextBest.length-1] = 0;
        for (int i = nextBest.length - 2; i >= 0; i --){
            int minCost = Integer.MAX_VALUE;

            for (int j = i + 1; j < distances.length; j ++){
                int c = 400 - (distances[j] - distances[i]);
                int cost = c*c + nextBest[j];
                minCost = Math.min(minCost, cost);
            }
            nextBest[i] = minCost;
        }

        System.out.println(nextBest[0]);
    }
}
