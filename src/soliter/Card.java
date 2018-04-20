package soliter;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * <h1>This class describes the card object<h1> 
 * <p>It has all the usual features of a card, such as shape, color and value, 
 * as well as other features; The image of the card, in which piles it was, 
 * whether or not the it with face-up or down and find out if a certain 
 * coordinates is inside it's area.<br>
 * Object of this class can be displayed graphically.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0
 */
public class Card 
{   
    /**
     * <b>The width of the card, measured in pixels</b><br>
     * Used to control the graphic elements in addition to check if the mouse
     * click occurred in the area of the card.
     */
    public static final int CARD_WIDTH; 
    
    /**
     * <b>The height of the card, measured in pixels</b><br>
     * Used to control the graphic elements in addition to check if the mouse
     * click occurred in the area of the card.
     */
    public static final int CARD_HEIGHT;
    
    /**
     * <b>The back image of the card.</b><br>
     * This image displayed when the card is upside down.
     */
    private static final ImageIcon BACK_IMAGE;  

    static 
    {        
    	/**
    	 * the class loader. used to get the resource of the image path.
    	 */
        ClassLoader CLSLDR = Card.class.getClassLoader();
        
        /**
         * <b>The path to the card's back image.</b>
         */
        String urlPath = "soliter\\cardimages\\b.png";
        
        /**
         * used to load the current card's imageIcon from the file.
         */
        java.net.URL imageURL = CLSLDR.getResource(urlPath);
        
        /**
         * <b>The assignment of the back image.</b>
         */
        BACK_IMAGE = new ImageIcon(imageURL);
        
         /**
         * <b>The assignment of the card's width, measured in pixels</b><br>
         * Determined by the width of the back image, in the variable 
         * <i>BACK_IMAGE</i>
         */
        CARD_WIDTH  = BACK_IMAGE.getIconWidth(); 
        
        /**
         * <b>The assignment of the card's height, measured in pixels</b><br>
         * Determined by the height of the back image, in the variable 
         * <i>BACK_IMAGE</i>
         */
        CARD_HEIGHT = BACK_IMAGE.getIconHeight();
    }

    /**
     * <b>The card's color.</b> Can be only <i>"Red"</i> or <i>"Black"</i>.<br> 
     * Determined by the value of the shape, which contained in the variable 
     * {@link Card#SHAPE}.  
     * If the value of shape is <i>"h"</i> or <i>"d"</i> than the color will be red. else, the color will be black.
     */
    private final String COLOR;
    
    /**
     * <b>The card's shape.</b> Can be <i>"h"</i>, <i>"d"</i>, <i>"s"</i> or 
     * <i>"c"</i>.
     */
    private final String SHAPE;
    
    /**
     * <b>The card's value.</b> Can be a number from 1 to 13.
     */
    private final int VALUE;
    
    /**
     * <b>The card's image.</b> The image of the card when it is face-up.
     */
    private final ImageIcon CARD_IMAGE;
    
    /**
     * <b>The X position of the card, measured in pixels</b> Used for drawing the card in the 
     * wanted location.
     */
    private int xPosition;
    
    /**
     * <b>The Y position of the card, measured in pixels</b> Used for drawing the card in the 
     * wanted location.
     */
    private int yPosition;
    
    /**
     * <b>Whether or not the card with face-up.</b><br>
     * Used to know whether the card can be clicked.
     */
    private boolean showCard  = false;
    
    /**
     * <b>Whether or not the card was in the house pile.</b><br>
     * Used to calculate the score.
     */
    private boolean visitHouse  = false;
    
    /**
     * <b>Whether or not the card was in the deck.</b><br>
     * Used to calculate the score.
     */
    private boolean visitDeck  = true;
    
    /**
     * <b>Whether or not the card was in a storage pile.</b><br>
     * Used to calculate the score.
     */
    private boolean visitStorage  = false;

    /**
     * <b>class's constructor</b><br>
     * Initializes the card's values.
     * @param shape The card's shape
     * @param value The card's value
     * @param cardImage The card's image
     */
    public Card(String shape, int value, ImageIcon cardImage) //constructor
    {
       this.SHAPE=shape;
       this.VALUE=value;
       this.CARD_IMAGE=cardImage;
       
       if(shape.compareTo("h")==0 || shape.compareTo("d")==0) // set color by shape
       {
           COLOR = "Red";
       }
       else
       {
           COLOR="Black";
       }
    }
    
    /**
     * <b>This method returns the card's value.</b><br>
     * @return The card's value
     */
    public int getValue() 
    {
        return VALUE;
    }
    
    /**
     * <b>This method returns the card's shape.</b><br>
     * @return The card's shape
     */
    public String getShape() 
    {
        return SHAPE;
    }
    
    /**
     * <b>This method returns the card's color.</b><br>
     * @return The card's color
     */
    public String getColor() 
    {
        return COLOR;
    }

    /**
     * <b>This method sets the card's coordinates for the drawing.</b><br>
     * @param x the card's X coordination, measured in pixels
     * @param y the card's Y coordination, measured in pixels
     */
    public void setPosition(int x, int y) 
    {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * <b>This method draws the card's image.</b><br>
     * Draws the back of the card or the front of it by the return value of the
     * method {@link Card#isShowingCard()}.
     * @see Graphics                    
     * @param g Graphic object used to draw the card's image.
     */
    public void draw(Graphics g)  
    {
        if (isShowingCard()) // draw the card
        {
            getCardImage().paintIcon(null, g, getX(), getY());
        } 
        else // draw the card's back
        {
            BACK_IMAGE.paintIcon(null, g, getX(), getY());
        }
    }

    /**
     * <b>This method finds out whether or not a certain coordinates is inside
     * the card's area.</b><br> 
     * Used to diagnosis clicks on the cards.
     * @param x the card's X coordination, measured in pixels
     * @param y the card's Y coordination, measured in pixels
     * @return whether or not the coordinates is inside the card's area.
     */
    public boolean isInside(int x, int y) // check if point is inside card are
    {
        return (x >= this.getX() && x < this.getX()+CARD_WIDTH) && 
                (y >= this.getY() && y < this.getY()+CARD_HEIGHT);
    }

    /**
     * <b>This method return the card's X coordination.</b>
     * @return the card's X coordination, measured in pixels
     */
    public int getX() 
    {
        return xPosition;
    }
    
    /**
     * <b>This method return the card's Y coordination.</b>
     * @return the card's Y coordination, measured in pixels
     */
    public int getY() 
    {
        return yPosition;
    }

    /**
     * <b>This method sets the card's X coordination.</b>
     * @param x the card's X coordination, measured in pixels
     */
    public void setX(int x) 
    {
        this.xPosition = x;
    }
    
    /**
     * <b>This method sets the card's Y coordination.</b>
     * @param x the card's Y coordination, measured in pixels
     */
    public void setY(int y) 
    {
        this.yPosition = y;
    }

    /**
     * <b>This method sets whether the card with face-up or down.</b>
     * @param mod whether to set the card with face-up or down
     */
    public void setShowCard(boolean mod) 
    {
        showCard = mod;
    }
    
    /**
     * <b>This method returns whether the card with face-up or down.</b>
     * @return whether the card with face-up or down.
     */
    public boolean isShowingCard() 
    {
        return showCard;
    }
    
    /**
     * <b>This method return whether or not the card was in the deck.</b>
     * @return whether or not the card was in the deck
     */
    public boolean isVisitDeck() 
    {
        return visitDeck;
    }
    
    /**
     * <b>This method return whether or not the card was in the house pile.</b>
     * @return whether or not the card was in the house pile
     */
    public boolean isVisitHouse() 
    {
        return visitHouse;
    }
    
    /**
     * <b>This method return whether or not the card was in a storage pile.</b>
     * @return whether or not the card was in a storage pile
     */
    public boolean isVisitStorage() 
    {
        return visitStorage;
    }
    
    /**
     * <b>This method sets whether or not the card was in the deck.</b>
     * @param visitDeck whether or not the card was in the deck
     */
    public void setVisitDeck(boolean  visitDeck) // mark is the card was in the deck
    {
        this.visitDeck = visitDeck;
    }
    
    /**
     * <b>This method sets whether or not the card was in the house pile.</b>
     * @param visitDeck whether or not the card was in the house pile
     */
    public void setVisitHouse(boolean visitHouse) // mark is the card was in house cell
    {
        this.visitHouse = visitHouse;
    }
    
    /**
     * <b>This method sets whether or not the card was in a storage pile.</b>
     * @param visitDeck whether or not the card was in a storage pile
     */
    public void setVisitStorage(boolean visitStorage) // mark is the card was in storage cell
    {
        this.visitStorage =visitStorage;
    }
    
    /**
     * <b>This method returns the card's image.</b>
     * @return the card's image
     */
    public ImageIcon getCardImage()
    {
        return CARD_IMAGE;
    }
}