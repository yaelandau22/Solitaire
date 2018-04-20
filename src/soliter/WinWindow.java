package soliter;

import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * <h1>This class describes the victory window</h1>
 * <p>The victory window appears when the player manages to finish the game.
 * It displays buttons of starting a new game and exiting the game.
 * Additionally, the window shows the score the player gained during the game.
 * If the score broke the game's record, more fields will appear, 
 * allowing the player to save his new record with his name.
 * Object of this class can be displayed graphically.<br>
 * This class extends {@link JFrame}</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 * @see JFrame
 */
public class WinWindow extends JFrame 
{
    
    /**
     * rather the window appeared or not.
     */
    public static boolean winWindowVisebility;
    
    /**
     * Reference to the game's gameRunner object
     * allows access to the games board data.
     * @see {@link GameRunner}
     */
    private GameRunner gameRunner;
    
    /**
     * Reference to the game's RecordFile object
     * allows to save the game's score in case of a new record.
     * @see {@link RecordFile}
     */
    private RecordFile recordFile = new RecordFile();
    
    /**
     * The current game's score, as a String.
     */
    private String scoreString;

    /**
     * <b>class's constructor</b> 
     * Sets the graphical features of the window.
     * @param gameRunner reference to the game's gameRunenner object
     */
    public WinWindow(GameRunner gameRunner) 
    {
        this.setUndecorated(true); 
        setGameRunner(gameRunner);
        this.setLocation(750, 350);
        initComponents();  
    }

    /**
     * This method sets the graphical features of the components in the window
     * @param jLabel1 the window's headline. contain the string <i>"WELL DONE!!"</i>
     * @param jButton1 the <i>'new game'</i> button
     * @param jButton2 the <i>'exit'</i> button
     * @param jLabel3 <i>"Your score :"</i> label
     * @param jTextField1 the filed where the score will be displayed
     * @param jLabel4 <i>"New record!!"</i> label
     * @param jTextField2 the filed where you write the new record players name
     * @param jButton3 the <i>'save'</i> button
     * @param jLabel2 <i>"Enter your name :"</i> label
     * @param jPanel1 the panel than contain all the window's components
     * 
     */

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(new java.awt.Color(0, 102, 0));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("WELL DONE!!");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton1.setText("New Game");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setName(""); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Exit");
        jButton2.setVerifyInputWhenFocusTarget(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Your score : ");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField1.setName("scorefield"); // NOI18N
        jTextField1.setPreferredSize(new java.awt.Dimension(100, 28));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("NEW RECORD!!");
        jLabel4.setName("newRecord"); // NOI18N

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton3.setText("save");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Enter your name :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3)
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 138, 138))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jLabel1))
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }
/**
 * <b>This method is responsible of executing the <i>new game</i> button.</b><br>
 * The button begins a new game, using the method {@linkplain GameRunner#restartGame()}.
 * @param evt object that allows executing the button pressing event  
 */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        getGameRunner().restartGame();
    }//GEN-LAST:event_jButton1ActionPerformed

/**
 * <b>This method is responsible of executing the <i>Exit</i> button.</b><br>
 * This method closes the program.
 * @see {@linkplain java.lang.System#exit System.exit()}
 * @param evt object that allows executing the button pressing event.
 */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * This method is responsible on the score filed events
     * this method wasn't implemented.
     * @param evt object that allows to execute the filed events
     */
private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

}//GEN-LAST:event_jTextField1ActionPerformed

/**
 * <b>This method is responsible of executing the record <i>save</i> button.</b><br>
 * <p>When the button is pressed, the field where the player writes his name is checked.
 * saving the record will not be allowed if the field is empty or if
 * the string is longer then ten chars or contained chars that are not letters,
 * then, pop up window will appear with an explanation for the reason the saving can not be completed.
 * If the player's name is legal, the score and his name will be saved in the 
 * recoed's file, and the board's record field will be updated.</p>
 * @param evt object that allows to execute the filed events
 * @param temp the current char from the players name.
 * @param toSave is the player's name legal
 * @param someCharFounf is the string empty
 * @param charIndex the string's index
 * @param newRecord contains the players name and score
 */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        String recordName = getJTextField2().getText();
        char temp;
        boolean toSave = true;
        boolean someCharFounf = false;
        for (int charIndex = 0; charIndex < recordName.length(); charIndex++) //chek the player name
        {
            temp = recordName.charAt(charIndex);
            if (temp != '\0') //if the string is not empty
            {
                someCharFounf = true;
            }
            if (!(Character.isLetter(temp))) //if character is not a letter
            {
                JOptionPane.showMessageDialog(this,
                        "You can not enter characters that are not letters", "Wrong input",
                        JOptionPane.ERROR_MESSAGE);
                toSave = false;
                break;
            }
        }

        if (recordName.length() > 10) //if string is too long
        {
            JOptionPane.showMessageDialog(this,
                    "Name must be shorter than 10 characters", "Wrong input",
                    JOptionPane.ERROR_MESSAGE);
            toSave = false;
        }

        if (toSave && someCharFounf) {
            String newRecord = recordName + " " + getScoreString();
            getRecordFile().addRecord(newRecord); //add the new record
            getGameRunner().restartGame(); //restart the game
            this.setVisible(false); 
            getGameRunner().updateRecord(newRecord); //update the record text field
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * This method is responsible of the field that contains the player's name.
     * This method wasn't implemented. 
     * @param evt object that allows to execute the filed events
     */
    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        
    }//GEN-LAST:event_jTextField2ActionPerformed

    // Variables declaration 
    /**
     * the <i>'new game'</i> button
     */
    private javax.swing.JButton jButton1;
    
    /**
     *  the <i>'exit'</i> button
     */
    private javax.swing.JButton jButton2;
    
    /**
     * the <i>'save'</i> button
     */
    private javax.swing.JButton jButton3;
    
    /**
     * the window's headline. contain the string <i>"WELL DONE!!"</i>
     */
    private javax.swing.JLabel jLabel1;
    
    /**
     * <i>"Enter your name :"</i> label
     */
    private javax.swing.JLabel jLabel2;
    
    /**
     * <i>"Your score :"</i> label
     */
    private javax.swing.JLabel jLabel3;
    
    /**
     * <i>"New record!!"</i> label
     */
    private javax.swing.JLabel jLabel4;
    
    /**
     * the panel than contain all the window's components
     */
    private javax.swing.JPanel jPanel1;
    
    /**
     * the filed where the score will be displayed
     */
    private javax.swing.JTextField jTextField1;
    
    /**
     * the filed where you write the new record players name
     */
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

    /**
     * <b>This method is responsible of setting the score field.</b><br>
     * This method calculates the score the player gained during the game.
     * The calculation of the score is the score the player got do to the 
     * transmits of the cards among the piles, plus 700000 / time of the game, 
     * in seconds.
     * @param scoreDouble will contain the final score of the game
     */
    public void setScoreField()  //sets the score field
    {
        getGameRunner().setEnabled(false);
        getGameRunner().getGameClock().stop(); //stop the clock
        double scoreDouble = getGameRunner().getScore()
                + (700000.0 / getGameRunner().getGameClock().getSecondsForScore()); //calculate the current game score
        setScoreString(new DecimalFormat("#.##").format(scoreDouble));  
        jTextField1.setEditable(false);
        jTextField1.setText(getScoreString()); 

        if (getGameRunner().readFile() != "" && !(getRecordFile().checkRecord //if the score is not a new record
        (getGameRunner().readFile(), getScoreString()))) 
        {
            jLabel2.setVisible(false);
            jLabel4.setVisible(false);
            jButton3.setVisible(false);
            jTextField2.setVisible(false);
        }
    }

    /**
     * @return the game's GameRunner object
     */
    public GameRunner getGameRunner() 
    {
        return gameRunner;
    }
    
    /**
     * @param gameRunner the game's GameRunner object
     */
    public void setGameRunner(GameRunner gameRunner) 
    {
        this.gameRunner = gameRunner;
    }

    /**
     * @return the record field
     */
    public RecordFile getRecordFile() 
    {
        return recordFile;
    }

    /**
     * @return the score of the current game
     */
    public String getScoreString() 
    {
        return scoreString;
    }

    /**
     * This method sets the score of the current game.
     * @param inScoreString the score of the current game
     */
    public void setScoreString(String inScoreString) 
    {
        scoreString = inScoreString;
    }

    /**
     * @return the field that contains the player's name
     */
    public javax.swing.JTextField getJTextField2() 
    {
        return jTextField2;
    }
}
