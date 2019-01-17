import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class NonDecreasing {

    /**
     * Take a rectangular grid of numbers and find the length
     * of the longest sub-sequence.
     * @return the length as an integer.
     */
    public static int longestSequence(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int maxPath = 0;
        boolean[][] visited;

        //finding longest path for every index
        for (int row = 0; row < rows; row ++){
            for (int col = 0; col < cols; col ++){
                visited = new boolean[rows][cols];
                visited[row][col] = true;
                //the starting value must be counted
                int path = 1 + adjacentPaths(grid, row, col, visited);
                if (path > maxPath){
                    maxPath = path;
                }
            }
        }
        //if only the starting value was counted in path, then there's no path satisfying difference greater than 3
        if (maxPath == 1){
            return 0;
        }
        //returns longest path of all indices
        return maxPath;
    }

    public static int adjacentPaths(int[][] grid, int row, int col, boolean[][] visit/*,int [][] maxes*/){
        int rows = grid.length;
        int cols = grid[0].length;
        int val = grid[row][col];
        //saving every initial visited indicator grid so each adjacent path can have "reset"
        boolean[][] firstVisited = visit;
        boolean[][] visited = visit;
        int maxPath = 0;

        //finds paths and compares them to current maxPath for each adjacent index
        if (col + 1 < cols){ //if column to the right exists
            if (Math.abs(grid[row][col + 1]- val) > 3 && visit[row][col+1] == false){ //adjacent to the right
                visited = firstVisited;
                visited[row][col+1] = true;
                int path1 = 1 + adjacentPaths(grid, row, col+1, visited/*, maxes*/);
                if (path1 > maxPath){
                    maxPath = path1;
                }
            }
            if (row + 1 < rows){//if below row exists and right col. exists
                if (Math.abs(grid[row+1][col+1]-val) > 3 && visit[row+1][col+1] == false) {//adjacent to the lower right diagonal
                    visited = firstVisited; //reset visited booleans
                    visited[row + 1][col + 1] = true;
                    int path1 = 1 + adjacentPaths(grid, row + 1, col + 1, visited/*, maxes*/);
                    if (path1 > maxPath) {
                        maxPath = path1;
                    }
                }
            }
            if (row-1 >= 0){ //if above row exists and right col. exists
                if (Math.abs(grid[row-1][col+1]-val) > 3 && visit[row-1][col+1] == false){//adjacent to the upper right diagonal
                    visited = firstVisited;
                    visited[row-1][col+1] = true;
                    int path1 = 1 + adjacentPaths(grid, row-1, col+1, visited/*, maxes*/);
                    if (path1 > maxPath){
                        maxPath = path1;
                    }
                }
            }
        }
        if (row + 1 < rows) {//if below row exists
            if (Math.abs(grid[row + 1][col] - val) > 3 && visit[row+1][col] == false) {//adjacent to the bottom
                visited = firstVisited;
                visited[row + 1][col] = true;
                int path1 = 1 + adjacentPaths(grid, row + 1, col, visited/*, maxes*/);
                if (path1 > maxPath) {
                    maxPath = path1;
                }
            }
        }
        if (row-1 >= 0) { //if above row exists
            if (Math.abs(grid[row - 1][col] - val) > 3 && visit[row-1][col] == false) {//adjacent to the top
                visited = firstVisited;
                visited[row - 1][col] = true;
                int path1 = 1 + adjacentPaths(grid, row - 1, col, visited/*, maxes*/);
                if (path1 > maxPath) {
                    maxPath = path1;
                }
            }
        }
        if (col - 1 >= 0){ //if left column exists
            if (Math.abs(grid[row][col-1]- val) > 3 && visit[row][col-1] == false){ //adjacent to the left
                visited = firstVisited;
                visited[row][col-1] = true;
                int path1 = 1 + adjacentPaths(grid, row, col-1, visited/*, maxes*/);
                if (path1 > maxPath){
                    maxPath = path1;
                }
            }
            if (row + 1 < rows){//if below row exists and left col. exists
                if (Math.abs(grid[row+1][col-1]-val) > 3 && visit[row+1][col-1] == false) {//adjacent to the lower left diagonal
                    visited = firstVisited; //reset visited booleans
                    visited[row+1][col-1] = true;
                    int path1 = 1 + adjacentPaths(grid, row+1, col-1, visited/*, maxes*/);
                    if (path1 > maxPath) {
                        maxPath = path1;
                    }
                }
            }
            if (row - 1 >= 0){//if above row exists and left col. exists
                if (Math.abs(grid[row-1][col-1]-val) > 3 && visit[row-1][col-1] == false) {//adjacent to the upper left diagonal
                    visited = firstVisited; //reset visited booleans
                    visited[row-1][col-1] = true;
                    int path1 = 1 + adjacentPaths(grid, row-1, col-1, visited/*, maxes*/);
                    if (path1 > maxPath) {
                        maxPath = path1;
                    }
                }
            }
        }
        return maxPath;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numRows = 0;
        int numCols = 0;
        String[] firstLine = reader.readLine().split("\\s+");
        numRows = Integer.parseInt(firstLine[0]);
        numCols = Integer.parseInt(firstLine[1]);

        int[][] grid = new int[numRows][numCols];

        for (int row = 0; row < numRows; row++) {
            String[] inputRow = reader.readLine().split("\\s+");

            for (int col = 0; col < numCols; col++) {
                grid[row][col] = Integer.parseInt(inputRow[col]);
            }
        }
        int length = longestSequence(grid);
        System.out.println(length);
    }
}
