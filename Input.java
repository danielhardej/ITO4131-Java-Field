/**
 * Input class
 * Used to take a user's input with adding a new student, enrolment, or unit
 * Ensures that a user enters the correct data type
 * 
 * @version 1.0
 * @author Daniel Hardej
 * @since 2023-11-09
 * Last updated: 2023-11-09
 */

import java.util.Scanner;

public class Input {
    
    /**
     * Default constructor for objects of class Input
     */
    public Input() {}

    /**
     * Method to get a char input from the user
     * 
     * @param prompt The prompt to display to the user
     * @param idx The index of the char to get from the user
     * @return The char input from the user
     */
    public char getCharInput(String prompt, int idx) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        char inputChar = scanner.next().charAt(idx);
        return inputChar;
    }

    /**
     * Method to get a double input from the user
     * 
     * @param prompt The prompt to display to the user
     * @return The double input from the user
     */
    public double getDoubleInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        double inputDouble = scanner.nextDouble();
        return inputDouble;
    } 

    /**
     * Method to get an integer input from the user
     * 
     * @param prompt The prompt to display to the user
     * @return The integer input from the user
     */
    public int getIntegerInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        int inputInt;
        try {
            inputInt = scanner.nextInt();
        } catch (Exception e) {
            System.out.println(e.getMessage() + " Invalid input. Please enter a valid integer.");
            inputInt = scanner.nextInt();
        }
        return inputInt;
    }

    /**
     * Method to get a string input from the user
     * 
     * @param prompt The prompt to display to the user
     * @return The string input from the user
     */
    public String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        String inputString = scanner.nextLine();
        while (!(inputString instanceof String)) {
            System.out.println("Invalid input. Please enter a valid string.");
            inputString = scanner.nextLine();
        }
        return inputString;
    }
}
 