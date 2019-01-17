import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class NarrowArtGallery {
    static int[][] values;
    // DP [row][k+1][-1, 0, 1]    0 corresponds to -1 (close either or neither door), 1 to 0 (keep door 0 open), 2 to 1 (keep door 1 open)
    static int[][][] closeVals;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer k = null;

        String[] first = reader.readLine().split(" ");
        int rows = Integer.parseInt(first[0]);
        k = Integer.parseInt(first[1]);
        values = new int[rows][2];
        closeVals = new int[rows][k + 1][3];

        for (int i = 0; i < rows; i++) {
            String[] r = reader.readLine().split(" ");
            values[i][0] = Integer.parseInt(r[0]);
            values[i][1] = Integer.parseInt(r[1]);
        }

        for (int row = values.length - 1; row >= 0; row--) {
            for (int kInd = 0; kInd <= k; kInd++) {
                for (int restraint = 0; restraint < 3; restraint++) {
                    if (row == values.length - 1) {
                        if (kInd == 0) {
                            closeVals[row][kInd][restraint] = values[row][0] + values[row][1];
                        } else {
                            if (restraint == 0) {
                                closeVals[row][kInd][restraint] = Math.max(values[row][0], values[row][1]);
                            } if (restraint == 1) {
                                closeVals[row][kInd][restraint] = values[row][0];
                            } else if (restraint == 2) {
                                closeVals[row][kInd][restraint] = values[row][1];
                            }
                        }
                    } else if (kInd == 0) {
                        closeVals[row][kInd][restraint] = values[row][0] + values[row][1] + closeVals[row + 1][kInd][0];
                    } else if (kInd == values.length - row) {
                        if (restraint == 0) {
                            closeVals[row][kInd][restraint] = Math.max(values[row][0] + closeVals[row + 1][kInd - 1][1], values[row][1] + closeVals[row + 1][kInd - 1][2]);
                        } else if (restraint == 1) {
                            closeVals[row][kInd][restraint] = values[row][0] + closeVals[row + 1][kInd - 1][1];
                        } else if (restraint == 2) {
                            closeVals[row][kInd][restraint] = values[row][1] + closeVals[row + 1][kInd - 1][2];
                        }
                    } else {// k < rowTotal - row
                        if (restraint == 0) {
                            closeVals[row][kInd][restraint] = Math.max(values[row][0] + values[row][1] + closeVals[row + 1][kInd][0],
                                    Math.max(values[row][0] + closeVals[row + 1][kInd - 1][1],
                                            values[row][1] + closeVals[row + 1][kInd - 1][2]));
                        } else if (restraint == 1) {
                            closeVals[row][kInd][restraint] = Math.max(values[row][0] + values[row][1] + closeVals[row + 1][kInd][0],
                                    values[row][0] + closeVals[row + 1][kInd - 1][1]);
                        } else if (restraint == 2) {
                            closeVals[row][kInd][restraint] = Math.max(values[row][0] + values[row][1] + closeVals[row + 1][kInd][0],
                                    values[row][1] + closeVals[row + 1][kInd - 1][2]);
                        }
                    }
                }
            }
        }

        int answer = Math.max(closeVals[0][k][0], Math.max(closeVals[0][k][1], closeVals[0][k][2]));
        System.out.println(answer);
    }
}
