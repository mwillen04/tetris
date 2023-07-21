import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
//import java.util.*;

/*
The Tetris class is the primary bulk of the code that runs the game. The board array contained here is one of the most vital parts of the game. Essentially, it serves as a map of the entire board, where each coordinate is either empty or is linked to the shape that is currently on that coordinate. This is what stops pieces from overlapping and allows rows to be cleared.
*/

public class Tetris extends JPanel implements ActionListener, KeyListener {
    protected Block[][] board;
    private javax.swing.Timer timer;
    protected Block b;
    protected Preview pr, hold;
    private static int highscore = 0;
    private int score, rows, level;
    private boolean tetrisLast, hasStarted, tempStop, gameOver, fromHold;

    // Creates the tetris board using the arguments provided when called in the Game class
    public Tetris(Color c, Preview pr, Preview hold, int w, int h, int lvl) {
        board = new Block[h/20][w/20];
        this.pr = pr;
        this.hold = hold;
        level = lvl;

        // 5 blocks are immediately added to the deck - when the top is used a new one is added
        // Essentially, the next 5 blocks are known by the code at all times
        for (int k = 0; k < 5; k++) {
            pr.setBlock(new Block(w));
        }

        hold.setBlock(null);
        setBackground(c);
        setBorder( BorderFactory.createLineBorder(Color.BLACK) );
        timer = new javax.swing.Timer( 400-25*(level-1), this );
        setFocusable(true);
        addKeyListener(this);
    }

    // Starts the game. Line 43 prevents a game being continued by clicking start once you've lost.
    public void start() {
        if (hasStarted && !tempStop) return;
        hasStarted = true;
        tempStop = false;
        timer.start();
        requestFocusInWindow();
    }

    // Pauses the game
    public void stop() {
        timer.stop();
        tempStop = true;
    }
    
    public static int getHScore() {
        return highscore;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getRows() {
        return rows;
    }
    public int getLevel() {
        return level;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public boolean hasGameStarted() {
        return hasStarted;
    }

    // Uses the board array to graphically display the tetris board.
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int k = 0; k < board.length; k++) {
            for (int h = 0; h < board[0].length; h++) {
                if (board[k][h] != null) {
                    g.setColor(board[k][h].getColor());
                    g.fillRect(h*20,k*20,20,20);
                    g.setColor(Color.BLACK);
                    g.drawRect(h*20,k*20,20,20);
                }
            }
        }
        
    }

    // This method is the regular action of the game and is run each time the timer is updated.
    public void actionPerformed(ActionEvent e) {
      
        // Checks if a block is currently in play
        // If not, it checks if any rows have been completed, then puts a new block in play
        if (b == null) {
            int complete = 0;
            for (int k = 0; k < board.length; k++) {
                if (isComplete(board[k])) {
                    for (int h = 0; h < board[k].length; h++) {
                        board[k][h] = null;
                    }
                    for (int j = k-1; j >= 0; j--) {
                        for (int h = 0; h < board[k].length; h++) {
                            board[j+1][h] = board[j][h];
                        }
                    }
                    rows++;
                    complete++;
                }
            }
            score += updateScore(complete);
            for (int k = 1; k <= 10; k++) {
                if (score >= (k-1)*2000) {
                    level = k;
                }
            }
            timer.setDelay(400-25*(level-1));
            if (score > highscore) highscore = score;
            repaint();
            b = pr.getBlock(); // Puts a block from the deck in play
            pr.setBlock(new Block(getWidth())); // Adds a new block to the deck
            fromHold = false;
            for (Point p : b.getPoints()) {
                if (p.y >= 0) { 
                    if (board[p.y][p.x] != null) b.moveUp();
                }
            }
            replaceBlock(b,b);
            repaint();
            return;
        }
        // If a piece reaches the bottom limit (based on where there are already other pieces), the block in play becomes null and the code checks if the board has reached game over conditions
        for (Point p : b.getPoints()) {
            if (p.y < -1) continue;
            if (p.y == (getHeight()-20)/20) {
                if (p.y <= 0) {
                    timer.stop();
                    gameOver = true;
                }
                b = null;
                return;
            }
            if (board[p.y+1][p.x] != null && board[p.y+1][p.x] != b) {
                if (p.y <= 0) {
                    timer.stop();
                    gameOver = true;
                }
                b = null;
                return;
            }
        }
        // If the above is all false, the current piece is moved down one space
        replaceBlock(b,null);
        b.update();
        replaceBlock(b,b);
        repaint();
    }

    // Checks whether a row on the board has been completed.
    private boolean isComplete(Block[] bl) {
        for (int k = 0; k < bl.length; k++) {
            if (bl[k] == null) {
                return false;
            }
        }
        return true;
    }

    // If a row has been completed, the score is updated using a scoring system close to the official Tetris scoring.
    private int updateScore(int c) {
        int n = 0;
        if (c == 1) n = 100;
        if (c == 2) n = 300;
        if (c == 3) n = 500;
        if (c == 4 && tetrisLast) return 1200;
        if (c == 4) { tetrisLast = true; return 800; }
        tetrisLast = false;
        return n;
    }

    // This method is used to clear the board of a block in its current position before also being used to re-add it in a new position after it is moved.
    protected void replaceBlock(Block b1, Block b2) {
        for (Point p : b1.getPoints()) {
            if (p.y >= 0) {
                board[p.y][p.x] = b2;
            }
        }
    }

    // Important method for recognizing keyboard inputs used to move blocks. When called, it will check which key was pressed and move the block accordingly.
    public void keyPressed(KeyEvent e) {
        if (b == null) return; // when no block is in play, nothing is done when a button is pressed
        
        // if possible, the block is moved one space to the left
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            for (Point p : b.getPoints()) {
                if (p.y >= 0) {
                    if (p.x == 0 || (board[p.y][p.x-1] != null && board[p.y][p.x-1] != b)) return;
                }
            }
            replaceBlock(b,null);
            b.moveLeft();
            replaceBlock(b,b);
            repaint();
        }
        // if possible, the block is moved one space to the right
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            for (Point p : b.getPoints()) {
                if (p.y >= 0) {
                    if (p.x == (getWidth()-20)/20 || (board[p.y][p.x+1] != null && board[p.y][p.x+1] != b)) return;
                }            
            }
            replaceBlock(b,null);
            b.moveRight();
            replaceBlock(b,b);
            repaint();
        }
          
        // if possible, the block is rotated
        else if (e.getKeyCode() == KeyEvent.VK_UP) {
            Point[] p2 = b.testRotate();
            for (Point p : p2) {
                if (p.x < 0 || p.x > (getWidth()-20)/20 || p.y >= getHeight()/20) return;
                if (p.y < 0) continue;
                if (board[p.y][p.x] != null && board[p.y][p.x] != b) return;
            }
            replaceBlock(b,null);
            b.rotate();
            replaceBlock(b,b);
            repaint();
        }
          
        // if possible, the block is moved one space down
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            for (Point p : b.getPoints()) {
                if (p.y == (getHeight()-20)/20 || (board[p.y+1][p.x] != null && board[p.y+1][p.x] != b)) return;
            }
            replaceBlock(b,null);
            b.update();
            replaceBlock(b,b);
            repaint();
        }

        // The block is immediately dropped straight down in its current position. 
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            outer: while (true) {
                for (Point p : b.getPoints()) {
                    if (p.y == (getHeight()-20)/20 || (board[p.y+1][p.x] != null && board[p.y+1][p.x] != b)) break outer;
                }
                replaceBlock(b,null);
                b.update();
                replaceBlock(b,b); 
            } 
            repaint();
        }

        // Moves the block into the hold
        // If there is a block currently in hold, that block is put into play
        // If hold is empty, the next block in the deck is put into play
        // Includes a check to make sure you're not flipping the same two pieces in and out of hold
        else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            if (fromHold) return;
            replaceBlock(b,null);
            Block b1 = b;
            b = hold.getBlock();
            hold.setBlock(new Block(getWidth(),b1.getShape()));
            if (b != null) replaceBlock(b,b);
            fromHold = true;
            repaint();
        }
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
}