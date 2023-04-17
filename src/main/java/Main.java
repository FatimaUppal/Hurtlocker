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
        return input.replaceAll("(?m)^##", "");
    }
    public static String correctSeparator() {
        try {
            Pattern pattern = Pattern.compile("[!@^%*]");
            Matcher matcher = pattern.matcher(readRawDataToString());
            return matcher.replaceAll(";");
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }
    public static String nameChanger(String input) {
        try {
            input = input.replaceAll("(?i)milk", "Milk")
                    .replaceAll("(?i)bread", "Bread")
                    .replaceAll("(?i)c[o0][o0]kies", "Cookies")
                    .replaceAll("(?i)apples", "Apples")
                    .replaceAll("(?i)name", "Name");
            return input;
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }
    public static int findGroceries(String input) {
        Pattern pattern = Pattern.compile("(?i)" + input );
        Matcher matcher = pattern.matcher(readyForFormatting());
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    public static int countingErrors() {
        int counter = 0;

        counter += findGroceries("Name:[^\\w\\d]");
        counter += findGroceries("(?<!\\d)Milk(?!\\d)") - findGroceries("Milk;price:3.23") - findGroceries("Milk;price:1.23");
        return counter;
    }
    public static String readyForFormatting() {
            String input = poundToNewLine(correctSeparator());
            String modified = nameChanger(input);
            return modified;
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
