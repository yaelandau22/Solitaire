package soliter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <h1>This class is the game runner<h1><br> <p>It's performing the boot of the game by 
 * creating all it's components, and  makes the connection between the 
 * graphics and the logic of the game.</p>
 * this class sets the window's graphic features.
 * This class extends {@link Game}.
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 * @see  Game
 */
public class GameRunner extends Game 
{
    /**
     * This object manage the cards movement on the board
     */
    private CardsPanel boardDisplay;
    
    /**
     * The game's clock
     * @see {@link Clock}
     */
    private Clock gameClock;
    
    /**
     * The score field. display the player score during the game.
     */
    private static double score;
    
    /**
     * This label contain the score field.
     */
    private static final JLabel SCORE_JL = new JLabel();
    
    /**
     * The winning window. display when the player manage to finish the game.
     */
    private final WinWindow WIN_WINDOW = new WinWindow(this);
    
    /**
     * This object contain all the cards pile in the game.
     */
    private final CardTable MY_CARD_TABLE = new CardTable(getWinWindow());
    
    /**
     * The upper toolbar. it contain the "new game" button and the record field.
     */
    private final JPanel TOOL_BAR = new JPanel();
    
    /**
     * The record's file path
     */
    private static final String RECORD_FILE_PATH = "SoliterRecord.txt";
    
    /**
     * The content of the record label
     */
    private final JLabel RECORD_LABLE = new JLabel("                                  "
            + "                The current record belongs to :    " + 
            readFile());

    /**
     * Class's constructor
     * @param gameName the name of the game
     * @param clock the game's clock
     */
    public GameRunner(String gameName, Clock clock) 
    {
        super(gameName);
        setGameClock(clock);
    }

    /**
     * <b>This method boot the game</b><br> It's set all the window's graphic components.
     * @param subToolBar the upper toolbar
     * @param newGameButton the <i>new game</i> button
     * @param content the panel that fill the window
     */
    @Override
    public void play() 
    {
        JPanel subToolBar = new JPanel();
        JButton newGameButton = new JButton("New Game");

        newGameButton.addActionListener(new ActionListener() 
        {
            /**
             * this method executed when the <i>new game</i> button is pressed.
             * it's restart the game by the method <i>restartGame()</i>.
             * @param e object that allows to execute the button event
             */
            public void actionPerformed(ActionEvent e) 
            {
                restartGame();
            }
        });
        
        setBoardDisplay(new CardsPanel(getMyCardTable())); // sets the cards panel (with the card table)
        JPanel content = new JPanel(); //the window content
        content.setLayout(new BorderLayout());
        content.add(getGameClock(), BorderLayout.SOUTH);
        content.add(getBoardDisplay(), BorderLayout.CENTER);
        content.add(getToolBar(), BorderLayout.NORTH);
        getScoreJL().setText(new DecimalFormat("score:   #.##").format(getScore())); //score text
        getGameClock().add(getScoreJL());
        getToolBar().setLayout(new BorderLayout());
        getToolBar().add(getRecordLabel(), BorderLayout.WEST);
        subToolBar.add(newGameButton);
        getToolBar().add(subToolBar); 
        setContentPane(content);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        getWinWindow().setLocation(500,200); //set winWindow locatoin
        setLocationRelativeTo(null); // set the window in the middle
        setResizable(false); //prevents Resizing
        setVisible(true);  
        getWinWindow().setResizable(false); //prevents Resizing
        getWinWindow().setVisible(false); 
        getWinWindow().setAlwaysOnTop(true); 
    }

    /**
     * <b>This method sets the score field</b><br>
     * This method adding the entering parameter to the points sum, but if the 
     * entering parameter value is zero the it will reset the sum.
     * @param scoreToAdd the score to add to the score's sum
     */
    public static void setScore(double scoreToAdd) //set the score
    {
        if (scoreToAdd == 0) 
        {
            score = 0;
        } 
        else 
        {
            score += scoreToAdd;
        }
        getScoreJL().setText(new DecimalFormat("score: ") //set the score text
                .format(getScore()));
    }

    /**
     * @return the game's cardTable object
     */
    public CardTable getCardTable() 
    {
        return getMyCardTable();
    }

    /**
     * @return the score's sum
     */
    public static double getScore() 
    {
        return score;
    }

    /**
     * @return the game's board
     */
    public CardsPanel getBoardDisplay() 
    {
        return boardDisplay;
    }
   
    /**
     * This method sets the game's board
     * @param mycardsPanel the game's board 
     */
    public void setBoardDisplay(CardsPanel mycardsPanel) 
    {
        boardDisplay = mycardsPanel;
    }

    /**
     * <b>This method restart the game</b><br>
     * It's reset the game's clock, the game's score and reactivated 
     * the method {@link CardTable#resetTable()}.
     */
    public void restartGame() 
    {
        this.setEnabled(true);
        getMyCardTable().resetTable();
        boardDisplay.repaint();
        getGameClock().start();
        setScore(0);
    }

    /**
     * <b>This method read the record file, extract the player name and score, 
     * and displays it on the upper toolbar, in the record field.</b><br>
     * If it's the first time this game is activated, the record file does not 
     * exist yet, so it will be created on this method.
     * @param content the content of the record's file, as a string
     * @param file the record's file
     * @param reader manage the reading from the record's file
     * @param chars the content of the record's file, as a chars array
     * @return the record's player name and score
     */
    public String readFile() //read the record
    {
        String content = null;
        File file=null;
        
        try 
        {
            file = new File(getRecordFilePath());
            if (file.createNewFile()) // create the file if it is not exist
            {
                getWinWindow().getRecordFile().addRecord("Be the first!");
                return "Be the first!"; 
            } 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        try  //read the record from the file
        {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return content;
    }
    
    /**
     * This method update the record field in case of new record.
     * @param newRecord the new record string
     */
    public void updateRecord(String newRecord) //update the record text
    {
        getRecordLabel().setText("                                             "
                + "The current record belongs to :    " + newRecord);
    }

    /**
     * @return the record file's path, which is: {@value #RECORD_FILE_PATH}
     */
    public String getRecordFilePath() 
    {
        return RECORD_FILE_PATH;
    }
    
    /**
     * @return the game's clock
     */
    public  Clock getGameClock()
    {
        return gameClock;
    }
    
    /**
     * This method sets the game's clock.
     * @param clock the game's clock
     */
    public void setGameClock(Clock clock)
    {
        gameClock =  clock;
    }
    
    /**
     * @return the games score label
     */
    public static JLabel getScoreJL()
    {
        return SCORE_JL;
    }
    
    /**
     * @return the winning window
     */
    public WinWindow getWinWindow()
    {
        return WIN_WINDOW;
    }
    
    /**
     * @return the game's cardTable
     */
    public  CardTable getMyCardTable()
    {
        return MY_CARD_TABLE;
    }
    
    /**
     * @return the upper toolbar panel 
     */
     public JPanel getToolBar()
     {
         return TOOL_BAR;
     }
     
     /**
      * @return the games record label
      */
     public JLabel getRecordLabel()
     {
         return RECORD_LABLE;
     }    
}
