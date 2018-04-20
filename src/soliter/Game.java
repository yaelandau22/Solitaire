package soliter;

import javax.swing.JFrame;

/**
 * <h1>This class is an abstract class, extends JFrame and implement {@link Playable} interface<h1>
 * It contains the game's name.
 * 
 * @see Playable
 * @see JFrame
 * @see {@linkplain <a href=http://docs.oracle.com/javase/tutorial/java/IandI/abstract.html </a> abstract class}
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public abstract class Game extends JFrame implements Playable
{    
    /**
     * <b>Class's constructor</b><br> Sets the game's name.
     * Used for the game's frame, there it will be displayed.
     * @param gameName the game's name.
     */
    public Game(String gameName) //sets the game name
    {
        super(gameName);
    }
    
}
