import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Field class
 * 
 * This class is used as the primary class for the game. It contains the main method. In starting the game,
 * the main method creates a new Field object, which in turn creates a new Grid object, new Player objects
 * are initialised for the human player and the computer player. The main method then commences the game loop,
 * which continues and controls the turns until the game is over when one of the players wins. The game is won
 * when one of the players has hit all three hearts of the other player.
 * 
 * @author Daniel Hardej
 * @version 1.0
 * @since 2023-11-25
 * @see Grid
 * @see Player
 * @see Dice
 * @see FileIO
 * @see Input
 * Last Modified: 2023-12-02
 */

public class Field
{
    private Player computerPlayer;
    private Dice dice;
    private static FileIO fileIO;
    private static boolean gameOver;
    private static Grid grid;
    private Player humanPlayer;
    private static final String outputFileName = "game_log.txt";
    private static int numTurns;

    /**
     * Default constructor for the Field class.
     */
    public Field()
    {
        this.grid = new Grid();
        this.humanPlayer = initialiseHumanPlayer();
        this.computerPlayer = initialiseComputerPlayer();
        this.fileIO = new FileIO();
        this.dice = new Dice();
        this.numTurns = 1;
        this.gameOver = false;
    }

    /**
     * Method to capture a grid spot. Prompts the user for the coordinates of the grid spot they would like to capture,
     * then rolls the dice to determine whether the capture is successful. If the capture is successful, the grid is
     * updated to reflect the capture, and the player's captured squares are updated. If the capture is unsuccessful,
     * the player's attempt fails and the turn ends.
     * 
     * @param currentPlayer The current player.
     * @param opponentPlayer The opponent player.
     * 
     * @see Player#addSquareCaptured(int[])
     */
    public void captureGridSpot(Player currentPlayer, Player opponentPlayer)
    {
        Input playerInput = new Input();
        int[] capturedCoordinates = new int[2];
        if (currentPlayer.getIsHuman())
        {
            System.out.println("Enter the coordinates of the tile you would like to capture.");
            capturedCoordinates[0] = playerInput.getIntegerInput("Enter the x coordinate: ");
            while (capturedCoordinates[0] < 0 || capturedCoordinates[0] > grid.getGridSize())
            {
                capturedCoordinates[0] = playerInput.getIntegerInput("Please enter a valid x coordinate: ");
            }
            capturedCoordinates[1] = playerInput.getIntegerInput("Enter the y coordinate: ");
            while (capturedCoordinates[1] < 0 || capturedCoordinates[1] > grid.getGridSize())
            {
                capturedCoordinates[1] = playerInput.getIntegerInput("Please enter a valid y coordinate: ");
            }
            while (opponentPlayer.hasCapturedSquare(capturedCoordinates, this.grid))
            {
                System.out.println("You cannot capture a tile that has already been captured by your opponent.");
                capturedCoordinates[0] = playerInput.getIntegerInput("Enter the x coordinate: ");
                while (capturedCoordinates[0] < 0 || capturedCoordinates[0] > grid.getGridSize())
                {
                    capturedCoordinates[0] = playerInput.getIntegerInput("Please enter a valid x coordinate: ");
                }
                capturedCoordinates[1] = playerInput.getIntegerInput("Enter the y coordinate: ");
                while (capturedCoordinates[1] < 0 || capturedCoordinates[1] > grid.getGridSize())
                {
                    capturedCoordinates[1] = playerInput.getIntegerInput("Please enter a valid y coordinate: ");
                }
            }
            fileIO.updateFile(outputFileName, currentPlayer.getName() + " is attempting to capture the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!\n");
        }
        else if (!currentPlayer.getIsHuman())
        {
            capturedCoordinates[0] = dice.getRandomNumber(1, grid.getGridSize());
            capturedCoordinates[1] = dice.getRandomNumber(1, grid.getGridSize());
            while (opponentPlayer.hasCapturedSquare(capturedCoordinates, this.grid))
            {
                capturedCoordinates[0] = dice.getRandomNumber(1, grid.getGridSize());
                capturedCoordinates[1] = dice.getRandomNumber(1, grid.getGridSize());
            }
            System.out.println("The computer is attempting to capture the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!");
            fileIO.updateFile(outputFileName, currentPlayer.getName() + " is attempting to capture the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!\n");
        }
        else
        {
            System.out.println("Error: Invalid player type.");
        }
        int currentPlayerAttack = dice.threeD6() + currentPlayer.getDamage();
        int opponentPlayerAttack = dice.twoD6() + opponentPlayer.getDefence();
        System.out.println(currentPlayer.getName() + " has rolled " + currentPlayerAttack +  " and " +
        opponentPlayer.getName() + " has rolled " + opponentPlayerAttack + "!");
        if (currentPlayerAttack > opponentPlayerAttack)
        {
            System.out.println("You have captured the tile!");
            grid.updateGridWithCapturedSquare(currentPlayer, capturedCoordinates);
            currentPlayer.addSquareCaptured(capturedCoordinates);
            opponentPlayer.removeSquareCaptured(capturedCoordinates);
            fileIO.updateFile(outputFileName, currentPlayer.getName() + " has captured the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!\n");
        }
        else if (currentPlayerAttack < opponentPlayerAttack)
        {
            System.out.println("You have failed to capture the tile!");
            fileIO.updateFile(outputFileName, currentPlayer.getName() + " has failed to capture the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!\n");
        }
        else
        {
            System.out.println("You have tied with your opponent!");
            captureGridSpot(currentPlayer, opponentPlayer);
        }
    }

    /**
     * Method to decrement the opponent's attack by 2 if the current player has chosed to sabotage their opponent.
     * 
     * Note: This method is called by the sabotageOpponent method. It differs from the Player method to update the
     * opponent's attack, as it also includes a calculation of the cost of the sabotage, and the logic to determine
     * how to execute the sabotage based on whether it is the human player's or the computer player's turn.
     * 
     * @param currentPlayer The current player.
     * @param opponentPlayer The opponent player.
     * 
     * @see Player#updateDamage()
     */
    public void decrementOpponentAttack(Player currentPlayer, Player opponentPlayer)
    {
        Input playerInput = new Input();
        System.out.println(currentPlayer.getName() + " has chosen to decrement their opponent's attack by 2.\n");
        fileIO.updateFile(outputFileName, currentPlayer.getName() + " has chosen to decrement their opponent's attack by 2.\n");
        int actionCost = getActionCost(500, 1500);
        String acceptSabotageCost = "";
        if (currentPlayer.getIsHuman())
        {
            acceptSabotageCost = playerInput.getStringInput("This will cost you " + actionCost + " coins. Do you accept? (Y/N): ");
        }
        else if (!currentPlayer.getIsHuman())
        {
            acceptSabotageCost = "Y";
        }
        if (acceptSabotageCost.equalsIgnoreCase("Y"))
        {
            if (currentPlayer.getCoins() < actionCost)
            {
                System.out.println("You do not have enough coins to sabotage your opponent's attack.");
                fileIO.updateFile(outputFileName, "Sabotage unsuccessful. " + currentPlayer.getName() + " does not have enough coins to sabotage their opponent's grid.\n");
            }
            else if (currentPlayer.getCoins() >= actionCost)
            {
                opponentPlayer.updateDamage(-2);
                currentPlayer.updateCoins(-actionCost);
                System.out.println("Sabotage successful. " + currentPlayer.getName() + " has decremented their opponent's attack by 2.\n");
                fileIO.updateFile(outputFileName, "Sabotage successful. " + currentPlayer.getName() + " has decremented their opponent's attack by 2.");
                fileIO.updateFile(outputFileName, " Cost of sabotage: " + actionCost + " coins.\n");
            }
            else
            {
                System.out.println("Error: Invalid input.");
            }
        }
        else
        {
            System.out.println("Sabotage unsuccessful. " + currentPlayer.getName() + " has chosen not to sabotage their opponent's grid.\n");
        }
    }

    /**
     * Method to decrement the opponent's defence by 2 if the current player has chosed to sabotage their opponent
     * by decrementing their defence.
     * 
     * Note: This method is called by the sabotageOpponent method. It differs from the Player method to update the
     * player's defence as it includes a calculation to determine the cost of the sabotage and the logic to determine
     * how the sabotage should be done based on whether it is a human or computer player.
     * 
     * @param currentPlayer The current player.
     * @param opponentPlayer The opponent player.
     * 
     * @see Player#updateDefence()
     */
    public void decrementOpponentDefence(Player currentPlayer, Player opponentPlayer)
    {
        Input playerInput = new Input();
        System.out.println(currentPlayer.getName() + " has chosen to decrement their opponent's defence by 2.\n");
        fileIO.updateFile(outputFileName, currentPlayer.getName() + " has chosen to decrement their opponent's defence by 2.\n");
        int actionCost = getActionCost(500, 1500);
        String acceptSabotageCost = "";
        if (currentPlayer.getIsHuman())
        {
            acceptSabotageCost = playerInput.getStringInput("This will cost you " + actionCost + " coins. Do you accept? (Y/N): ");
        }
        else if (!currentPlayer.getIsHuman())
        {
            acceptSabotageCost = "Y";
        }
        if (acceptSabotageCost.equalsIgnoreCase("Y"))
        {
            if (currentPlayer.getCoins() < actionCost)
            {
                System.out.println("You do not have enough coins to sabotage your opponent's defence.");
                fileIO.updateFile(outputFileName, "Sabotage unsuccessful. " + currentPlayer.getName() + " does not have enough coins to sabotage their opponent's grid.\n");
            }
            else if (currentPlayer.getCoins() >= actionCost)
            {
                opponentPlayer.updateDefence(-2);
                currentPlayer.updateCoins(-actionCost);
                System.out.println("Sabotage successful. " + currentPlayer.getName() + " has decremented their opponent's defence by 2.\n");
                fileIO.updateFile(outputFileName, "Sabotage successful. " + currentPlayer.getName() + " has decremented their opponent's defence by 2.");
                fileIO.updateFile(outputFileName, " Cost of sabotage: " + actionCost + " coins.\n");
            }
            else
            {
                System.out.println("Error: Invalid input.");
            }
        }
        else
        {
            System.out.println(currentPlayer.getName() + " has rejected the cost of " + actionCost + " coins to sabotage their opponent's defence.\n");
        }
    }

    /**
     * Method to check if the game is over. If the game is over, the method will print whether the 
     * current player has won or lost the game.
     * 
     * @param humanPlayer The human player.
     * @param computerPlayer The computer player.
     * @return Returns true if the game is over, false otherwise.
     */
    public boolean gameOver(Player humanPlayer, Player computerPlayer)
    {
        if (humanPlayer.getNumHearts() == 0 || computerPlayer.getNumHearts() == 0)
        {

            if (computerPlayer.getNumHearts() == 0)
            {
                System.out.println("\n-------------------------------------------------");
                System.out.println("Y     Y    OOOO   U     U    W     W  II  N     N");
                System.out.println(" Y   Y    O    O  U     U    W     W  II  NN    N");
                System.out.println("  Y Y    O      O U     U    W     W  II  N N   N");
                System.out.println("   Y     O      O U     U    W  W  W  II  N  N  N");
                System.out.println("   Y     O      O U     U    W  W  W  II  N   N N");
                System.out.println("   Y      O     O  U   U     W  W  W  II  N    NN");
                System.out.println("   Y       OOOO     UUU      WWW WWW  II  N     N");
                System.out.println("-------------------------------------------------\n");
            }
            else if (humanPlayer.getNumHearts() == 0)
            {
                System.out.println("\n---------------------------------------------------------");
                System.out.println("Y     Y    OOOO   U     U    L        OOOO   SSSSS  EEEEE  ");
                System.out.println(" Y   Y    O    O  U     U    L       O    O  S      E      ");
                System.out.println("  Y Y    O      O U     U    L       O    O  S      E      ");
                System.out.println("   Y     O      O U     U    L       O    O  SSSSS  EEEEE  ");
                System.out.println("   Y     O      O U     U    L       O    O      S  E      ");
                System.out.println("   Y      O     O  U   U     L       O    O      S  E      ");
                System.out.println("   Y       OOOO     UUU      LLLLLLL  OOOO   SSSSS  EEEEE  ");
                System.out.println("---------------------------------------------------------\n");
            }
            else
            {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Method to calculate the cost of an action, such as an attack or sabotage.
     * 
     * The cost is a random number between the minCost and maxCost.
     * 
     * @param minCost The minimum cost of an action.
     * @param maxCost The maximum cost of an action.
     * 
     * @return The cost of an action.
     */
    public int getActionCost(int minCost, int maxCost)
    {
        Random random = new Random();
        Input playerInput = new Input();
        int actionCost = random.nextInt(maxCost - minCost) + minCost;
        return actionCost;
    }

    /**
     * Method to initialise the computer player.
     * 
     * @return The computer player object.
     */
    public Player initialiseComputerPlayer()
    {
        Player newComputerPlayer = new ComputerPlayer();
        System.out.println("\nWelcome your opponent: " + newComputerPlayer.getName() + "!\n");
        return newComputerPlayer;
    }

    /**
     * Method to initialise the human player.
     * 
     * @return The human player object.
     */
    public Player initialiseHumanPlayer()
    {
        Input playerNameInput = new Input();
        Validation nameValidator = new Validation();
        String name = playerNameInput.getStringInput("\nPlease enter your name: ");
        while (!nameValidator.lengthWithinRange(name, 1, 15) || nameValidator.isBlank(name))
        {
            name = playerNameInput.getStringInput("Please enter a valid name: ");
        }
        Player newHumanPlayer = new HumanPlayer(name);
        System.out.println("Welcome " + newHumanPlayer.getName() + "!");
        return newHumanPlayer;
    }



    /**
     * This method allows the current player to sabotage the opponent player if the option is available and 
     * chosen during the current player's turn.
     * 
     * @param currentPlayer The player whose turn it is.
     * @param opponentPlayer The opponent player, who is not taking their turn.
     * 
     * @return void
     */
    public void sabotageOpponent(Player currentPlayer, Player opponentPlayer)
    {   
        Input playerInput = new Input();
        int sabotageChoice;
        System.out.println("How would you like to sabotage your opponent?");
        System.out.println("\t1. Decrement the opponent's attack by 2");
        System.out.println("\t2. Decrement the opponent's defence by 2");
        System.out.println("\t3. Sabotage an opponent's grid square");
        if (currentPlayer.getIsHuman())
        {
            sabotageChoice = playerInput.getIntegerInput("Enter your choice: ");
        }
        else if (!currentPlayer.getIsHuman())
        {
            int computerChoice = (currentPlayer.hasCompletePath(grid)) ? dice.getRandomNumber(1, 10) : dice.getRandomNumber(1, 8);
            if (computerChoice <= 4) {        // 40% chance
                sabotageChoice = 1;
            } else if (computerChoice <= 8) { // 40% chance
                sabotageChoice = 2;
            } else {                          // 20% chance
                sabotageChoice = 3;
            }
        }
        else
        {
            System.out.println("Error: Invalid player type.");
            return;
        }
        while (sabotageChoice < 1 || sabotageChoice > 3)
        {
            sabotageChoice = playerInput.getIntegerInput("Please enter a valid choice: ");
        
        }
        switch (sabotageChoice)
        {
            case 1:
                decrementOpponentAttack(currentPlayer, opponentPlayer);
                break;
            case 2:
                decrementOpponentDefence(currentPlayer, opponentPlayer);
                break;
            case 3:
                sabotageOpponentCapturedSquare(currentPlayer, opponentPlayer);
                break;
        }
    }

    /**
     * Method to sabotage an opponent's captured square.
     * 
     * Note: This method is called by the sabotageOpponent method.
     * 
     * @param currentPlayer The player whose turn it is.
     * @param opponentPlayer The opponent player, who is not taking their turn.
     * 
     * @return void
     */
    public void sabotageOpponentCapturedSquare(Player currentPlayer, Player opponentPlayer)
    {
        Input playerInput = new Input();
        System.out.println(currentPlayer.getName() + " has chosen to sabotage an opponent's grid square.\n");
        fileIO.updateFile(outputFileName, currentPlayer.getName() + " has chosen to sabotage an opponent's grid square.\n");
        String acceptSabotageCost = "";
        int actionCost = getActionCost(1000, 2500);
        if (currentPlayer.getIsHuman())
        {
            acceptSabotageCost = playerInput.getStringInput("This will cost you " + actionCost + " coins. Do you accept? (Y/N): ");
        }
        else if (!currentPlayer.getIsHuman())
        {
            acceptSabotageCost = "Y";
        }
        if (acceptSabotageCost.equalsIgnoreCase("Y"))
        {
            if (currentPlayer.getCoins() < actionCost)
            {
                System.out.println("You do not have enough coins to sabotage your opponent's grid.");
                fileIO.updateFile(outputFileName, "Sabotage unsuccessful. " + currentPlayer.getName() + " does not have enough coins to sabotage their opponent's grid.\n");
            }
            else if (currentPlayer.getCoins() >= actionCost)
            {
                if (currentPlayer.getIsHuman())
                {
                    playerInput = new Input();
                    int[] capturedCoordinates = new int[2];
                    System.out.println("Enter the coordinates of the tile you would like to capture.");
                    capturedCoordinates[0] = playerInput.getIntegerInput("Enter the x coordinate: ");
                    while (capturedCoordinates[0] < 0 || capturedCoordinates[0] > grid.getGridSize())
                    {
                        capturedCoordinates[0] = playerInput.getIntegerInput("Please enter a valid x coordinate: ");
                    }
                    capturedCoordinates[1] = playerInput.getIntegerInput("Enter the y coordinate: ");
                    while (capturedCoordinates[1] < 0 || capturedCoordinates[1] > grid.getGridSize())
                    {
                        capturedCoordinates[1] = playerInput.getIntegerInput("Please enter a valid y coordinate: ");
                    }
                    while (!opponentPlayer.hasCapturedSquare(capturedCoordinates, this.grid))
                    {
                        System.out.print("Please enter coordinates of a valid tile. Valid coordinates: ");
                        for (int[] square : opponentPlayer.getSquaresCaptured())
                        {
                            System.out.print("(" + square[0] + ", " + square[1] + ") ");
                        }
                        System.out.println();
                        capturedCoordinates[0] = playerInput.getIntegerInput("Enter the x coordinate: ");
                        while (capturedCoordinates[0] < 0 || capturedCoordinates[0] > grid.getGridSize())
                        {
                            capturedCoordinates[0] = playerInput.getIntegerInput("Please enter a valid x coordinate: ");
                        }
                        capturedCoordinates[1] = playerInput.getIntegerInput("Enter the y coordinate: ");
                        while (capturedCoordinates[1] < 0 || capturedCoordinates[1] > grid.getGridSize())
                        {
                            capturedCoordinates[1] = playerInput.getIntegerInput("Please enter a valid y coordinate: ");
                        }
                    }
                    fileIO.updateFile(outputFileName, currentPlayer.getName() + " is attempting to capture the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!\n");
                    System.out.println("You have captured the tile!");
                    grid.updateGridWithCapturedSquare(currentPlayer, capturedCoordinates);
                    currentPlayer.addSquareCaptured(capturedCoordinates);
                    opponentPlayer.removeSquareCaptured(capturedCoordinates);
                    fileIO.updateFile(outputFileName, currentPlayer.getName() + " has captured the tile at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + ")!\n");

                }
                else if (!currentPlayer.getIsHuman())
                {
                    ArrayList<int[]> opponentSquaresCaptured = opponentPlayer.getSquaresCaptured();
                    int randomSquare = dice.getRandomNumber(0, opponentSquaresCaptured.size() - 1);
                    int[] capturedCoordinates = opponentSquaresCaptured.get(randomSquare);
                    grid.updateGridWithCapturedSquare(currentPlayer, capturedCoordinates);
                    currentPlayer.addSquareCaptured(capturedCoordinates);
                    opponentPlayer.removeSquareCaptured(capturedCoordinates);
                    fileIO.updateFile(outputFileName, currentPlayer.getName() + " has sabotaged " + opponentPlayer.getName() + "'s grid at (" + capturedCoordinates[0] + ", " + capturedCoordinates[1] + "). ");
                }
                currentPlayer.updateCoins(-actionCost);
                fileIO.updateFile(outputFileName, "Cost of sabotage: " + actionCost + " coins.\n");
            }
            else
            {
                System.out.println("Error: Invalid input.");
            }
        }
        else
        {
            System.out.println(currentPlayer.getName() + " has chosen not to sabotage an opponent's grid square.\n");
        }
    }

    /**
     * Method to strike an opponent's heart if the current player has a complete path across the grid.
     * 
     * @param currentPlayer The player whose turn it is.
     * @param opponentPlayer The opponent player, who is not taking their turn.
     * 
     * @return void
     */
    public void strikeOpponentHeart(Player currentPlayer, Player opponentPlayer)
    {
        if (currentPlayer.hasCompletePath(grid))
        {
            System.out.println("You have a complete path across the grid! You can now strike your opponent's heart!");
            opponentPlayer.updateHearts(-1);
            fileIO.updateFile(outputFileName, currentPlayer.getName() + " has struck " + opponentPlayer.getName() + "'s heart!\n");
        }
        else
        {
            System.out.println("You do not have a complete path across the grid. You cannot strike your opponent's heart yet.");
            fileIO.updateFile(outputFileName, currentPlayer.getName() + "'s strike failed - does not have a complete path across the grid.\n");
        }
    }
    
    /**
     * Method to to initiate and manage each player's turn.
     * 
     * @param currentPlayer The player whose turn it is.
     * @param opponentPlayer The player who is not taking their turn.
     * @param grid The grid object that the game is being played on.
     * 
     * @return void
     */
    public void takeTurn(Player currentPlayer, Player opponentPlayer, Grid grid)
    {
        Input playerInput = new Input();
        int playerChoice = 0;
        System.out.println("It is " + currentPlayer.getName() + "'s turn.");
        System.out.println("Which move would you like to make?");
        System.out.println("\t1. Capture a grid spot");
        System.out.println("\t2. Sabotage the your opponent");
        System.out.println("\t3. Strike your opponent's heart");
        if (currentPlayer.getIsHuman())
        {
            playerChoice = playerInput.getIntegerInput("Enter your choice: ");
            while (playerChoice < 1 || playerChoice > 3)
            {
                playerChoice = playerInput.getIntegerInput("Please enter a valid choice: ");
            }
        }
        else if (!currentPlayer.getIsHuman())
        {
            System.out.print(currentPlayer.getName() + " is thinking");
            for (int i = 0; i < 3; i++)
            {
                System.out.print(".");
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    System.out.println("Error: Thread interrupted.");
                }
            }
            playerChoice = (numTurns == 1) ? 1 : ( currentPlayer.hasCompletePath(grid) ? 3 : dice.D3() );
            if (opponentPlayer.hasCompletePath(grid))
            {
                playerChoice = 2;
            }
        }
        System.out.print(currentPlayer.getName() + " chooses: ");
        switch (playerChoice)
        {
            case 1:
                System.out.println("Capture a grid spot");
                fileIO.updateFile(outputFileName, currentPlayer.getName() + " attempts to capture a grid spot.\n");
                captureGridSpot(currentPlayer, opponentPlayer);
                break;
            case 2:
                System.out.println("Sabotage the enemy");
                fileIO.updateFile(outputFileName, currentPlayer.getName() + " attempts to sabotage the opponent.\n");
                sabotageOpponent(currentPlayer, opponentPlayer);
                break;
            case 3:
                System.out.println("Strike opponent's heart");
                fileIO.updateFile(outputFileName, currentPlayer.getName() + " attempts to strike the opponent's heart.\n");
                strikeOpponentHeart(currentPlayer, opponentPlayer);
                break;
        }
    }

    /**
     * Method to display the game's welcome message.
     */
    public static void welcomeGame()
    {
        fileIO = new FileIO();
        String welcomeMessageFile = "welcome.txt";
        String welcomeMessage = fileIO.readFile(welcomeMessageFile);
        System.out.println(welcomeMessage);
    }

    /**
     * The main method of the game. This is where the game runs from!
     * 
     * @param args
     * @return void
     */
    public static void main(String[] args)
    {
        welcomeGame();
        Field gameField = new Field();
        gameField.grid.displayGrid();
        fileIO.writeFile(outputFileName, "Game Start\n");
        fileIO.updateFile(outputFileName, "Players using a " + gameField.grid.getGridSize() + "x" + gameField.grid.getGridSize() + " grid\n");
        fileIO.updateFile(outputFileName, "Starting stats:");
        fileIO.updateFile(outputFileName, gameField.humanPlayer.getPlayerStats(gameField.grid));
        fileIO.updateFile(outputFileName, gameField.computerPlayer.getPlayerStats(gameField.grid));
        while (!gameOver)
        {
            fileIO.updateFile(outputFileName, "Turn: " + numTurns + "\n");
            gameField.takeTurn(gameField.humanPlayer, gameField.computerPlayer, gameField.grid);
            gameOver = gameField.gameOver(gameField.humanPlayer, gameField.computerPlayer);
            if (gameOver) break;
            gameField.takeTurn(gameField.computerPlayer, gameField.humanPlayer, gameField.grid);
            gameOver = gameField.gameOver(gameField.humanPlayer, gameField.computerPlayer);
            if (gameOver) break;
            fileIO.updateFile(outputFileName, gameField.humanPlayer.getPlayerStats(gameField.grid));
            fileIO.updateFile(outputFileName, gameField.computerPlayer.getPlayerStats(gameField.grid));
            gameField.humanPlayer.displayPlayerStats(gameField.grid);
            gameField.computerPlayer.displayPlayerStats(gameField.grid);
            grid.displayGrid();
            numTurns++;
        }
        String winner = (gameField.humanPlayer.getNumHearts() == 0) ? gameField.computerPlayer.getName() : gameField.humanPlayer.getName();
        fileIO.updateFile(outputFileName, "Game Over!\n");
        fileIO.updateFile(outputFileName, "Winner: " + winner + "\n");
        fileIO.updateFile(outputFileName, "Total number of turns: " + numTurns);
    }
}

