import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/*
 * ALG:
 * from t to 0:
 *      find all people with time of t or greater, get the max deposit of all those people.
 *      Add the max deposit, delete the person.
 */
public class BankQueue {
    static TreeMap<Integer, ArrayList<Integer>> timeAmountMap = new TreeMap<>();

    public static void main(String[] args) {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        try {
            String[] first = r.readLine().split(" ");
            int peopleNum = Integer.parseInt(first[0]);
            int totalTime = Integer.parseInt(first[1]);

            for (int i = 0; i < peopleNum; i ++){
                String[] info = r.readLine().split(" ");
                Integer time = Integer.parseInt(info[1]);
                Integer amount = Integer.parseInt(info[0]);

                if (!timeAmountMap.containsKey(time)){
                    timeAmountMap.put(time, new ArrayList<Integer>());
                }
                timeAmountMap.get(time).add(amount);
            }
            findMaxAmount(totalTime);
        }
        catch (Exception e){        }
    }

    public static void findMaxAmount(int time){
        int amount = 0;

        for (int t = time - 1; t >= 0; t--){
            if (timeAmountMap.isEmpty()){
                System.out.println(amount);
                return;
            }

            SortedMap<Integer, ArrayList<Integer>> greaterTimes = timeAmountMap.tailMap(t);
            if (greaterTimes.size() == 0){ continue; }
            int maxDeposit = Integer.MIN_VALUE;
            int maxDepositKey = 0;
            for (Integer k : greaterTimes.keySet()){
                if (greaterTimes.get(k).size() == 0) {continue;}
                int timeMax = Collections.max(greaterTimes.get(k));
                if (timeMax > maxDeposit){
                    maxDeposit = timeMax;
                    maxDepositKey = k;
                }
            }
            if (maxDeposit == Integer.MIN_VALUE) {continue;}

            // ensure the element at index of maxDeposit isn't being removed...
            timeAmountMap.get(maxDepositKey).remove(Integer.valueOf(maxDeposit));
            amount += maxDeposit;
        }
        System.out.println(amount);
    }
}
