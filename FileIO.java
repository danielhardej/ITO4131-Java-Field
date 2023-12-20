import java.util.Scanner;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FileIO class
 * 
 * This class is used to read and write to files.
 * 
 * @version 1.0
 * @author Daniel Hardej
 * Last updated: 2023-11-22
 */
 
 public class FileIO
 {

    String fileName;

    /**
     * Default constructor for objects of class FileIO
     */
    public FileIO () {}

    /**
     * Non-default constructor for objects of class FileIO
     * 
     * @param fileName The name of the file to be read from or written to
     */
    public FileIO (String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * Method to create a Scanner object to read from a file
     * 
     * @param fileName The name of the file to be read from
     * @return A Scanner object to read from the file
     */
    public Scanner getFileScanner(String fileName)
    {
        Scanner fileScanner = null;
        try {
            FileReader file = new FileReader(fileName);
            fileScanner = new Scanner(file);
        } catch (Exception e) {
            System.out.println("Error in reading file: " + e.getMessage());
        }
        return fileScanner;
    }

    /**
     * Method to return the number of lines in a file
     * 
     * @param fileName The name of the file to be read from
     * @return The number of lines in the file
     */
    public int getNumLines(String fileName)
    {
        int numLines = 0;
        try {
            FileReader file = new FileReader(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
                numLines++;
            }
            file.close();
            fileScanner.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(fileName + " not found: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error in reading file: " + e.getMessage());
        }
        return numLines;
    }

    /**
     * Method to read a file and return its contents as a String
     * 
     * @param fileName The name of the file to be read from
     * @return The contents of the file as a String
     */
    public String readFile(String fileName) {
        String fileContent = "";
        try {
            FileReader file = new FileReader(fileName);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                fileContent += fileScanner.nextLine() + "\n";
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(fileName + " not found: " + e.getMessage());
        }
        catch (Exception e) {
            System.err.println("Error in reading file: " + e.getMessage());
        }
        return fileContent;
    }

    /**
     * Method to write to a file
     * 
     * @param fileName The name of the file to be written to
     * @param fileContents The contents to be written to the file
     * @return void
     */
    public void writeFile(String fileName, String fileContents)
    {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(fileContents);
        } catch (IOException e) {
            System.err.println("Error in writing file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error in writing file: " + e.getMessage());
        } finally {
            fileWriter.close();
        }
    }

    /** 
     * Method to append to a file. Used to update an existing file, without over-
     * writing its existing contents.
     * 
     * @param fileName The name of the file to be appended to
     * @param newFileContents The contents to be appended to the file
    */
    public void updateFile(String fileName, String newFileContents)
    {
        try {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(newFileContents);
        } catch (IOException e) {
            System.err.println("Error in writing file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error in writing file: " + e.getMessage());
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
    }
}
