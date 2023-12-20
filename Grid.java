import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class used to create, define, modify, and display the game grid.
 * 
 * The grid is represented as an ArrayList of String arrays, where each String array represents a row of the grid.
 * Each String array contains the String representation of each square in the row.
 *
 * The grid is created by the createGrid() method, which takes an ArrayList of boost coordinates as a parameter.
 * 
 * The grid size is set by the user, and must be between 3 and 10.
 *
 * The grid is displayed by the displayGrid() method and updated by different methods, depending on the context in the game.
 * 
 * @author Daniel Hardej
 * @version 1.0
 * @since 2023-11-22
 * @see Boost
 * @see Field
 * Last Modified: 2023-12-01 
 */

public class Grid 
{
    private final int COORDINATE_ARRAY_SIZE = 2;
    private int columns;
    private ArrayList<String[]> gridArray;
    private int gridSize;
    private final int MAX_GRID_SIZE = 10;
    private final int MIN_GRID_SIZE = 3;
    private int rows;

    /**
     * Default constructor for objects of class Grid. Promts the user to enter a grid size,
     * and then creates a grid of that size.
     */
    public Grid()
    {
        getGridSizeFromUser();
        this.gridArray = this.createGrid(this.getBoostCoordinates());
    }

    /**
     * Constructor for objects of class Grid
     * 
     * @param gridSize The size of the grid
     */
    public Grid(int gridSize)
    {
        this.rows = gridSize;
        this.columns = gridSize;
        this.gridArray = this.createGrid(this.getBoostCoordinates());
    }

    /**
     * Method to create a grid of the given size, with a given set of boosts with defined coordinates.
     * 
     * This method creates the grid as an ArrayList of String arrays, where each String array represents a row of the grid.
     * Each String array contains the String representation of each square in the row.
     * The boosts are read from the file, assigned random coordinates within the grid, and then placed on the grid in
     * accordance withg the coordinates passed as the parameter.
     * 
     * @param boostCoordinates The coordinates of the boosts
     * @return ArrayList<String[]> The grid
     */
    public ArrayList<String[]> createGrid(ArrayList<int[]> boostCoordinates)
    {
        ArrayList<String[]> grid = new ArrayList<String[]>();
        Boosts boosts = new Boosts();
        ArrayList<int[]> boostsList = boosts.readBoostsFromFile();
        int[] currentBoost = new int[3];
        for (int i = 0; i < rows; i++)
        {
            String[] row = new String[columns];
            for (int j = 0; j < columns; j++)
            {
                boolean isBoost = false;
                for (int k = 0; k < boostCoordinates.size(); k++)
                {
                    if (Arrays.equals(boostCoordinates.get(k), new int[]{i+1, j+1}))
                    {
                        isBoost = true;
                        currentBoost = boostsList.get(k);
                        break;
                    }
                }
                if (isBoost)
                {
                    row[j] = String.format(" [ %3d, %3d, %5d ] ", currentBoost[0], currentBoost[1], currentBoost[2]);
                }
                else
                {
                    row[j] = String.format(" [ %3d, %3d, %5d ] ", 0, 0, 0);
                }
                row[j] = String.format("%1$-12s", row[j]);
            }
            grid.add(row);
        }
        return grid;
    }

    /**
     * Method to display the game grid.
     * 
     */
    public void displayGrid() {
        System.out.print("    ");
        for (int i = 0; i < this.gridArray.get(0).length; i++) {
            System.out.printf("         %2d          ", i + 1);
        }
        System.out.println("\n");
        int rowNumber = 1;
        for (String[] row : this.gridArray) {
            System.out.printf("%-4d", rowNumber++);
            for (String square : row) {
                System.out.print(square);
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Getter method to return an ArrayList of the coordinates of the boosts.
     * 
     * @return ArrayList<int[]> The coordinates of the boosts
     */
    public ArrayList<int[]> getBoostCoordinates()
    {
        int upperLimit = getGridSize();
        int lowerLimit = 1;
        Boosts boosts = new Boosts();
        int numberOfBoosts = boosts.getNumberOfBoosts();
        ArrayList<int[]> boostCoordinates = new ArrayList<int[]>();
        for (int i = 0; i < numberOfBoosts; i++)
        {
            int[] boostCoordinate;
            boolean unique;
            do {
                boostCoordinate = new int[COORDINATE_ARRAY_SIZE];
                boostCoordinate[0] = (int) (Math.random() * (upperLimit - lowerLimit + 1) + lowerLimit);
                boostCoordinate[1] = (int) (Math.random() * (upperLimit - lowerLimit + 1) + lowerLimit);
                unique = true;
                for (int[] coordinate : boostCoordinates) {
                    if (Arrays.equals(coordinate, boostCoordinate))
                    {
                        unique = false;
                        break;
                    }
                }
            } while (!unique);
            boostCoordinates.add(boostCoordinate);
        }
        return boostCoordinates;
    }

    public ArrayList<String[]> getGridArray()
    {
        return this.gridArray;
    }

    /**
     * Getter method for the grid size.
     * 
     * @return int The grid size
     */
    public int getGridSize()
    {
        if (this.rows == this.columns) {
            this.gridSize = this.rows;
        } else {
            System.out.println("Error: Grid is not square.");
            System.out.println("Setting default grid size (5).");
            int gridSize = 5;
            setGridSize(gridSize);
        }
        return gridSize;
    }

    /**
     * Prompts the user to enter a grid size, and then sets the grid size to the user's input.
     * 
     * @return void
     */
    public void getGridSizeFromUser()
    {
        int gridSize;
        try {
            Input input = new Input();
            Validation inputValidator = new Validation();
            gridSize = input.getIntegerInput("Enter the grid size: ");
            while (!inputValidator.validateIntegerValue(gridSize, MIN_GRID_SIZE, MAX_GRID_SIZE) || inputValidator.isBlank(gridSize)) {
                System.out.println("Error: Grid size must be an integer between " + MIN_GRID_SIZE + " and " + MAX_GRID_SIZE + ".");
                gridSize = input.getIntegerInput("Enter the grid size: ");
            }
            setGridSize(gridSize);
        } catch (Exception e) {
            System.err.println("Error capturing grid size: " + e.getMessage());
            System.out.println("Setting default grid size (5).");
            gridSize = 5;
        }
    }

    /**
     * Setter method for the grid size.
     * 
     * @param newGridSize The new grid size
     */
    public void setGridSize(int newGridSize)
    {
        this.rows = newGridSize;
        this.columns = newGridSize;
        System.out.println("Grid size set to " + this.rows + " x " + this.columns + "." );
    }

    /**
     * Returns the square at the given coordinates.
     * 
     * @param xCoordinate The x coordinate of the square
     * @param yCoordinate The y coordinate of the square
     * @return String The square at the given coordinates
     */
    public String getGridSquare(int xCoordinate, int yCoordinate) {
        if (xCoordinate < 1 || xCoordinate > this.gridArray.get(0).length || yCoordinate < 1 || yCoordinate > this.gridArray.size()) {
            throw new IllegalArgumentException("Coordinates are out of bounds.");
        }
        int xCoordinateIndex = xCoordinate - 1;
        int yCoordinateIndex = yCoordinate - 1;
        String[] row = this.gridArray.get(yCoordinateIndex);
        String square = row[xCoordinateIndex];
        return square;
    }

    /**
     * Updates the grid with the player's name in the square that was captured.
     * 
     * @param currentPlayer The player who captured the square
     * @param capturedCoordinates The coordinates of the square that was captured
     * @return void
     */
    public void updateGridWithCapturedSquare(Player currentPlayer, int[] capturedCoordinates) {
        int capturedYCoordinateIndex = capturedCoordinates[0] - 1;
        int capturedXCoordinateIndex = capturedCoordinates[1] - 1;
        String[] capturedRow = gridArray.get(capturedXCoordinateIndex);
        String capturedSquare = capturedRow[capturedYCoordinateIndex];
        String[] capturedSquareBoostArray = capturedSquare.replaceAll("\\[", "").replaceAll("\\]", "").trim().split(",");
        try {
            int damageBoost = Integer.parseInt(capturedSquareBoostArray[0].trim());
            int defenceBoost = Integer.parseInt(capturedSquareBoostArray[1].trim());
            int coinsBoost = Integer.parseInt(capturedSquareBoostArray[2].trim());
            currentPlayer.updateDamage(damageBoost);
            currentPlayer.updateDefence(defenceBoost);
            currentPlayer.updateCoins(coinsBoost);
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        String[] updatedRow = gridArray.get(capturedXCoordinateIndex);
        updatedRow[capturedYCoordinateIndex] = String.format(" [ %1$-15s ] ", currentPlayer.getName());
        gridArray.set(capturedXCoordinateIndex, updatedRow);
    }

    /**
     * Updates the grid with the player's name in the square that was sabotaged.
     * 
     * @param currentPlayer The player who sabotaged the square
     * @param sabotagedCoordinates The coordinates of the square that was sabotaged
     * @return void
     */
    public void updateGridWithSabotagedSquare(Player currentPlayer, int[] sabotagedCoordinates) {
        int capturedYCoordinateIndex = sabotagedCoordinates[0] - 1;
        int capturedXCoordinateIndex = sabotagedCoordinates[1] - 1;
        String[] capturedRow = gridArray.get(capturedXCoordinateIndex);
        String capturedSquare = capturedRow[capturedYCoordinateIndex];
        String[] updatedRow = gridArray.get(capturedXCoordinateIndex);
        updatedRow[capturedYCoordinateIndex] = String.format(" [ %1$-15s ] ", currentPlayer.getName());
        gridArray.set(capturedXCoordinateIndex, updatedRow);
    }
    
}