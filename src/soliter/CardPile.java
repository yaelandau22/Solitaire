package soliter;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * <h1>This class describes a cards pile<h1>
 * <p>this class object is made up of a list of objects of the {@link Card} class kind.
 * This class provides methods that enables to manage the list of cards in the pile.
 * This class implements Iterable,to enable to run trough the list of the cards from the class's object.
 * Most of the methods in this class are implemented by using the build in 
 * {@linkplain java.util.LinkedList LinkedList}'s methods.</p>
 * 
 * @see Iterable
 * @see {@linkplain java.util.LinkedList LinkedList}
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class CardPile implements Iterable<Card> 
{    
    /**
     * <b>The pile's X coordination, measured in pixels</b><br>
     * is used to show the location of the pile for the drawing
     */
    private final int X_POSITION;
    
    /**
     * <b>The pile's Y coordination, measured in pixels</b><br>
     * is used to show the location of the pile for the drawing
     */
    private final int Y_POSITION;
    /**
     * The list of cards that the pile is consisted of,
     * composed of objects of the class {@link Card} kind. 
     */
    private LinkedList<Card> cards = new LinkedList<Card>(); // the card list
    
    /**
     * <b>class's constructor</b><br>
     * Sets the location of the card pile
     * @param xPosition the pile's X coordination, measured in pixels 
     * coordination 
     * @param yPosition the pile's Y coordination, measured in pixels
     * coordination 
     */
    public CardPile(int xPosition, int yPosition) // constructor
    {
        this.X_POSITION = xPosition;
        this.Y_POSITION = yPosition;
    }    
    
    /**
     * <b>This method performs pulling a card out of the pile</b><br>
     * The card that was pulled out will be the last card on the list, only if the list is not empty.
     * Performed by using the method {@linkplain java.util.LinkedList#remove(int) LinkedList.remove(int)}
     * @param lastIndex the last card index
     * @param crd the removable card
     * @return the last card in the list
     */
    public Card pop() // pop card out of the list
    {
        int lastIndex = size()-1;
        Card crd = getCards().get(lastIndex);
        getCards().remove(lastIndex);
        return crd;
    }

    /**
     * <b>This method performs addition of a card to the pile </b><br>
     * The new card will be added to the end of the list that exist in the pile.
     * Performed by using the method {@linkplain java.util.LinkedList#add(int) LinkedList.add(int)}
     * @param newCard the new card to add to the list
     * @return whether or not the card added successfully
     */
    public boolean push(Card newCard)  // add card to the list 
    {
            getCards().add(newCard);
            return true;
    }

    /**
     * This method checks if the rules that are implied on the pile allow adding a certain card 
     * @param card The card thats being checked rather its legal to add or not 
     * @return Is adding the card legal or not
     */
    public boolean rulesAllowAddingThisCard(Card card)  //checking the legality of a move
    { 
        return true;
    }
    
    /**
     * This method returns the size of the card's list or in other words how many cards are in the pile.
     * Performed by using the method {@linkplain java.util.LinkedList#size() LinkedList.size()}
     * @return the size of the card's list
     */
    public int size() // return the size of the list
    {
        return getCards().size();
    }
    
    /**
     * This method returns the pile's X coordination, measured in pixels
     * @return the pile's X coordination
     */
    public int getPileX() 
    {
        return X_POSITION;
    }
    
    /**
     * This method returns the pile's Y coordination, measured in pixels
     * @return the pile's Y coordination
     */
    public int getPileY() 
    {
        return Y_POSITION;
    }
    
    /**
     * <b>This method returns the last card in the card's list.</b><br>
     * The method dose not takes out the card, only returns reference to it.
     * Performed by using the method {@linkplain java.util.LinkedList#get(int) LinkedList.get(int)}
     * @return the last card in the card's list
     */
    public Card getLastCard() // return the last card in the list (not pop!!)
    {
        return getCards().get(getCards().size() - 1);
    }
    
    /**
     * <b>This method returns the card's list as iterator.</b><br>
     * To make it possible to run over the whole list in a 'foreach' loop.
     * Performed by using the method {@linkplain java.util.LinkedList#iterator() LinkedList.iterator()}
     * @return the card's as iterator
     */
    @Override
    public Iterator<Card> iterator() // Allows to run on the cards list
    {
        return getCards().iterator();
    }

    /**
     * This method returns the card's list.
     * @return the card's list
     */
    public LinkedList<Card> getCards() 
    {
        return cards;
    }
    
    /**
     * This method returns a card from a specific index in the list.
     * Performed by using the method {@linkplain java.util.LinkedList#get(int) LinkedList.get(int)}
     * @param index the index of the wanted card
     * @return the wanted card
     */
    public Card getCard(int index)  // get card by index
    {
        return getCards().get(index);
    }
    
    /**
     * <b>This method finds out whether or not a certain coordinates is inside
     * the pile's area.</b> <br>
     * Used to diagnosis clicks on the pile. 
     * @param x the pile's X coordination, measured in pixels
     * @param y the pile's Y coordination, measured in pixels
     * @return whether or not the coordinates is inside the card's area.
     */
    public boolean isInside(int x, int y) // Checks if coordination is inside the pile area
    {
        return (x >= this.getPileX() && x < this.getPileX()+Card.CARD_WIDTH) 
             && (y >= this.getPileY()&& y < this.getPileY()+Card.CARD_HEIGHT );
    }
}