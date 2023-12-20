import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Boosts class
 * 
 * This class is used to read boosts from file and store them in an ArrayList
 * 
 * @author Daniel Hardej
 * @version 1.0
 * @since 2023-12-02
 * @see Grid
 * Last Updated: 2023-12-02
 * 
 */

public class Boosts {

    private ArrayList<int[]> boosts;
    private String BOOSTS_FILE_NAME;
    private int numberOfBoosts;
    
    /**
     * Default constructor for objects of class Boosts
     */
    public Boosts()
    {
        this.BOOSTS_FILE_NAME = "boosts.txt";
        this.numberOfBoosts = getNumberOfBoosts();
        this.boosts = new ArrayList<int[]>();
    }

    /**
     * Returns all boosts
     * 
     * @return ArrayList<int[]> All boosts, as an ArrayList of arrays of integers in the format: [damageBoost, defenceBoost, coinBoost]
     */
    public ArrayList<int[]> getAllBoosts()
    {
        return this.boosts;
    }

    /**
     * Returns a boost at a given index
     * 
     * @param index The index of the boost to be returned
     * @return int[] The boost at the given index, as an array of integers in the format: [damageBoost, defenceBoost, coinBoost]
     */
    public int[] getBoost(int index)
    {
        return this.boosts.get(index);
    }

    /**
     * Returns the number of boosts in the boosts file
     * 
     * @return int The number of boosts
     */
    public int getNumberOfBoosts()
    {
        FileIO fileIO = new FileIO();
        int numberOfBoosts = fileIO.getNumLines(this.BOOSTS_FILE_NAME);
        return numberOfBoosts;
    }

    /**
     * Reads boosts from file and returns an array of Boosts objects
     * 
     * @return ArrayList<int[]> An array of Boosts objects
     */
    public ArrayList<int[]> readBoostsFromFile()
    {
        ArrayList<int[]> boosts = new ArrayList<int[]>();
        FileIO fileIO = new FileIO();
        try {
            Scanner fileScanner = fileIO.getFileScanner(this.BOOSTS_FILE_NAME);
            for (int i = 0; i < this.numberOfBoosts; i++) {
                String[] boostLine = fileScanner.nextLine().split(",");
                int currentDamageBoost = Integer.parseInt(boostLine[0]);
                int currentDefenceBoost = Integer.parseInt(boostLine[1]);
                int currentCoinBoost = Integer.parseInt(boostLine[2]);
                int[] currentBoost = {currentDamageBoost, currentDefenceBoost, currentCoinBoost};
                try {
                    boosts.add(currentBoost);
                } catch (Exception e) {
                    System.err.println("Error in adding boost: " + e.getMessage());
                }
                
            }
            fileScanner.close();
        } catch (Exception e) {
            System.err.println("Error in reading boosts file: " + e.getMessage());
        }
        return boosts;
    }

}
