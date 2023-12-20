/**
 * Validation class for validating user input
 * 
 * @version 1.0
 * @author Daniel Hardej
 * Last updated: 2023-11-09
 */

public class Validation
{
    /**
     * Default constructor for objects of class Validation
     */
    public Validation()
    {

    }

    /**
     * Method to check if a string is blank
     * 
     * @param string The string to check
     */
    public boolean isBlank(String string)
    {
        return string.trim().equals("");
    }

    /**
     * Method to check if an int value provided is blank
     * 
     * @param int The integer to check
     */
    public boolean isBlank(int intValue) {
        return Integer.toString(intValue).trim().equals("");
    }

    /**
     * Method to check if a string's length is within a range
     * 
     * @param string The string to check
     * @param min The minimum length of the string
     * @param max The maximum length of the string
     */
    public boolean lengthWithinRange(String string, int min, int max) {
        return string.trim().length() >= min && string.trim().length() <= max;
    }

    /**
     * Method to check if a valid integer value has been entered
     * 
     * @param checkVal The value to check
     * @param min The minimum accepted value of the integer
     * @param max The maximum accepted value of the integer
     */
    public boolean validateIntegerValue(int checkVal, int min, int max) {
        return checkVal >= min && checkVal <= max;
    }
}