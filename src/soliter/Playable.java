package soliter;

/**
 * <h1>This is an interface, requires those who implement it to implement the method {@link #play()}.</h1>
 * <p>This interface is intended for games.</p>
 * 
 * @author Yael Landau
 * @author Matan Ganani
 * @version 1.0   
 * @see {@linkplain <a href=http://docs.oracle.com/javase/tutorial/java/concepts/interface.html </a> Interface}
 */
public interface Playable 
{
	/**
	 * This method will have to be implemented by who ever implemented this interface.
	 */
    public void play(); 
}
