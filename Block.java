import java.awt.*;

/*
The Block class establishes all of the important methods and properties for each tetris piece. Tetris pieces are objects of the Block class and the methods below detail what can be done with these pieces.
*/

public class Block {
    private Point[] b = new Point[4];
    private Color c;
    private int shape, rotation;

    // The shape is randomly chosen from the 7 available options detailed in the BlockProperties class
    // Essentially, BlockProperties contains the piece possibilities while Block is the piece itself
    // The Block takes boardWidth as an argument so it knows the limits of where it can rotate
    // The Point array maps the Block on the board using the coordinates of the four squares of the Block
    public Block(int boardWidth) {
        shape = (int)(7*Math.random());
        for (int k = 0; k < 4; k++) {
            b[k] = new Point(BlockProperties.getTetromino(shape)[2*k],BlockProperties.getTetromino(shape)[2*k+1]);
        } 
        c = BlockProperties.getColor(shape);
    }

    // This is just an alternative constructor where you can choose the shape beforehand
    public Block(int boardWidth, int shape) {
        this.shape = shape;
        for (int k = 0; k < 4; k++) {
            b[k] = new Point(BlockProperties.getTetromino(shape)[2*k],BlockProperties.getTetromino(shape)[2*k+1]);
        }
        c = BlockProperties.getColor(shape);
    }

    // Returns the shape of the Block
    public int getShape() {
        return shape;
    }

    // Returns the color of the Block
    public Color getColor() {
        return c;
    }

    // Returns the array of coordinates of each square segment of the Block
    public Point[] getPoints() {
        return b;
    }

    // The regular updating of the block's position - moves it down one space on each tick
    public void update() {
        for (Point p : b) {
            p.y += 1;
        }
    }

    // Moves the block one space to the left when called
    public void moveLeft() {
        for (Point p : b) {
            p.x -= 1;
        }
    }
  
    // Moves the block one space to the right when called
    public void moveRight() {
        for (Point p : b) {
            p.x += 1;
        }
    }

    // Moves the block one space up when called
    public void moveUp() {
        for (Point p : b) {
            p.y -= 1;
        }
    }

    // Checks if rotating the block is possible (based on the edges/other pieces) before doing it
    public Point[] testRotate() {
        if (shape == 6) return b;
        Point[] pts = new Point[4];
        for (int k = 0; k < b.length; k++) {
            pts[k] = new Point(b[k].x,b[k].y);
        }
        for (int k = 1; k < pts.length; k++) {
            pts[k].x += BlockProperties.getRotation(shape)[rotation][k-1][0];
            pts[k].y += BlockProperties.getRotation(shape)[rotation][k-1][1];
        }
        return pts;
    }

    // Rotates the block using the rotation matrix in BlockProperties
    public void rotate() {
        for (int k = 1; k < b.length; k++) {
            b[k].x += BlockProperties.getRotation(shape)[rotation][k-1][0];
            b[k].y += BlockProperties.getRotation(shape)[rotation][k-1][1];
        }
        rotation++;
        if (rotation == 4) rotation = 0;
    }

    // Not needed in tetris but good to have; checks if two different blocks are the same shape.
    public boolean equals(Object obj) {
        if (!(obj instanceof Block)) return false;
        Block b2 = (Block) obj;
        return (this.getShape() == b2.getShape());
    }
}