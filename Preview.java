import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
import java.util.*;

/*
The Preview class is essentially a holding space for tetris pieces. It contains a list of blocks that pieces can be added to and removed from using the provided methods. Graphically, it displays the blocks in descending order within the bounds set for it in the Game class, so the bounds determine how many pieces are displayed in the Preview.
*/

public class Preview extends JPanel {
    private LinkedList<Block> bList = new LinkedList<Block>();
    
    public Preview(Color c) {
        setBackground(c);
        setBorder( BorderFactory.createLineBorder(Color.BLACK) );
    }

    // Adds a block to the list
    public void setBlock(Block b) {
        if (b == null) return;
        bList.addLast(b);
        repaint();
    }

    // Removes and returns the first block in the list
    public Block getBlock() {
        if (bList.isEmpty()) return null;
        return bList.removeFirst();
    }

    // Creates the graphical element of the Preview class
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bList.isEmpty()) return;
        int k = 0;
        for (Block b : bList) { 
            for (Point p : b.getPoints()) {
                g.setColor(b.getColor());
                g.fillRect(p.x*20-40,p.y*20+20+k,20,20);
                g.setColor(Color.BLACK);
                g.drawRect(p.x*20-40,p.y*20+20+k,20,20);
            }
            k += 60;
        }
    }
}