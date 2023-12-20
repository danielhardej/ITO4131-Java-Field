/**
 * PlayerTest.java
 * 
 * This class tests the Player class.
 * 
 * @version 1.0 
 * @since 2023-12-01
 * @author Daniel Hardej
 * Last updated: 2023-12-04
 */

public class PlayerTest {
    public static void main(String[] args) {
        // Create a Player object with the default constructor
        Player defaultPlayer = new Player();
        System.out.println("Default Player Name: " + defaultPlayer.getName()); // Should print "Player"

        // Create a Player object with the non-default constructor with valid field values
        Player validPlayer = new Player("Test", 10, 5, 100, 3, true);
        System.out.println("Valid Player Name: " + validPlayer.getName()); // Should print "Test"

        // Create a Player object with the non-default constructor with invalid field values
        // This depends on what you consider as invalid. For this example, let's consider negative values as invalid.
        Player invalidPlayer = new Player("Test", -10, -5, -100, -3, true);
        System.out.println("Invalid Player Damage: " + invalidPlayer.getDamage()); // Should print "-10"

        // Test all get methods
        System.out.println("Valid Player Defence: " + validPlayer.getDefence()); // Should print "5"
        System.out.println("Valid Player Coins: " + validPlayer.getCoins()); // Should print "100"
        System.out.println("Valid Player Hearts: " + validPlayer.getNumHearts()); // Should print "3"

        // Test all set methods with valid field values
        validPlayer.setDamage(15);
        System.out.println("Updated Valid Player Damage: " + validPlayer.getDamage()); // Should print "15"

        validPlayer.setDefence(10);
        System.out.println("Updated Valid Player Defence: " + validPlayer.getDefence()); // Should print "10"

        validPlayer.setCoins(150);
        System.out.println("Updated Valid Player Coins: " + validPlayer.getCoins()); // Should print "150"

        validPlayer.setHearts(2);
        System.out.println("Updated Valid Player Hearts: " + validPlayer.getNumHearts()); // Should print "2"

        // Test all set methods with invalid field values
        // This depends on what you consider as invalid. For this example, let's consider negative values as invalid.
        validPlayer.setDamage(-15);
        System.out.println("Updated Invalid Player Damage: " + validPlayer.getDamage()); // Should print "-15"

        validPlayer.setDefence(-10);
        System.out.println("Updated Invalid Player Defence: " + validPlayer.getDefence()); // Should print "-10"

        validPlayer.setCoins(-150);
        System.out.println("Updated Invalid Player Coins: " + validPlayer.getCoins()); // Should print "-150"

        validPlayer.setHearts(-2);
        System.out.println("Updated Invalid Player Hearts: " + validPlayer.getNumHearts()); // Should print "-2"
    }
}
