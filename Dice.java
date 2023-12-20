import java.lang.Math;

/**
 * Dice class
 * 
 * This class is used to generate random numbers for the game, specifically when
 * choosing a random move for the computer and also when determining the attack
 * stats of players when the sabotage enemy moves are chosen.
 * 
 * Java's Math class is used to generate random numbers.
 * 
 * @author Daniel Hardej
 * @version 1.0
 * @since 2023-11-28
 * @see Field
 * Last updated: 2023-12-02
 */

public class Dice {

    /**
     * Emulates a dice roll, returning a random number between 1 and 2.
     * 
     * @return A random number between 1 and 2
     */
    public static int D2()
    {
        return (int) (Math.random() * 2 + 1);
    }

    /**
     * Emulates a dice roll, returning a random number between 1 and 3.
     * 
     * @return A random number between 1 and 3
     */
    public static int D3()
    {
        return (int) (Math.random() * 3 + 1);
    }

    /**
     * Emulates a dice roll, returning a random number between 1 and 6.
     * 
     * @return A random number between 1 and 6
     */
    public static int D6()
    {
        return (int) (Math.random() * 6 + 1);
    }

    /**
     * Emulates a roll of two six-sided dice, returning the sum of the two dice.
     * 
     * @return the sum of two six-sided dice
     */
    public static int twoD6()
    {
        return D6() + D6();
    }

    /**
     * Emulates a roll of three six-sided dice, returning the sum of the three dice.
     * 
     * @return the sum of three six-sided dice
     */

    public static int threeD6()
    {
        return D6() + D6() + D6();
    }

    /**
     * Generates a random number between a minimum and maximum value.
     * 
     * @param min The minimum value of the random number
     * @param max The maximum value of the random number
     * 
     * @return A random number between the minimum and maximum values
     */
    public static int getRandomNumber(int min, int max)
    {
        return (int) (Math.random() * (max - min + 1)) + min;
    }
}
