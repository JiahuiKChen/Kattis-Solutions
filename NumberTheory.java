import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.Math;

public class NumberTheory {
    public static void main (String[] args){
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String> lines = new ArrayList<>();

        try {
            String l = r.readLine();

            while (l != null && !l.isEmpty()){
                lines.add(l);
                l= r.readLine();
            }

            for (String line : lines){
                String[] parts = line.split(" ");
                String category = parts[0];

                if (category.equals("gcd")){
                    System.out.println(gcd(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
                }
                else if (category.equals("exp")){
                    modExpDriver(Long.parseLong(parts[1]), Long.parseLong(parts[2]), Long.parseLong(parts[3]));
                }
                else if (category.equals("inverse")){
                    inverse(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), true);
                }
                else if (category.equals("isprime")){
                    isPrime(Integer.parseInt(parts[1]));
                }
                else if (category.equals("key")){
                    rsa(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                }
            }
        }
        catch (Exception e){
        }
    }

    public static long gcd(long a, long b){
        while (b > 0){
            long aMod = a % b;
            a = b;
            b = aMod;
        }
        return a;
    }

    public static long modExp(long x, long y, long N){
        if (y == 0){
           return 1;
           // return 1%N;
        }
        else {
            long z = modExp(x, Math.abs(y/2), N);
            if (y % 2 == 0){
                return ((z % N) * (z % N)) % N;
                //return (long)((Math.pow(z, 2)) % N);
            }
            else {
                return (((x % N) * (z % N) % N) * (z % N)) % N;
                //return (long)((x * Math.pow(z, 2)) % N);
            }
        }
    }

    public static void modExpDriver(long x, long y, long N){
        long answer = modExp(x, y, N);
        System.out.println(answer);
    }

    public static long inverse(long a, long N, boolean print){
        long[] eeNums = extendedEuclid(a, N);
        if (eeNums[2] == 1){
            long inv =  eeNums[0] % N;

            if (inv < 0){
                inv += N;
            }
            if (print){System.out.println(inv);}
            return inv;
        }
        else {
            if (print) {System.out.println("none");}
            return 0;
        }
    }

    public static long[] extendedEuclid(long a, long b){
        if (b == 0){
            long[] base = {1, 0, a};
            return base;
        }
        else {
            long[] e = extendedEuclid(b, a%b);
            long y = e[1];
            long mid = e[0] - (a/b)*y;
            long d = e[2];
            return new long[] {y, mid, d};
        }
    }

    public static void isPrime(int x){
        long[] tests = {2, 3, 5};

        for (long a : tests){
            if (modExp(a, x-1, x) != (long)1){
                System.out.println("no");
                return;
            }
        }
        System.out.println("yes");
    }

    public static void rsa(long p, long q){
        long N = p*q;
        long totient = (p-1)*(q-1);

        long e = 2;
        while (true){
            if (gcd(e, totient) == 1){
                break;
            }
            else {
                e++;
            }
        }

        long d = inverse(e, totient, false);
        // d = 1
//        while (true){
//            if ((e*d)%totient == 1){
//                break;
//            }
//            else {
//                d ++;
//            }
//        }
        System.out.println(N + " " + e + " " + d);
    }
}
