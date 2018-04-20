package soliter;

import java.net.URL;
import java.util.Collections;

import javax.swing.ImageIcon;

/**
 * <h1>This class describes the game's deck<h1> <br>
 * <p>This class extends the class {@link CardPile}
 * by an object of the class there is the ability to form the game's deck.
 * Object of this class can be displayed graphically.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class Deck extends CardPile 
{
    /**
     * <b>The class's constructor</b><br> It sets the pile's coordinates for the drawing, 
     * and create the game's list of cards which made from 52 objects of the 
     * {@link Card} class kind. Afterwards, the deck is being shuffled.
     * @param xPosition the pile's X coordination, measured in pixels
     * @param yPosition the pile's Y coordination, measured in pixels
     * @param shapes array that contain the shapes values of the cards
     * @param charValues array that contain the char values. used to build the right image path.
     * @param values array that contain the numbers values of the cards
     * @param cardNumber the cards counter
     * @param cldr the class loader. used to get the resource of the image path.
     * @param imagePath the path for the current card's imageIcon
     * @param imageURL used to load the current card's imageIcon from the file
     * @param img the current card's imageIcon.
     * @param newCard the new card that was created
     */
    public Deck(int xPosition,int yPosition) //create the deck 
    {
        super(xPosition, yPosition);
            
            String shapes[] = {"s","h","d","c"};
            String charValues[] = {"a","2","3","4","5","6","7","8","9","t","J"
                    ,"Q","K"};
            int values[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
            int cardNumber = 0;
            
            while(cardNumber<52)
            {
                ClassLoader cldr = this.getClass().getClassLoader();
                String imagePath ="soliter\\cardimages\\"+charValues[cardNumber%13] //create the image path
                        +shapes[cardNumber/13]+".png";  
                URL imageURL = cldr.getResource(imagePath);
                ImageIcon img = new ImageIcon(imageURL);
                Card newCard = new Card(shapes[cardNumber/13] //create new card 
                        ,values[cardNumber%13],img);
                this.push(newCard); //add the new card to the deck
                cardNumber++;
            }           
                
      shuffle();
    }
    
    /**
     * This method shuffle the deck, using the method 
     * {@linkplain Collections#shuffle(java.util.List) Collections.shuffle()}
     * @see java.util.Collections       
     */
    public void shuffle() //shuffle the deck
    {
        Collections.shuffle(this.getCards());
    }  
}
        
