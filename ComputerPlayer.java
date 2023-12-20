/**
 * ComputerPlayer class
 * 
 * A class that represents the computer player in the game. The computer player is a subclass of the Player class.
 * 
 * When the game begins, both players are given a damage stat of 5 and defence stat of 7.
 * The computer player starts with a total of 10,000 coins.
 * 
 * @author Daniel Hardej
 * @version 1.0
 * @since 2023-11-28
 * @see Player
 * Last Modified: 2023-12-02
 * 
 */

public class ComputerPlayer extends Player
{
    /**
     * Default constructor for objects of class ComputerPlayer. No other constructor, parameters, 
     * or methods are needed, as the boosts and player stats for the computer player are pre-determined 
     * for every game, and all methods that are needed are inherited from the Player class.
     * 
     * When the game begins, both players are given a damage stat of 5 and defence stat of 7.
     * The computer player starts with a total of 10,000 coins.
     * 
     */
    public ComputerPlayer()
    {
        super("Computer", 7, 5, 10000, 3, false);
    }
}
