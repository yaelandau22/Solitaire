package soliter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <h1>This class is responsible for the documentation of the highest score of the 
 * game.<h1>
 * <p>This class checks the current game's score in comparison to the current 
 * record.
 * If the score is higher then the record, the record will change to the new 
 * highest score.
 * There is an exception case in the first win, when there is no comparison to 
 * previous record.
 * in this case, the game's score will automatically be saved as the new record.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class RecordFile 
{

    /**
     * <b>This method performs the writing of the record into the file.</b><br>
     * This method gets the players name that had done the new record, with the 
     * score he got, as a string, and that string is saved  into the file, 
     * as the new record.
     * while trampling the last record if there was one.
     * @param score The string that contains the players name and his score.
     * @param recordsPath The path to the file where the record will be saved.
     * @param file The file where the record will be saved
     * @param fw allows to write into the file
     * @param myBufferedWriter allows to write in the file
     */
    public void addRecord(String score) //add new record to the file
    {
        String recordsPath = "SoliterRecord.txt"; //file path
        File file = new File(recordsPath);
        
        try 
        {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter myBufferedWriter = new BufferedWriter(fw);

            myBufferedWriter.newLine();
            myBufferedWriter.write(score); //write the score in the file

            myBufferedWriter.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * <b>This method checks if the score of the current game is higher then the 
     * record saved in the file.</b><br>
     * There will be  thrown an exception in this method after the first win of the game, because 
     * the file won't contain a number, only the sentence <i>be the first!</i>.
     * In this case, it will be determined that the score of the current game 
     * will set as the record.
     * @param currentRecord the current record string
     * @param playerScore the current game's record string
     * @param playerScoreDouble the player score string converted to double
     * @param currentRecordDouble the current record string converted to double
     * @param currentRecordArr the array that contain the current record string splitted
     * @return Is the score of the current game is a new record
     */
    public boolean checkRecord(String currentRecord, String playerScore) //chek if the current game score is a new record
    {
        double playerScoreDouble = Double.valueOf(playerScore);
        double currentRecordDouble;
        String[] currentRecordArr = currentRecord.split(" "); //split the record's name and score 
        
        try 
        {
            currentRecordDouble = Double.valueOf(currentRecordArr[1]); //convert the score from string to double
        } 
        catch (Exception e) //Happens for the first record
        {
            currentRecordDouble = 0;
        }

        if (currentRecordDouble > playerScoreDouble) 
        {
            return false;
        }

        return true;
    }
}
