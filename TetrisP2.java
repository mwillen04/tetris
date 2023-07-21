//import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
//import java.util.*;

public class TetrisP2 extends Tetris {
    public TetrisP2(Color c, Preview pr, Preview hold, int w, int h, int lvl) {
        super(c, pr, hold, w, h, lvl);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (b == null) return;
        if (e.getKeyCode() == KeyEvent.VK_A) {
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
        else if (e.getKeyCode() == KeyEvent.VK_D) {
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
        else if (e.getKeyCode() == KeyEvent.VK_W) {
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
        else if (e.getKeyCode() == KeyEvent.VK_S) {
            for (Point p : b.getPoints()) {
                if (p.y == (getHeight()-20)/20 || (board[p.y+1][p.x] != null && board[p.y+1][p.x] != b)) return;
            }
            replaceBlock(b,null);
            b.update();
            replaceBlock(b,b);
            repaint();
        }
        else if (e.getKeyCode() == KeyEvent.VK_E) {
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
        else if (e.getKeyCode() == KeyEvent.VK_Q) {
            replaceBlock(b,null);
            Block b1 = b;
            b = hold.getBlock();
            hold.setBlock(new Block(getWidth(),b1.getShape()));
            if (b != null) replaceBlock(b,b);
            repaint();
        }
    }
}