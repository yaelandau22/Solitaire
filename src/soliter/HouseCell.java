package soliter;

/**
 * <b>This class describes a home cell</b><br>
 * <p>This class extends the class {@link CardPile}.
 * This class executes the unique legality of the home cells and performs the calculation of the game's score. 
 * Object of this class can be displayed graphically.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class HouseCell extends CardPile 
{  
    /**
     * <b>class's constructor</b> Sets the pile's coordinates for the drawing.
     * @param xPosition the pile's X coordination, measured in pixels
     * @param yPosition the pile's Y coordination, measured in pixels
     */
    HouseCell(int xPosition,int yPosition)
    {
        super(xPosition,yPosition); //sets the pile location
    }
     
    /**
     * <b>This method checks whether adding a certain card is legal or not.</b><br>
     * The move will be legal if the pile is empty and the added card is an Ace 
     * or if the card that has been checked has the same shape as the last card on the list 
     * and its value is bigger in precisely one.   
     * @param card the tested card
     * @param top the last card in the card's list
     * @return whether adding a card legal or not
     */
    @Override
    public boolean rulesAllowAddingThisCard(Card card) 
    { 

        if ( (this.size() == 0) && (card.getValue()==1) ) //if the card is ace and the pile is empty
        {
            findScore(card);
            return true;
        }       
        else if (size() > 0)  
        { 
            Card top = getLastCard();
            if ( (top.getShape().compareTo(card.getShape()) == 0 ) &&   //if the cards difference is one and the cards have the same shape
                    (top.getValue()+ 1 == card.getValue()) ) 
            {
                findScore(card);
                return true;
            }
        }
        return false;
    }
    
    /**
     * <b>This method calculates how many points should be added after adding a card to the house cell.</b><br>
     * Adding points will be executed only if its the first time that the card has entered the house cell.
     * If the card was in the deck and afterwards in the storage cell the score will rise in 15 points
     * but if the card was only in the storage cell or only in the deck, the score will rise in only 15 points.
     * @param card The card that got in the home cell
     */
    public void findScore(Card card) //add score for the move
    {
            if(card.isVisitDeck() && card.isVisitStorage() &&  //if the card visit deck and storage cell
                    !card.isVisitHouse())
            {
                GameRunner.setScore(15);
            }
            else if(!card.isVisitHouse()) //if the card did not visit house cell yet
            {
                GameRunner.setScore(10);
            }
            card.setVisitHouse(true);
    }
}