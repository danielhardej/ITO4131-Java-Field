/**
 * HumanPlayer is a subclass of Player that represents a human player in the game.
 * 
 * When the game begins, the human player is given a damage stat of 5 and defence stat of 7, and starts
 * with a total of 3,000 coins.
 * 
 * @author Daniel Hardej
 * @version 1.0
 * @since 2023-11-28
 * @see Player
 * Last Updated: 2023-12-02
 */

public class HumanPlayer extends Player
{
    /**
     * Default constructor for objects of class HumanPlayer
     */
    public HumanPlayer()
    {
        super("Human", 5, 7, 3000, 3, true);
    }

    /**
     * Parameterised constructor for objects of class HumanPlayer
     * 
     * @param name The name of the player
     */
    public HumanPlayer(String name)
    {
        super(name, 5, 7, 3000, 3, true);
    }
}
