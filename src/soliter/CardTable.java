package soliter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1>This class gathers in it all the game's objects and there for it is the game's board</h1>
 * <p>in this class the card's piles are formed and the cards are divided to them.
 * This class implements Iterable,to enable to run trough the list of piles from the class's object.</p>
 * 
 * @see Iterable
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class CardTable implements Iterable<CardPile>  // create the card's piles
{
    /**
     * the storage cells
     */
   private StorageCell[] storageCells;
   
   /**
    * the house cells
    */
   private HouseCell[] houseCells;  
   
   /**
    * the x coordination of the pile, measured in pixels. used to draw the piles in different locations.
    */
   private int xPosition=820;
   
   /**
    * the y coordination of the pile, measured in pixels. used to draw the piles in different locations.
    */
   private int yPosition=50;
   
   /**
    * the deck
    */
   private Deck deck;
   
   /**
    * the fake deck. a CardPile object that contain the open cards from the deck
    */
   private CardPile fakeDeck = new CardPile(175,50);
   
   /**
    * the window that appears the second the player finishes the game.
    */
   private WinWindow winWindow;
   
   /**
    * all the piles in the table linked in one list 
    */
   private LinkedList<CardPile> allPiles;

   /**
    * <b>class's constructor</b><br>
    * in the constructor all the card cells are formed except for the deck and 
    * the fake deck.
    * after forming the card cells the constructor activates the method 
    * {@linkplain #resetTable()}, which divides the cards to the cells
    * @param winWindow 
    * @param pileNumber index of the created card's pile  
    */
    public CardTable(WinWindow winWindow) // constractor
    {
       setWinWindow(winWindow);
       allPiles = new LinkedList<CardPile>();
       houseCells = new HouseCell[4];
       storageCells = new StorageCell[7];

        for (int pileNumber = 0; pileNumber < getHousePiles().length; pileNumber++) // create the house cells 
        {  
            getHousePiles()[pileNumber] = new HouseCell(getXPosition()
                    ,getYPosition()); 
            setXPosition(getXPosition()-(Card.CARD_WIDTH+50));
            getAllPiles().add(getHousePiles()[pileNumber]);
        }
        
        setYPosition(getYPosition()+(Card.CARD_HEIGHT+50));
        setXPosition(820);
        
        for (int pileNumber = 0; pileNumber < storageCells.length; pileNumber++) // create the storage cells
        {
            storageCells[pileNumber] = new StorageCell(getXPosition()
                    ,getYPosition());
            setXPosition(getXPosition()-(Card.CARD_WIDTH+50));
            getAllPiles().add(storageCells[pileNumber]);
        }
        
        resetTable();
    }
    
    /**
     * <b>This method restarts the board</b><br>
     * in this method the deck is created and from it the cards are divided to all the piles.
     * @param pile the card's pile on the board 
     * @param pileNumber index of the storage cell which the cards are divided to it in that moment.
     * @param cardsToDeal the number of storage cells that still need to get cards
     */
    public void resetTable() // deal the cards
    {     
       for ( CardPile pile : this ) // clean the cells for new game
        {
            pile.getCards().clear();
        }
        
        deck = new Deck(50,50);
        getAllPiles().add(getDeck());
        getAllPiles().add(getFakeDeck());   
        
        int cardsToDeal=7;
       
       for(int pileNumber = 0; pileNumber<7; pileNumber++) // deal cards to storage cells
       {
           for(int cardsLeft=0;  cardsLeft<cardsToDeal; cardsLeft++)
           {
                storageCells[pileNumber].push(getDeck().pop());
                storageCells[pileNumber].getLastCard().setVisitStorage(true);  //marks card was in storage cell
                storageCells[pileNumber].getLastCard().setVisitDeck(false);  //marks card was not in the deck
           }
           cardsToDeal--;
       }
        
    }
    
    /**
     * <b>This method returns a home cell according to index</b>
     * @param index the wanted home cell index
     * @return the home cell in the requested index
     */
    public CardPile getHousePile(int index)  // return house pile by index
    {
        return houseCells[index];
    }
    
    /**
     * The method returns the home cells array.
     * @return the home cells array
     */
    public CardPile[] getHousePiles()  //return the house piles
    {
        return houseCells;
    }
    
    /**
     * This method returns a storage cell according to index.
     * @param index the wanted storage cell index
     * @return the storage cell in the requested index
     */
    public CardPile getStoragePile(int index) // return storage pile by index
    {
        return storageCells[index];
    }

    /**
     * The method returns the storage cells array.
     * @return the storage cells array
     */
    public CardPile[] getStoragePiles() // return the storage piles
    {
        return storageCells;
    }
    
    /**
     * The method returns the deck.
     * @return the deck
     */
    public Deck getDeck() 
    {
        return deck;
    }
   
    /**
     * The method returns the fake deck
     * @return the fake deck
     */
    public CardPile getFakeDeck() 
    {
        return fakeDeck;
    }

    /**
     * The method returns LinkedList that contains all the cards piles.
     * @return all the piles in linked list
     */
    public LinkedList<CardPile> getAllPiles()  
    {
        return allPiles;
    }
    
    /**
     * The method returns the cards piles list as iterator.
     * @return all the piles as Iterator
     * @see Iterator
     */
   @Override
    public Iterator<CardPile> iterator() // Allows to run on the piles list
    {
        return getAllPiles().iterator();
    }
    
    /**
     * The method executes the transmit of a card from one pile to another,
     * in condition that the target pile's rules allow that transmit.
     * @param source the pile which the card came out of
     * @param target the pile that the card is designated to transmit to
     * @param card the card that is designated to transmit  
     * @return whether the transmit was legal or not
     */
    public boolean moveFromPileToPile(CardPile source, CardPile target) // move card from pile to another
    {
        if (source.size() > 0) 
        {
            Card card = source.getLastCard();
            if (target.rulesAllowAddingThisCard(card)) 
            {
                target.push(card);
                source.pop();
                
                 if(target.getClass()==houseCells[0].getClass() && isWin()) // checks winning 
                 {
                     getWinWindow().setScoreField();  //set score field
                     getWinWindow().setVisible(true);  //reveals winning window
                     return true;
                 }  
            }
        }
        
        return false;
    }
   
    /**
     * This method performs the transmit of the cards list from one storage 
     * pile to another, in condition the move is legal.
     * @param subList the list of cards that designated to transmit
     * @param source source cell of the card's list
     * @param target target cell of the card's list
     * @param card the first card on the cards list that is designated to transmit. it used to determine whether the move is legal or not.
     */
    public void moveFromListToPile(List<Card> subList,StorageCell source, 
            StorageCell target)  // move list of cards from storage pile to another storage pile
    {
        if (subList.size() > 0) 
        {
            Card card = subList.get(0);
            if (target.rulesAllowAddingThisCard(card)) // checks the legality of the move
            {
                target.pushList(subList);
                source.popList(subList);
            } 
        }
    }
   
    /**
     * This method checks if the player managed to finish the game.
     * @return whether the game ended or not
     */
    private boolean isWin() // checks winning
    {
        for(CardPile pile : this.getHousePiles() )
        {
            if(pile.size()!=13)
            {
                return false;
            }
        }    
        return true;   
    }
    
    /**
     * @return the x coordination of the current created pile, measured in pixels
     */
    public int getXPosition() 
    {
        return xPosition;
    }
    
    /**
     * @return the y coordination of the current created pile, measured in pixels
     */
    public int getYPosition() 
    {
        return yPosition;
    }
   
    /**
     * This method sets the y coordination for the next created pile
     * @param x the x coordination for the next created pile, measured in pixels
     */
    public void setXPosition(int x) 
    {
        xPosition = x;
    }
    
    /**
     * This method sets the y coordination for the next created pile
     * @param y the y coordination for the next created pile, measured in pixels
     */
    public void setYPosition(int y) 
    {
        yPosition = y;
    }
   
    /**
     * @return the winning Window
     */
    public WinWindow getWinWindow() 
    {
        return winWindow;
    }
    
    /**
     * This method sets the winning Window
     * @param inWinWindow the winning Window
     */
    public void setWinWindow( WinWindow inWinWindow ) 
    {
        winWindow =  inWinWindow ;
    }
}
