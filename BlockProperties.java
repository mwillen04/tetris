import java.awt.*;

/*
The BlockProperties class provides the basic properties of each basic tetris shape. Each property is contained in an array, where any given index number corresponds to the same piece across each array.

----------------------------------------------------
Index || Piece           || Official Name
----------------------------------------------------
0     || Straight Line   || (Hero)
1     || Tee             || (Teewee)
2     || Forward L       || (Orange Ricky)
3     || Backwards L     || (Blue Ricky) 
4     || Backwards Z     || (Cleveland Z)
5     || Forward Z       || (Rhode Island Z)
6     || Square          || (Smashboy)
----------------------------------------------------
*/

public class BlockProperties {

    // Coordinates of the four segments of the pieces
    private static final int[][] TETROMINOES = {
        {4,0,3,0,5,0,6,0},
        {4,0,3,0,5,0,4,1},
        {4,0,3,0,5,0,3,1},
        {4,0,3,0,5,0,5,1},
        {4,0,5,0,4,1,3,1},
        {4,0,3,0,4,1,5,1},
        {4,0,5,0,4,1,5,1}
    };

    // Change in coordinate for each rotation (rotation matrix)
    private static final int[][][][] ROTATIONS = {
        {{{1,-1},{-1,1},{-2,2}},{{-1,1},{1,-1},{2,-2}},{{1,-1},{-1,1},{-2,2}},{{-1,1},{1,-1},{2,-2}}},
        {{{1,-1},{-1,1},{-1,-1}},{{1,1},{-1,-1},{1,-1}},{{-1,1},{1,-1},{1,1}},{{-1,-1},{1,1},{-1,1}}},
        {{{1,-1},{-1,1},{0,-2}},{{1,1},{-1,-1},{2,0}},{{-1,1},{1,-1},{0,2}},{{-1,-1},{1,1},{-2,0}}},
        {{{1,-1},{-1,1},{-2,0}},{{1,1},{-1,-1},{0,-2}},{{-1,1},{1,-1},{2,0}},{{-1,-1},{1,1},{0,2}}},
        {{{-1,-1},{1,-1},{2,0}},{{1,1},{-1,1},{-2,0}},{{-1,-1},{1,-1},{2,0}},{{1,1},{-1,1},{-2,0}}},
        {{{1,1},{1,-1},{0,-2}},{{-1,-1},{-1,1},{0,2}},{{1,1},{1,-1},{0,-2}},{{-1,-1},{-1,1},{0,2}}}
    };

    // Color of each piece
    private static final Color[] COLORS = {
      Color.CYAN,
      Color.MAGENTA,
      new Color(255, 165, 0),
      Color.BLUE,
      Color.GREEN,
      Color.RED,
      Color.YELLOW
    };

    // Returns the tetromino coordinate for the provided shape number
    public static int[] getTetromino(int shape) {
        return TETROMINOES[shape];
    }

    // Returns the rotation matrix for the provided shape number
    public static int[][][] getRotation(int shape) {
        return ROTATIONS[shape];
    }

    // Returns the color for the provided shape number
    public static Color getColor(int shape) {
        return COLORS[shape];
    }
}