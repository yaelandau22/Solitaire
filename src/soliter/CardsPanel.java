package soliter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JComponent;

/**
 * <h1>This class is responsible to manage the cards movement on the board and from one pile 
 * to another pile graphically</h1>
 * <p>This class extends the class {@link JComponent}.  
 * and implement {@link MouseListener} and  {@link MouseMotionListener} in order to diagnose 
 * the different clicks on the cards:
 * <ul> 
 * <li>double clicks </li>
 * <li>extended clicks</li>
 * <li> dragging clicks</li>
 * <li> releasing clicks</li>
 * </ul>
 * Updating the cards location on the board is performed by diagnosing those mouse clicks,
 * updating the cards coordinate by the mouse coordinate and redrawing the game's board.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class CardsPanel extends JComponent implements MouseListener, 
        MouseMotionListener
{
    /**
     * <b>The card table of the current game</b><br>
     * This object allows access to all the objects in the game
     */
    private CardTable cardTable;
    
    /**
     * <b>The current dragged card</b><br>
     * Is used to know rather to draw the card according to the pile it belongs to.<br>
     * If the value is true the card will be drawn acording to the pile.<br>
     * If the value is false the card will be drawn acording to the coordinate it got from the 
     * mouse's location.
     */
    private Card draggedCard = null;
    
    
    /**
     * <b>The X coordination the card was dragged from, measured in pixels</b><br>
     * Used in cases of an attempt to make an illegal move which afterwards the card needs to 
     * return to the location it was dragged from.
     */
    private int dragFromX = 0; 
    
    /**
     * <b>The Y coordination the card was dragged from, measured in pixels</b><br>
     * Used in cases of an attempt to make an illegal move which afterwards the card needs to 
     * return to the location it was dragged from.
     */
    private int dragFromY = 0;
    
    /**
     * <b>The pile the card was dragged from</b><br>
     * Used in cases of an attempt to make an illegal move which afterwards the card needs to 
     * return to the pile it was dragged from.
     */
    private CardPile draggedFromPile = null;
    
    /**
     * <b>The dragged cards list as a StorageCells</b><br>
     * is used in cases you need to give the list of cards qualities of a pile of cards.
     */
    private StorageCell draggedPile = null;   
    
    /**
     * <b>The current dragged cards list</b><br>
     * is used to know rather to draw the cards that are on the list according to the pile 
     * they belong to.
     * If the value is true the cards will be drawn according to the pile.
     * If the value is false the cards will be drawn with regard to the coordinate they got 
     * from the mouse's location.
     */
    private List<Card> subList=null;
    
    /**
     * <b>class's constructor</b><br>
     * Initializing the cardTable, the window size and adding the MouseListener and the 
     * MouseMotionListener.
     * @param cardTable this object allows access to all the board objects in the game.
     */
    public CardsPanel(CardTable cardTable) 
    {
        setCardTable(cardTable);        
        setPreferredSize(new Dimension(910, 600)); //window size
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * <b>This method is responsible of the screen's drawing and all its constituents.</b><br>
     * <p>This method is executed over and over and is responsible of graphically updating the 
     * changes in the game's board. It method activated by calling the method {@link JComponent#repaint()}.</p>
     * <p>In this method the background's color is set, and the piles drawing is executed by the method 
     * {@linkplain CardsPanel#drawFrame(Graphics, CardPile) drawFrame()},
     * and the piles are drawn by the method {@linkplain CardsPanel#drawPile(Graphics, CardPile) drawPile()} 
     * except for the storage piles that are drawn by the method 
     * {@linkplain CardsPanel#drawStoragePiles(Graphics, CardPile) drawStoragePiles()}.
     * Also this method draws the dragged card and the list of of cards that are being dragged.</p>
     * @param g Object that used to draw the graphic elements on the screen
     * @param width window width, measured in pixels
     * @param height window height, measured in pixels
     * @param pile The pile which is drawn
     * @param card The card which is drawn
     */
    @Override
    public void paint(Graphics g) 
    {
        int width = getWidth();
        int height = getHeight();
        g.setColor(new Color(0, 150, 0)); //background color
        g.fillRect(0, 0, width, height);

        for ( CardPile pile : getCardTable() ) //draw all piles
        {
            drawFrame(g, pile);
            
            if(pile.getClass()!=  getCardTable().getStoragePile(0).getClass()) // draw all the piles except the storage piles
            {
                drawPile(g, pile); 
            }
            else
            {
                drawStoragePiles(g, pile); // draw the storage piles
            }
        }
        
        if (getDraggedCard() != null) //draw dragged card
        {
            getDraggedCard().draw(g);
        }
        
        if (getSubList() != null) //draw dragged list
        {
            for(Card card : getSubList())
            {
                card.draw(g);
            }
        }
    }
    
    /**
     * <b>This method is responsible of performing the storage cells drawing.</b><br>
     * This method operates by going through all the cards in the pile,
     * checks that they are not being dragged right now and sending them to the method {@linkplain CardsPanel#drawCardsDown(Graphics, CardPile, Card, int) drawCardsDown()}.
     * @param g Object that used to draw the graphic elements on the screen
     * @param pile The storage pile thats being drawn right now
     * @param cardYPosition the Y coordination of the next card in the pile, measured in pixels
     * @param card The card that was sent to to be drawn
     * @param check used as a flag to check if a curtain card is in the dragged list 
     * @param subListCard a card from the dragged list
     */
    private void drawStoragePiles(Graphics g, CardPile pile)  // draw the storage piles
    {       
        int cardYPosition = pile.getPileY();
        
        if (pile.size() > 0) //draw only if the pile is not empty
        {
            if(getSubList()==null) // if no list is dragged
            {
                for (Card card : pile) 
                {
                    if (card != getDraggedCard()) 
                    {
                        if (card == pile.getLastCard()) //reveals the last card in each storage pile
                        {
                            card.setShowCard(true);
                        }
                        cardYPosition = drawCardsDown(g,pile,card,
                                cardYPosition);
                    }
                }
            }
            else // if list is dragged
            {
                for (Card card : pile) 
                {
                    int check=0;
                    for(Card subListCard : getSubList())
                    {
                        if(card==subListCard)
                        {
                            check=1; //mark the cards in the dragged list
                        }
                    }
                    if(check==0) // draw only the non dragged cards 
                    {
                        cardYPosition = drawCardsDown(g,pile,card,
                                cardYPosition);
                    }
                }
            }
        }        
    }
    
    /**
     * <b>This method performs the drawing of the storage cells cards.</b><br>
     * It operates by updating the coordinates of the card and redrawing it, using the method {@link Card#draw(Graphics)}.
     * @param g Object that used to draw the graphic elements on the screen
     * @param pile the pile which the current drawn card came from
     * @param card The card that is currently being drawn
     * @param cardYPosition the Y coordination of the card, measured in pixels 
     * @return the current card's Y coordination
     */
    private int drawCardsDown(Graphics g, CardPile pile, Card card, 
            int cardYPosition) // draw the storage pile cards
    {    
         card.setPosition(pile.getPileX(), cardYPosition);
         card.draw(g);
         return cardYPosition += 18;
    }

    /**
     * This method draws all the card piles except for the storage piles.
     * This method operates by going through all the cards in the pile, 
     * checks that the current card isn't dragged and redraws it, using the method {@link Card#draw(Graphics)}.
     * @param g Object that used to draw the graphic elements on the screen
     * @param pile the pile of cards that the method is drawing
     * @param card a card in the pile that needs to be drawn
     */
    private void drawPile(Graphics g, CardPile pile) 
    {
        if (pile.size() > 0) //draw only if the pile is not empty
        {
            for (Card card : pile) 
            {
                if (card != getDraggedCard()) // draw only non dragged card
                {
                        card.setPosition(pile.getPileX(), pile.getPileY());
                        card.draw(g);
                }
            }
        }
    }
    
    /**
     * <b>This method performs the drawing of the cards piles frames.</b><br>
     * operates by using this method {@linkplain Graphics2D#drawRoundRect(int, int, int, int, int, int) Graphics2D.drawRoundRect()}.
     * @param g Object that used to draw the graphic elements on the screen
     * @param pile the pile which the frame is drawn for
     * @param g2 the <i>g</i> variable casted to Graphics2D
     * @see Graphics2D
     */
    private void drawFrame(Graphics g, CardPile pile) // draw the cells frames
    {
        if(pile!=getCardTable().getFakeDeck())
        {
            g.setColor(Color.WHITE); //set the pen color for the frame drawing 
            Graphics2D g2 = (Graphics2D) g; 
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(pile.getPileX()+2, pile.getPileY()+3, Card.CARD_WIDTH-5
                    ,Card.CARD_HEIGHT-6,10,10); 
            g.setColor(new Color(0, 175, 0));
        }
    }

    /**
     * <b>This method is responsible of the execution of the mouse events when there is a click.</b><br>
     * the method is responsible of two kind of events:
     * <ul>
     * <li>click</li> 
     * <li>double click</li>
     * </ul>
     * <b>click:</b> If there was a click on the deck and its not empty, the last card in the pile reveals and is transmitted to the fake pile.
     * If the pile is empty all the cards in the fake deck are transmitted back to the deck and get shuffled.<br>
     * <b>double click:</b> If there was a double click on any card, the home cells are being checked if the card can legally transmit to them.
     * If the home cell was found suitable, the card will transmit to it.
     * @param me An object that gives details on the mouse's features while performing the click 
     * @param x the click's x coordination, measured in pixels
     * @param y the click's y coordination, measured in pixels
     * @param housePile A home cell thats being checked if can receive the card that was double clicked
     */
    @Override    
    public void mouseClicked(MouseEvent me) 
    {
        int x = me.getX(); //saves the click coordination
        int y = me.getY();
        
        if(getCardTable().getDeck().isInside(x, y) && 
                getCardTable().getDeck().size()>0) //checks if the click occurred on the deck and if deck is not empty 
        {
            getCardTable().getDeck().getLastCard().setShowCard(true); // open the deck
            getCardTable().moveFromPileToPile(getCardTable().getDeck()
                    ,getCardTable().getFakeDeck());
            this.repaint(); 
        }
        else if( getCardTable().getDeck().isInside(x, y) ) // checks if the click occurred on the deck and if deck is empty 
        { 
            while(0 < getCardTable().getFakeDeck().size())  // reload the deck
            {
                getCardTable().getFakeDeck().getLastCard().setShowCard(false); 
                getCardTable().moveFromPileToPile(getCardTable().getFakeDeck()
                        ,getCardTable().getDeck());
            }
            getCardTable().getDeck().shuffle(); // Shuffle the deck
        }
        
        if (me.getClickCount() == 2) // when double click occurred
        {
            CardPile pile=findPileAt(x,y); //checks where the double click occurred
            
            if(pile!=null && pile.size()>0 && 
                    pile.getLastCard().isShowingCard() ) //only if the card is accessible 
            {
                for(CardPile housePile : getCardTable().getHousePiles()) //find the right pile for the card
                {
                    if(pile.getLastCard().getValue()==1  && housePile.size()==0) //if the card is ace
                    {
                        getCardTable().moveFromPileToPile(pile,housePile);
                        break;
                    }
                    else if(housePile.size()>0 && housePile.getLastCard() //other cards
                            .getValue()+1==pile.getLastCard().getValue() 
                            && housePile.getLastCard().getShape().compareTo
                            (pile.getLastCard().getShape())==0)
                    {
                        getCardTable().moveFromPileToPile(pile,housePile);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * <b>This method is responsible for the mouse events when its pressed</b><br>
     * When a mouse pressed is identified there is a one card movement or a list movement on the screen.  
     * When the mouse is pressed, the pressing location is being identified and the dragged card's location 
     * data is saved, to be able to return it back to it's previous location in case of illegal move.
     * @param me object that gives details on the mouse features while the mouse is pressed 
     * @param x the x coordinate of were the pressed occurred, measured in pixels
     * @param y the y coordinate of were the pressed occurred, measured in pixels
     * @param pile the pile that is being checked rather it was pressed or not
     * @param testCard the last card of any pile that is being checked rather it was pressed or not
     */
    @Override
    public void mousePressed(MouseEvent me) 
    {
        int x = me.getX(); //saves the click coordination
        int y = me.getY();   
        setDraggedCard(null);  
  
        for ( CardPile pile : getCardTable() )
        {
            if (pile.size() > 0 && pile != getCardTable().getDeck()) //if the pile is not empty and is not the deck
            {
                Card testCard = pile.getCard(pile.size() - 1); //check if the click occurred on the last card of some pile
                if (testCard.isInside(x, y))  //moving one card
                {
                    setDragfromX(x - testCard.getX()); //saves coordination of the dragged card for case of illegal move
                    setDragfromY(y - testCard.getY());  
                    setDraggedCard(testCard);  
                    setDraggedFromPile(pile); // save the source pile
                    break;   
                }
                else //moving list of cards
                {
                    CardPile testPile = pile;
                    if(testPile.isInside(x, y)) //check if the click occurred on some pile
                    { 
                        Card card=null;
                        for(int index=testPile.size()-1; index>=0; index--) //finds which card on the pile was clicked
                        {               
                            if(testPile.getCard(index).isInside(x, y) && 
                                    testPile.getCard(index).isShowingCard()) //only if the card is accesible 
                            {
                                card=pile.getCard(index);
                                setSubList( testPile.getCards().subList  //create the sub list to move
                                (testPile.getCards().indexOf(card),testPile.
                                        getCards().size()) ); 
                                setDragfromX(x - testPile.getPileX());  //saves coordination of the dragged list for case of illegal move
                                setDragfromY(y - testPile.getPileY()); 
                                setDraggedPile((StorageCell)pile);
                                break;
                            }
                        }
                    } 
                }
            }           
        }
    }
    
    /**
     * <b>This method is responsible of executing the mouse events while the mouse is dragged</b><br>
     * When the mouse drags a card or a list of cards their coordinates update 
     * concerning the location features of the mouse while dragging, and afterwards there is a redrawing of the board.  
     * the process occurs each time a mouse movement is identified while dragging which gives the illusion that the cards are moving on the screen.
     * Updating the coordinates of the dragged items is in condition that the coordinates don't exceed the screen's limits.
     * in this case the coordinates that the items will receive will be the frame's closest coordinates.
     * @param me Object that gives details of the mouse features while the mouse is dragged  
     * @param newX the card's  new x coordinate, measured in pixels
     * @param newY the card's  new y coordinate, measured in pixels
     * @param space how much to add the the next card's y coordinate
     * @param card card in the dragged list
     */
    @Override
    public void mouseDragged(MouseEvent me) 
    {
        if (getDraggedCard() != null || getSubList()!=null) //if something is dragged
        {
            int newX = me.getX() - getDragfromX();
            int newY = me.getY() - getDragfromY();
            newX = Math.max(newX, 0); //make sure the card don't passes the left border
            newX = Math.min(newX, getWidth() - Card.CARD_WIDTH); //make sure the card don't passes the right border
        
            if (getDraggedCard() != null) //one card is dragged
            {  
                newY = Math.max(newY, 0); //make sure the card don't passes the top border
                newY = Math.min(newY, getHeight() - Card.CARD_HEIGHT); //make sure the card don't passes the bottom border
                getDraggedCard().setPosition(newX, newY);
                this.repaint();
            }

            if(getSubList()!=null) //list is dragged
            {
                newY = Math.max(newY, 0-( (getDraggedPile().size()*18) - 
                        (getSubList().size()*18) )); //make sure the cards don't passes the top border
                newY = Math.min(newY, getHeight()- 
                        (18*(getDraggedPile().size()-1) + Card.CARD_HEIGHT)); //make sure the cards don't passes the bottom border
                int space=(18*getDraggedPile().size())-(18*getSubList().size()) ;

                for(Card card : getSubList())  //set the dragged cards new locations
                {
                    card.setPosition(newX, newY+space);
                    space+=18;
                }
                this.repaint();
            }
        }
    }
    
    /**
     * <b>This method is responsible of executing the mouse events while the mouse is released</b><br>
     * when a card or a dragged list release is identified, the release location is checked.
     * If in that location there is a pile of cards the legality of adding the dragged card to that pile is being checked.
     * If adding the card is legal the card will be added to the end of that pile of cards.
     * If adding the card is illegal or the release wasn't executed on any card's pile the card will return to its original pile.
     * @param me object that gives details on the mouse features while the mouse is released 
     * @param x coordinate X which the card was released in, measured in pixels
     * @param y coordinate Y which the card was released in, measured in pixels
     * @param targetPile the pile which the card was released on
     */
    @Override
    public void mouseReleased(MouseEvent me) 
    {
        int x = me.getX(); //saves the click coordination
        int y = me.getY();
        
        if (getDraggedCard() != null) //case of dragged card
        {
            CardPile targetPile = findPileAt(x, y); //find the target pile
            if (targetPile != null && targetPile.getClass() != getCardTable() //if the target pile is not the deck or the fake deck
                    .getDeck().getClass() && targetPile.getClass() != 
                    getCardTable().getFakeDeck().getClass() ) 
            {
                getCardTable().moveFromPileToPile
                (getDraggedFromPile(),targetPile);
            }        
        }
        
        if (getSubList() != null) //case of dragged list
        {
            if(findPileAt(x,y)!=null && findPileAt(x, y).getClass() == 
                    getCardTable().getStoragePile(0).getClass()) //if the target pile is a storage pile only
            {
                StorageCell targetPile = (StorageCell)findPileAt(x, y); //find the specific storage pile
                if (targetPile != null) 
                {
                    getCardTable().moveFromListToPile(getSubList(),  //move the list
                            getDraggedPile(),targetPile);     
                }   
            }
        }
        
        this.repaint();
        clearDrag(); //clear the dragged items
    }
    
    /**
     * <b>this method checks rather the coordinate its getting is in a card's pile range</b><br>
     * operated by using this method {@linkplain CardPile#isInside(int, int) CardPile.isInside()}.
     * @param x the x coordinate, measured in pixels
     * @param y the y coordinate, measured in pixels
     * @param pile a pile from the board that is being checked rather the coordinate is in its range or not
     * @return the pile which the coordinate is in its range. can be null.
     */
    private CardPile findPileAt(int x, int y) //find specific pile by coordination
    {
        for ( CardPile pile : getCardTable() )
        {
            if (pile.isInside(x, y)) 
            {
                return pile;
            }
        }
        
        return null; //if no pile was found
    }
    
    /**
     * <b>This method is responsible of executing the mouse events when the mouse entered the window's range</b><br>
     * This method wasn't implemented in this class.
     * @param me an object that gives details on the mouse's features while the mouse entered the window's range
     */
    @Override
    public void mouseEntered(MouseEvent me) 
    {
    }
    
    /**
     * <b>This method is responsible of executing the mouse events when the mouse exited the window's range</b><br>
     * This method wasn't implemented in this class.
     * @param me  an object that gives details on the mouse's features while the mouse exited the window's range
     */
    @Override
    public void mouseExited(MouseEvent me) 
    {
    }
    
    /**
     * <b>This method is responsible of executing the mouse events when the mouse is moving in the window's range</b><br>
     * This method wasn't implemented in this class.
     * @param me an object that gives details on the mouse's features while the mouse is moving in the window's range
     */
    @Override
    public void mouseMoved(MouseEvent me) 
    {
    }    
    
    /**
     * This method is responsible to reset the variables {@linkplain CardsPanel#draggedCard draggedCard}, 
     * {@linkplain CardsPanel#subList subList} and {@linkplain CardsPanel#draggedFromPile draggedFromPile} with null value.
     */
    private void clearDrag() //clean the dragged items
    {
        setDraggedCard(null);
        setSubList(null);
        setDraggedFromPile(null);
    }
    
    /**
     * This method return the cardTable
     * @return the cardTable
     */
    public CardTable getCardTable()
    {
        return cardTable;
    }
    
    /**
     * This method sets the cardTable
     * @param cardTable the current game's cardTable
     */
    public void setCardTable(CardTable cardTable)
    {
        this.cardTable = cardTable;
    }
    
    /**
     * This method return the dragged card
     * @return the current draggedCard
     */
    public Card getDraggedCard()
    {
        return draggedCard;
    }
    
    /**
     * This method sets the dragged card
     * @param inDraggedCard the current draggedCard
     */
    public void setDraggedCard(Card inDraggedCard)
    {
        draggedCard = inDraggedCard;
    }
    
    /**
     * This method return were the dragged item was dragged from
     * @return the source pile of the dragged card
     */
    public CardPile getDraggedFromPile()
    {
        return draggedFromPile;
    }
    
    
    /**
     * This method sets the source pile of the dragged card
     * @param inDraggedPile the current dragged item's source pile
     */
    public void setDraggedFromPile(CardPile inDraggedPile)
    {
        draggedFromPile = inDraggedPile;
    }
    
    
    /**
     * This method return the current dragged pile
     * @return the current dragged pile
     */
    public StorageCell getDraggedPile()
    {
        return draggedPile;
    }
    
    /**
     * This method sets the current dragged pile
     * @param inDraggedPile the current dragged pile
     */
    public void setDraggedPile(StorageCell inDraggedPile)
    {
        draggedPile = inDraggedPile;
    }
    
    /**
     * This method return the x coordinate of the source pile's
     * @return the x coordinate of the source pile's, measured in pixels
     */
    public int getDragfromX()
    {
        return dragFromX;
    }
    
    /**
     * This method sets the x coordinate of the source pile's
     * @param xPosition the x coordinate of the source pile's, measured in pixels
     */
    public void setDragfromX(int xPosition)
    {
        dragFromX = xPosition;
    }
    
    /**
     * This method return the y coordinate of the source pile's
     * @return the y coordinate of the source pile's, measured in pixels
     */
    public int getDragfromY()
    {
        return dragFromY;
    }
    
    /**
     * This method sets the y coordinate of the source pile's
     * @param yPosition the y coordinate of the source pile's, measured in pixels
     */
    public void setDragfromY(int yPosition)
    {
        dragFromY = yPosition;
    }
    
     /**
     * This method return the current dragged list
     * @return the current draggedList
     */
    public List<Card> getSubList() 
    {
        return subList;
    }

    /**
     * This method sets the current dragged list
     * @param inDraggedList the current dragged list
     */
    public void setSubList(List<Card> inSubList) 
    {
        subList = inSubList;
    }  
}
