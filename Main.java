/*
TETRIS
----------------------------------------------------
Michael Willen
Methacton High School '22 || Yale University '26
mnw2229@gmail.com
----------------------------------------------------

Hi!! Thanks for checking out my tetris code. This was made when I was bored on a Thursday night at the end of 11th grade and I've been improving it incrementally ever since. Admittedly I didn't really add any comments/documentation when making it so I'm trying to add them now to make things clearer.
*/

public class Main {
    public static void main(String[] args) {
        Game g = new Game(200, 360);
        g.display();
    }
}