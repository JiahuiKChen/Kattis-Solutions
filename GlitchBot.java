import java.util.Scanner;

public class GlitchBot {
    static String changed;

    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);

        int goalX = scan.nextInt();
        int goalY = scan.nextInt();
        scan.nextLine();

        int lines = scan.nextInt();
        scan.nextLine();
        String[] directions = new String[lines];
        //gets all the directions
        for (int i = 0; i < lines; i ++){
            directions[i] = scan.next();
            scan.nextLine();
        }

        //tries other 2 directions for all directions until correct goal is found
        for (int j = 0; j < lines; j ++){
            if (directions[j].equals("Forward")){
                if (tryChange(directions,j, "Right", "Left", goalX, goalY) == true){
                    StringBuilder returned = new StringBuilder();
                    returned.append(j+1).append(' ').append(changed);
                    System.out.println(returned.toString());
                    return;
                }
            }
            else if (directions[j].equals("Right")){
                if (tryChange(directions,j,"Forward", "Left", goalX, goalY) == true){
                    System.out.println(Integer.toString(j + 1) + " " + changed);
                    return;
                }
            }
            else if (directions[j].equals("Left")){
                if (tryChange(directions,j, "Forward", "Right", goalX, goalY) == true){
                    System.out.println(Integer.toString(j + 1) + " " + changed);
                    return;
                }
            }
        }
    }

    public static boolean tryChange(String[] directions, int index, String dir1, String dir2, int goalX, int goalY){
        int x = 0;
        int y = 0;
        //[up right down left]
        boolean[] moves = new boolean[]{true, false, false, false};
        int moveInd = 0;

        //trying first other direction
        String olddir = directions[index];
        directions[index] = dir1;
        for (String dir : directions){
            if (dir.equals("Right")){
                if (moveInd == 3){
                    moves[0] = true;
                    moves[moveInd] = false;
                    moveInd = 0;
                }
                else {
                    moves[moveInd] = false;
                    moves[++moveInd] = true;
                }
            }
            else if (dir.equals("Left")){
                if (moveInd == 0){
                    moves[3] = true;
                    moves[moveInd] = false;
                    moveInd = 3;
                }
                else {
                    moves[moveInd] = false;
                    moves[--moveInd] = true;
                }
            }
            else if (dir.equals("Forward")){
                if (moves[0] == true) {
                    //move up
                    y++;
                }
                else if (moves[1] == true){
                    //move right
                    x ++;
                }
                else if (moves[2] == true){
                    //move down
                    y --;
                }
                else {
                    //move left
                    x--;
                }
            }
        }
        if (x == goalX && y == goalY){
            changed = dir1;
            return true;
        }
        else {
            x = 0;
            y = 0;
            //[up right down left]
            moves = new boolean[]{true, false, false, false};
            moveInd = 0;

            //trying second other direction
            directions[index] = dir2;
            for (String dir : directions){
                if (dir.equals("Right")){
                    if (moveInd == 3){
                        moves[0] = true;
                        moves[moveInd] = false;
                        moveInd = 0;
                    }
                    else {
                        moves[moveInd] = false;
                        moves[++moveInd] = true;
                    }
                }
                else if (dir.equals("Left")){
                    if (moveInd == 0){
                        moves[3] = true;
                        moves[moveInd] = false;
                        moveInd = 3;
                    }
                    else {
                        moves[moveInd] = false;
                        moves[--moveInd] = true;
                    }
                }
                else if (dir.equals("Forward")){
                    if (moves[0] == true) {
                        //move up
                        y++;
                    }
                    else if (moves[1] == true){
                        //move right
                        x ++;
                    }
                    else if (moves[2] == true){
                        //move down
                        y --;
                    }
                    else {
                        //move left
                        x--;
                    }
                }
            }
            if (x == goalX && y == goalY){
                changed = dir2;
                return true;
            }
        }

        //reset to prev direction to not fuk the whole thing
        directions[index] = olddir;
        return false;
    }
}