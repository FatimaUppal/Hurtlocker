import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void testReadRawDataToString() throws Exception {
        // Test that the method returns a non-empty string
        String result = Main.readRawDataToString();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }
    @Test
    public void testPoundToNewLine() {
        // Test that ## is replaced with newline character
        String input = "## Milk";
        String expectedOutput = " Milk";
        assertEquals(expectedOutput, Main.poundToNewLine(input));
    }

    @Test
    public void testNameChanger() {

        String input = "milk bread cookies apples";
        String expectedOutput = "Milk Bread Cookies Apples";
        assertEquals(expectedOutput, Main.nameChanger(input));
    }
    @Test
    public void testFindGroceries() {
        String input = "milk;price:3.23";
        int expected = 5;
        int actual = Main.findGroceries(input);
        assertEquals(expected, actual);
    }
    @Test
    public void testCountingErrors() {

        assertEquals(4, Main.countingErrors());
    }
    @Test
    public void testDoingTheFormatting() {
        String expectedOutput = "name:    Milk        seen: 8 times\n" +
                "=============        =============\n" +
                "Price:   3.23        seen: 5 times\n" +
                "-------------        -------------\n" +
                "Price:   1.23        seen: 1 times\n\n" +

                "name:   Bread        seen: 6 times\n" +
                "=============        =============\n" +
                "Price:   1.23        seen: 6 times\n" +
                "-------------        -------------\n\n" +

                "name: Cookies        seen: 8 times\n" +
                "=============        =============\n" +
                "Price:   2.25        seen: 8 times\n" +
                "-------------        -------------\n\n" +

                "name:  Apples        seen: 4 times\n" +
                "=============        =============\n" +
                "Price:   0.25        seen: 2 times\n" +
                "-------------        -------------\n" +
                "Price:   0.23        seen: 2 times\n\n" +

                "Errors               seen: 4 times";

        String actualOutput = Main.doingTheFormatting();

        assertEquals(expectedOutput, actualOutput);
    }

}




