import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Player class represents a player in the game
 * 
 * @version 1.0
 * @author Daniel Hardej
 * Last updated: 2023-11-22
 */

public class Player
{
    private int coins;
    private int damage;
    private int defence;
    private boolean hasCompletePath;
    private int hearts;
    private boolean isHuman;
    private String name;
    private int numSquaresLost;
    private ArrayList<int[]> squaresCaptured;

    /**
     * Default constructor for the Player class
     */
    public Player()
    {
        this.name  = "Player";
        this.coins = 0;
        this.defence = 0;
        this.hearts = 3;
        this.squaresCaptured = new ArrayList<int[]>();
        this.isHuman = true;
        this.hasCompletePath = false;
    }

    /**
     * Parameterised constructor for the Player class
     * 
     * @param name The name of the player
     * @param damage The damage stat of the player
     * @param defence The defence stat of the player
     * @param coins The number of coins the player has
     * @param hearts The number of hearts the player has (default = 3)
     * @param isHuman Whether the player is human or not
     */
    public Player(String name, int damage, int defence, int coins, int hearts, boolean isHuman)
    {
        this.name = name;
        this.damage = damage;
        this.defence = defence;
        this.coins = coins;
        this.hearts = hearts;
        this.squaresCaptured = new ArrayList<int[]>();
        this.isHuman = isHuman;
        this.hasCompletePath = false;
    }

    /**
     * Mutator method to add a new captured square to the player's list of captured squares
     * 
     * @param squareCoordinates The coordinates of the square to be added
     */
    public void addSquareCaptured(int[] squareCoordinates)
    {
        this.squaresCaptured.add(squareCoordinates);
    }

    /**
     * Method to display the player's stats for a given grid
     * 
     * @param grid The grid object
     */
    public void displayPlayerStats(Grid grid)
    {
        System.out.print("Name: " + this.getName());
        System.out.print(" | Captured: " + this.getNumSquaresCaptured());
        System.out.print(" | Damage: " + this.getDamage());
        System.out.print(" | Defence: " + this.getDefence());
        System.out.print(" | Coins: " + this.getCoins());
        System.out.print(" | Hearts: " + this.getHearts());
        ArrayList<int[]> squaresCaptured = this.getSquaresCaptured();
        System.out.print(" | Squares captured: ");
        for (int[] square : squaresCaptured) {
            System.out.print(Arrays.toString(square) + " ");
        }
        System.out.print(" | Has complete path: " + this.hasCompletePath(grid) + "\n");
    }

    /**
     * Getter method for the coins attribute
     * 
     * @return The number of coins the player has
     */
    public int getCoins()
    {
        return this.coins;
    }

    /**
     * Getter method for the attack attribute
     * 
     * @return The attack stat of the player
     */
    public int getDamage()
    {
        return this.damage;
    }

    /**
     * Getter method for the defence attribute
     * 
     * @return The defence stat of the player
     */
    public int getDefence()
    {
        return this.defence;
    }

    /**
     * Getter method for the hearts attribute
     * 
     * @return The number of hearts the player has as a string containing heart symbols
     */
    public String getHearts()
    {   
        String heartString = "";
        int numHearts = this.hearts;
        for (int i = 0; i < numHearts; i++)
        {
            heartString += " ♥ ";
        }
        return heartString;
    }

    public boolean getIsHuman()
    {
        return this.isHuman;
    }

    /**
     * Getter method for the name attribute
     * 
     * @return The name of the player
     */
    public String getName()
    {
        return this.name;
    }
    /**
     * Getter method for the hearts attribute
     * 
     * @return The number of hearts the player has
     */
    public int getNumHearts()
    {
        return this.hearts;
    }

    /**
     * Getter method for the squaresCaptured attribute
     * 
     * @return The squares captured by the player
     */
    public int getNumSquaresCaptured()
    {
        int count = 0;
        for (int i = 0; i < this.squaresCaptured.size(); i++)
        {
            count++;
        }
        return count;
    }

    /**
     * Method to display the player's stats
     * 
     * @param grid The grid object
     * @return A string containing the player's stats
     */
    public String getPlayerStats(Grid grid)
    {
        String playerStats = "";
        playerStats += "Name: " + this.getName();
        playerStats += " | Captured: " + this.getNumSquaresCaptured();
        playerStats += " | Damage: " + this.getDamage();
        playerStats += " | Defence: " + this.getDefence();
        playerStats += " | Coins: " + this.getCoins();
        playerStats += " | Hearts: " + this.getHearts();
        ArrayList<int[]> squaresCaptured = this.getSquaresCaptured();
        playerStats += " | Squares captured: ";
        for (int[] square : squaresCaptured) {
            playerStats += Arrays.toString(square) + " ";
        }
        playerStats += " | Has complete path: " + this.hasCompletePath(grid) + "\n";
        return playerStats;
    }

    /**
     * Getter method for the squaresCaptured attribute
     * 
     * @return An ArrayList of the coordinates of the squares captured by the player
     */
    public ArrayList<int[]> getSquaresCaptured()
    {
        return this.squaresCaptured;
    }

    /**
     * Getter method indicating whether the player has captured a given square on the grid
     * 
     * @param squareCoordinates The coordinates of the square to check
     * @param grid The grid object
     * @return boolean indicating whether the player has captured the square or not
     */
    public boolean hasCapturedSquare(int[] squareCoordinates, Grid grid)
    {
        String gridSquare = grid.getGridSquare(squareCoordinates[0], squareCoordinates[1]);
        return gridSquare.contains(this.getName());
    }

    /**
     * Method to check whether the player has a captured a complete path across the grid,
     * either horizontally or vertically.
     * 
     * This method will also count the total number of complete paths captured by the player
     * and update the numCompletePathsCaptured attribute accordingly.
     * 
     * @param grid The grid object
     * @return boolean indicating whether the player has a complete path or not
     * 
     */
    public boolean hasCompletePath(Grid grid)
    {
        int horizontalCount = 0;
        int verticalCount = 0;
        int completePathCount = 0;
        for (int i = 0; i < grid.getGridSize(); i++)
        {
            for (int j = 0; j < grid.getGridArray().get(i).length; j++)
            {
                if (grid.getGridSquare(i + 1, j + 1).contains(this.getName()))
                {
                    horizontalCount++;
                }
            }
            if (horizontalCount == grid.getGridArray().get(i).length)
            {
                this.hasCompletePath = true;
                completePathCount++;
                return true;
            }
            horizontalCount = 0;
        }
        
        for (int i = 0; i < grid.getGridSize(); i++)
        {
            for (int j = 0; j < grid.getGridArray().size(); j++)
            {
                if (grid.getGridSquare(j + 1, i + 1).contains(this.getName()))
                {
                    verticalCount++;
                }
            }
            if (verticalCount == grid.getGridArray().size())
            {
                this.hasCompletePath = true;
                completePathCount++;
                return true;
            }
            verticalCount = 0;
        }
        return false;
    }
    
    /**
     * Setter method for the coins attribute
     * 
     * @param coins The number of coins the player has
     */
    public void setCoins(int newCoins)
    {
        this.coins = newCoins;
    }

    /**
     * Setter method for the damage attribute
     * 
     * @param newDamage The attack stat of the player
     */
    public void setDamage(int newDamage)
    {
        this.damage = newDamage;
    }

    /**
     * Setter method for the defence attribute
     * 
     * @param defence The defence stat of the player
     */
    public void setDefence(int newDefence)
    {
        this.defence = newDefence;
    }

    /**
     * Setter method for the hearts attribute
     * 
     * @param hearts The number of hearts the player has
     */
    public void setHearts(int newHearts)
    {
        this.hearts = newHearts;
    }

    /**
     * Setter method for the name attribute
     * 
     * @param name The name of the player
     */
    public void setName(String newName)
    {
        this.name = newName;
    }

    /**
     * Mutator method to update the player's coins
     * 
     * @param coinsModifier The amount to increase/decrease the player's coins by
     */
    public void updateCoins(int coinsModifier)
    {
        this.coins += coinsModifier;
    }

    /**
     * Mutator method to update the player's damage stat
     * 
     * @param damageModifier The amount to increase/decrease the player's damage by
     */
    public void updateDamage(int damageModifier)
    {
        this.damage += damageModifier;
    }

    /**
     * Mutator method to update the player's defence stat
     * 
     * @param defenceModifier The amount to increase/decrease the player's defence by
     */
    public void updateDefence(int defenceModifier)
    {
        this.defence += defenceModifier;
    }

    /**
     * Mutator method to update the player's hearts
     * 
     * @param heartsModifier The amount to increase/decrease the player's hearts by
     */
    public void updateHearts(int heartsModifier)
    {
        this.hearts += heartsModifier;
    }

    /**
     * Mutator method to remove a captured square from the player's list of captured squares
     * 
     * @param squareCoordinates The coordinates of the square to be removed
     */
    public void removeSquareCaptured(int[] squareCoordinates)
    {
        this.squaresCaptured.remove(squareCoordinates);
    }
}
