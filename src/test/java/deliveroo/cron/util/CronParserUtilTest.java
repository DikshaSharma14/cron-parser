package deliveroo.cron.util;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import deliveroo.cron.util.CronParserUtil;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CronParserUtilTest {

    @Test
    public void testParseAndPrint_ValidExpression() throws InvalidFieldValueException {
        String cronExpression = "*/15 0 1,15 * 1-5 /usr/bin/find";
        CronParserUtil parserUtil = new CronParserUtil(cronExpression);

        // Redirect System.out to capture output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Call parseAndPrint method
        parserUtil.parseAndPrint();

        // Reset System.out
        System.setOut(System.out);

        String expectedOutput = String.format("%-14s %s%n", "minute", "0 15 30 45") +
                String.format("%-14s %s%n", "hour", "0") +
                String.format("%-14s %s%n", "day of month", "1 15") +
                String.format("%-14s %s%n", "month", "1 2 3 4 5 6 7 8 9 10 11 12") +
                String.format("%-14s %s%n", "day of week", "1 2 3 4 5") +
                String.format("%-14s %s%n", "command", "/usr/bin/find");

       // assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testParseAndPrint_InvalidExpression() throws InvalidFieldValueException {
        String invalidExpression = "*/15 0 1,15 * 1-5"; // Missing command field
        CronParserUtil parserUtil = new CronParserUtil(invalidExpression);

        // Redirect System.err to capture error message
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errorStream));

        // Call parseAndPrint method
        parserUtil.parseAndPrint();

        // Reset System.err
        System.setErr(System.err);

        // Verify the error message
        assertTrue(errorStream.toString().contains("Invalid cron expression format"));
    }
}
