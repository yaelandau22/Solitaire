package soliter;
import java.util.List;

/**
 * <h1>This class describes a storage cell</h1>
 * This class extends the class {@link CardPile}.
 * This class executes the unique legality of the storage cells 
 * and allows to manage the transition of the cards as a list between the storage cells.
 * Object of this class can be displayed graphically.
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class StorageCell extends CardPile 
{
    /**
     * The class's constructor sets the pile's coordinates for the drawing.
     * @param xPosition the pile's X coordination, measured in pixels
     * @param yPosition the pile's Y coordination, measured in pixels
     */
    StorageCell(int xPosition,int yPosition)
    {
        super(xPosition,yPosition); //sets the pile location
    }

    /**
     * <b>This method checks whether adding a certain card is legal or not.</b><br>
     * The move will be legal if the pile is empty and the card that was checked is a King
     * or if the card has a smaller value by precisely one and has a different color then the last card on the list.  
     * @param card the tested card
     * @return whether adding the card legal or not
     */
    @Override
    public boolean rulesAllowAddingThisCard(Card card) 
    {
        if ((this.size() == 0 && card.getValue() == 13) || //if the pile is empty and the card's value is 13 (K)
                (this.size() > 0 && 
                this.getLastCard().getValue() - 1 == card.getValue() &&  //or if the cards difference is one and the cards coler in not the same
                this.getLastCard().getColor().compareTo(card.getColor())!=0 )) 
        {
            card.setVisitStorage(true);
            return true;
        }
        return false;
    }
    
    /**
     *<b>This method finds out whether or not a certain coordinates is inside
     * the pile's area.</b><br>
     * Used to diagnosis clicks on the pile or on one of the of the open 
     * cards in it.
     * @param x the pile's X coordination, measured in pixels
     * @param y the pile's y coordination, measured in pixels
     * @return whether or not the coordinates is inside the card's area or 
     * inside one of the of the open card's area
     */
    @Override
    public boolean isInside(int x, int y)  // checks if coordination is inside the pile area
    {
        return (x >= this.getPileX() && x < this.getPileX()+Card.CARD_WIDTH) &&
                (y >= this.getPileY()&& y < this.getPileY()+
                Card.CARD_HEIGHT+(18*(this.size()-1)) );
    }

    /**
     * This method used to add sub-list to the storage pile's list of cards.
     * Used the {@linkplain java.util.LinkedList#add(Object) LinkedList.add()}, applied on each of the cards
     * in the sub-list.
     * @param subList the sub-list to add to the storage pile's list of cards
     * @param card the current card, during the loop, in the subList. 
     */
    public void pushList(List<Card> subList) // add list of cards to the pile
    {
        for(Card card : subList)
        {
            this.getCards().add(card);
        }
    }
    
    /**
     * This method pop sub-list from the storage pile's list of cards.
     * Used the {@linkplain java.util.LinkedList#remove(int) LinkedList.remove()} applied on each of the cards
     * in the sub-list.
     * @param subList the sub-list to pop from the storage pile's list of cards
     * @param size the subList index
     */
    public void popList(List<Card> subList) // pop list of cards from the pile
    {
        for(int size=subList.size(); size>0; size--)
        {
            int lastIndex = this.getCards().size()-1;
            this.getCards().remove(lastIndex);
        }
    }
}

