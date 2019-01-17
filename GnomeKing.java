import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Answer/solution to oddgnome problem in Kattis
public class GnomeKing {

    public void findGnomeKing()/*static void main (String[] args)*/{
        try {
            System.setIn(new FileInputStream(("sample01.in")));
        }
        catch (FileNotFoundException e){
            System.out.print("File not found!");
        }
        Scanner scan = new Scanner(System.in);

        //////////////////////////////Submitted Solution
        //scans number of gnome groups
        int groups = Integer.parseInt(scan.nextLine());

        //finds king of each group
        for (int i = 0; i < groups; i ++){

            int gnomeCount = scan.nextInt();
            int prev = scan.nextInt();
            int after = scan.nextInt();

            for (int gnomeIndex = 2; gnomeIndex <= gnomeCount; gnomeIndex++){

                if (after - prev != 1){
                    System.out.println(gnomeIndex);
                    scan.nextLine();
                    break;
                }
                prev = after;
                after = scan.nextInt();
            }
        }
    }
}
