package soliter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * <h1>This class describes the game's clock</h1><br> 
 * <p>It gives the ability to measure and calculate the score the player deserves.
 * the clock zeroes immediately when a new game begins and stops in the end of 
 * the game.</p>
 * <p>In the end of the game, the calculation of the time is added to the player's 
 * score, by the equation: seconds/700,000
 * Object of this class is displayed graphically.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class Clock extends JPanel 
{
	/**
     * The value of one minutes ({@value #MINUTE})<br>
     * Used to know how many seconds worth one minute
     */
    private static final int MINUTE = 60;
    
    /**
     * Used to know listen to the timer events 
     */
    private final ClockListener CLOCK_LISTENER = new ClockListener();
    
    /**
     * The game timer, with delay value of one second. 
     */
    private final Timer GAME_TIMER = new Timer(1000, CLOCK_LISTENER);
    
    /**
     * the field that will contain the clock digits. 
     * limited to 8 characters.
     */
    private final JTextField CLOCK_FIELD = new JTextField(8);
    
    /**
     * the clock's hours digits value
     */
    private int hours;
    
    /**
     * the clock's minutes digits value
     */
    private int minutes;
    
    /**
     * the clock's seconds digits value
     */
    private int seconds;
    
    /**
     * contain how many time past totally since the game started, in seconds.
     * used for the score calculation.
     */
    private int secondsForScore;
    
    /**
     *  The class's constructor. sets all the graphical features.
     * @param panel the panel that will contain the clock's field
     */
    public Clock() 
    {
        GAME_TIMER.setInitialDelay(0);
        JPanel panel = new JPanel();
        CLOCK_FIELD.setHorizontalAlignment(JTextField.RIGHT);
        CLOCK_FIELD.setEditable(false);
        panel.add(CLOCK_FIELD);
        this.add(panel);
        this.setVisible(true);
    }

    /**
     * This method reset the timer and start it.
     */
    public void start() //restart timer 
    {
        setHours(0);
        setMinutes(0);
        setSeconds(0);
        setSecondsForScore(0);
        GAME_TIMER.start();  
    }

    /**
     * <b>This method is responsible of updating and settings the game's timer 
     * in the clock field on the game's board</b><br>
     * This method activates by zeroing the seconds value when they are equal 
     * to a minute value and of course at the same time adding to the minutes 
     * value one minute.
     *  In the same way the method keeps track on the minutes value to make 
     * sure the time wont pass an our value. 
     * @param hour how many round hours past since the game started, formatted to string
     * @param minute how many round minute past since the game started, formatted to string
     * @param second how many second past since the game started formatted, to string
     * @param NumberFormat the format of the clock digits
     */
    private class ClockListener implements ActionListener 
    {
        private String hour;
        private String minute;
        private String second;

        @Override
        public void actionPerformed(ActionEvent e)  //sets the minutes, seconds and hours 
        {
            NumberFormat formatter = new DecimalFormat("00");
            if (getSeconds() == MINUTE) 
            {
               setSeconds(00);
               setMinutes(getMinutes()+ 1);
            }

            if (getMinutes() == MINUTE) 
            {
                setMinutes(00);
                setHours(getHours()+1);
            }
            hour = formatter.format(getHours());
            minute = formatter.format(getMinutes());
            second = formatter.format(getSeconds());
            CLOCK_FIELD.setText(String.valueOf
                (hour + ":" + minute + ":" + second));
            setSeconds(getSeconds()+ 1);
            setSecondsForScore(getSecondsForScore()+1);
        }
    }
    
    /**
     * Returns the total seconds of the game. Used to calculate the score
     * @return the total seconds of the game
     */
    public int getSecondsForScore()
    {
        return secondsForScore;
    }  
    
    /**
     * Returns the clock's seconds digits value
     * @return the clock's seconds
     */
    public int getSeconds()
    {
        return seconds;
    }
    
    /**
     * Returns the clock's hours digits value
     * @return the clock's hours
     */
    public int getHours()
    {
        return hours;
    }
    
    /**
     * The clock's minutes digits value
     * @return the clock's minutes
     */
    public int getMinutes()
    {
        return minutes;
    } 
    
    /**
     * Sets the clock's seconds digits value
     * @param inSeconds the new seconds digits value
     */
    public void setSeconds(int inSeconds)
    {
        seconds = inSeconds;
    }
    
    /**
     * Sets the clock's hours digits value
     * @param inHours the new seconds digits value
     */
    public void setHours(int inHours)
    {
        hours = inHours;
    }
    
    /**
     * Sets the clock's minutes digits value
     * @param inMinutes the new seconds digits value
     */
    public void setMinutes(int inMinutes)
    {
        minutes = inMinutes;
    }   
    
    /**
     * Sets the total seconds of the game
     * @param inSecondsForScore the total seconds of the game
     */
    public void setSecondsForScore(int inSecondsForScore)
    {
        secondsForScore = inSecondsForScore;
    } 
    
    /**
     * This method stops the clock
     * @see stop()
     */
    public void stop() //stop the clock
    {
        GAME_TIMER.stop();
    }
}