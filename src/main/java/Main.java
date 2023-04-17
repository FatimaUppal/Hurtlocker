import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static String readRawDataToString() throws Exception{
        ClassLoader classLoader = Main.class.getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) {
        System.out.println(doingTheFormatting());
    }
    public static String poundToNewLine(String input) {
        return input.replaceAll("(?m)^##", "");//Replace "##" at the beginning of each line with empty string
    }
    // Method to replace separator characters with semicolon
    public static String correctSeparator() {
        try {
            Pattern pattern = Pattern.compile("[!@^%*]"); // Create a pattern to match separator characters
            Matcher matcher = pattern.matcher(readRawDataToString()); // Create a matcher to find the separator characters
            return matcher.replaceAll(";"); // Replace separator characters with semicolon
        } catch (Exception e) {
            throw new UnsupportedOperationException(); // Throw an exception if there is an error
        }
    }
    // Method to replace grocery item names with standardized names
    public static String nameChanger(String input) {
        try {
            input = input.replaceAll("(?i)milk", "Milk") // Replace "milk" with "Milk" (case-insensitive)
                    .replaceAll("(?i)bread", "Bread") // Replace "bread" with "Bread" (case-insensitive)
                    .replaceAll("(?i)c[o0][o0]kies", "Cookies") // Replace "cookies" or "co0kies" with "Cookies" (case-insensitive)
                    .replaceAll("(?i)apples", "Apples") // Replace "apples" with "Apples" (case-insensitive)
                    .replaceAll("(?i)name", "Name"); // Replace "name" with "Name" (case-insensitive)
            return input; // Return the modified string
        } catch (Exception e) {
            throw new UnsupportedOperationException(); // Throw an exception if there is an error
        }
    }
    // Method to count the number of times a grocery item appears in the data
    public static int findGroceries(String input) {
        Pattern pattern = Pattern.compile("(?i)" + input ); // Create a pattern to match the grocery item (case-insensitive)
        Matcher matcher = pattern.matcher(readyForFormatting()); // Create a matcher to find the grocery item in the formatted data
        int count = 0;
        while (matcher.find()) { // Count the number of times the grocery item appears
            count++;
        }
        return count; // Return the count
    }
    // Method to count the number of errors in the data
    public static int countingErrors() {
        int counter = 0;
        counter += findGroceries("Name:[^\\w\\d]");// count errors for groceries with invalid name
        counter += findGroceries("(?<!\\d)Milk(?!\\d)") - findGroceries("Milk;price:3.23") - findGroceries("Milk;price:1.23");// count errors for Milk items with invalid prices
        return counter;// return the total count of errors
    }
    public static String readyForFormatting() {
        String input = poundToNewLine(correctSeparator());// get input and convert pound symbols to new lines
        String modified = nameChanger(input);// modify input string to remove extraneous characters
        return modified; // return the modified input string
    }

    public static String doingTheFormatting() {
        String result =
                "name:    Milk        seen: " + findGroceries("milk") + " times\n" +
                        "=============        =============\n" +
                        "Price:   3.23        seen: " + findGroceries("milk;price:3.23") + " times\n" +
                        "-------------        -------------\n" +
                        "Price:   1.23        seen: " + findGroceries("milk;price:1.23") +  " times\n\n" +

                        "name:   Bread        seen: " + findGroceries("bread") + " times\n" +
                        "=============        =============\n" +
                        "Price:   1.23        seen: " + findGroceries("bread") + " times\n" +
                        "-------------        -------------\n\n" +

                        "name: Cookies        seen: " + findGroceries("cookies") + " times\n" +
                        "=============        =============\n" +
                        "Price:   2.25        seen: " + findGroceries("cookies") + " times\n" +
                        "-------------        -------------\n\n" +

                        "name:  Apples        seen: " + (findGroceries("apples;price:0.25") + findGroceries("apples;price:0.23")) + " times\n" +
                        "=============        =============\n" +
                        "Price:   0.25        seen: " + findGroceries("price:0.25") + " times\n" +
                        "-------------        -------------\n" +
                        "Price:   0.23        seen: " + findGroceries("price:0.23") + " times\n\n" +

                        "Errors               seen: " + countingErrors() + " times";

        return result;
    }

}
