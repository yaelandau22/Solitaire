
package soliter;

/**
 * <h1>This is the main class. </h1>
 * This class boot the game.
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class SoliterRunner 
{
    /** 
     * This class boot the game, but not before creating the game's clock, 
     * activating it, and creating the GameRunner object, who boot the game, 
     * by the method {@link GameRunner#play()}.
     * @param args contains the supplied command-line arguments as an array of String objects.
     * @param clock the game's clock
     * @param myGame the game's runner
     */
    public static void main(String[] args) 
    {
        Clock clock = new Clock(); //the game clock
        clock.start();
        GameRunner myGame = new GameRunner("soliter", clock);  
        myGame.play(); //start the game
    }
}
