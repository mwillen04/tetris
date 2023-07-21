import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
Game is the sole object run in the main class - it includes the base setup of the screen, buttons, and board regions. It also includes calls to the classes that setup and run each part of the game.
*/

public class Game implements ActionListener {
    private JFrame jf = new JFrame();
    private JPanel jp = new JPanel();
    private Tetris board;
    private Preview deck = new Preview(Color.WHITE);
    private Preview hold = new Preview(Color.WHITE);
    private JButton btnStart = new JButton("Start");        // Starts/resumes the game
    private JButton btnStop = new JButton("Stop");          // Pauses the game
    private JButton btnReset = new JButton("Reset");        // Resets the board
    private JLabel lblHScore = new JLabel("High Score: 0"); // High score across consecutive games played
    private JLabel lblScore = new JLabel("Score: 0");       // Current score
    private JLabel lblRows = new JLabel("Rows: 0");         // Number of rows completed
    private JLabel lblLevel = new JLabel("Level: 1");       // Level changes at preset point increments
    private JLabel lblDesc = new JLabel("", SwingConstants.CENTER);
    private javax.swing.Timer timer;
    private int width, height;

    public Game(int w, int h){
        jf.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        jf.setTitle("Tetris");
        
        jp.setBackground(Color.PINK);
        jp.setPreferredSize(new Dimension(w+330,500));
        jp.setLayout(null);

        // w & h are determined when calling the Game class in Main
        // If changed, the tetris board will updated to fit the given dimensions.
        width = w;
        height = h;
      
        // board is the actual tetris game that is instantiated here - the tetris class better shows what each     argument corresponds to.
        board = new Tetris( Color.WHITE, deck, hold, width, height, 7);
        board.setBounds(10, 10, width, height);

        // deck and hold are both Preview objects - deck contains the upcoming pieces, while hold allows you to hold one piece at a time for later use. Line 45 is an alternative deck that lets you see the next 5 pieces rather than the next 1 piece - if you want to use it you'll also have to change the commented bit on line 95.
      
        deck.setBounds(width+50,280,120,80);
        //deck.setBounds(width+190,30,120,320);
        hold.setBounds(width+50,180, 120,80);

        // Lines 49-70 just map the locations of each button/label and add them to the game panel.
        btnStart.setBounds(width+50, 10, 100, 20);
        btnStop.setBounds(width+50, 40, 100, 20);
        btnReset.setBounds(width+50, 70, 100, 20);
        lblHScore.setBounds(width+50,100, 150, 20);
        lblScore.setBounds(width+50,120,100,20);
        lblRows.setBounds(width+50,140,100,20);
        lblLevel.setBounds(width+50,160,100,20);
        lblDesc.setBounds(width+50,260,120,20);
        lblDesc.setOpaque(false);
        lblDesc.setBackground(Color.WHITE);
        
        jp.add( board );
        jp.add( deck );
        jp.add( hold );
        jp.add(btnStart);
        jp.add(btnStop);
        jp.add(btnReset);
        jp.add(lblHScore);
        jp.add(lblScore);
        jp.add(lblRows);
        jp.add(lblLevel);
        jp.add(lblDesc);

        timer = new javax.swing.Timer( 25, this );
        timer.start();
        
        btnStart.addActionListener(this);
        btnStop.addActionListener(this);
        btnReset.addActionListener(this);

        jf.getContentPane().add( jp );
        jf. pack();
    }

    // Determines which button on the board was clicked and proceeds accordingly
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnStart)) board.start();
        else if (e.getSource().equals(btnStop)) board.stop();
        else if (e.getSource().equals(btnReset)) {
            if (board.hasGameStarted()) {
                // Stops the game and replaces the board and deck/hold with completely new ones
                board.stop();
                jp.remove(board);
                jp.remove(deck);
                jp.remove(hold);
                deck = new Preview( Color.WHITE );
                deck.setBounds(width+50,280,120,80);
                //deck.setBounds(width+190,30,120,320);
                jp.add(deck);
                hold = new Preview( Color.WHITE );
                hold.setBounds(width+50,180, 120,80);
                jp.add(hold);
                board = new Tetris( Color.WHITE, deck, hold, width, height,1);
                board.setBounds(10, 10, width, height);
                jp.add(board);
                lblDesc.setOpaque(false);
                lblDesc.setBorder(BorderFactory.createEmptyBorder());
                lblDesc.setText("");
                board.revalidate();
                board.repaint();
                deck.revalidate();
                deck.repaint();
                hold.revalidate();
                hold.repaint();
            }
        }
        lblHScore.setText("Highscore: " + Tetris.getHScore());
        lblScore.setText("Score: " + board.getScore());
        lblRows.setText("Rows: " + board.getRows());
        lblLevel.setText("Level: " + board.getLevel());
        if (board.isGameOver()) {
            lblDesc.setOpaque(true);
            lblDesc.setBorder( BorderFactory.createLineBorder(Color.BLACK) );
            lblDesc.setText("You Lose!");
        }
    }

    public void display() {
        EventQueue.invokeLater(new Runnable() {
                public void run() {
                    jf.setVisible(true);
                }
            });
    }
}