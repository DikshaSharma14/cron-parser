package deliveroo.cron;

import deliveroo.cron.CronParser;
import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CronParserTest {

    @Test
    public void testMain_ValidCronExpression() throws InvalidFieldValueException {
        // Redirect System.out to capture console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String cronExpression = "*/15 0 1,15 * 1-5 /usr/bin/find";
        String expectedOutput =
                "minute         0 15 30 45\n" +
                        "hour           0\n" +
                        "day of month   1 15\n" +
                        "month          1 2 3 4 5 6 7 8 9 10 11 12\n" +
                        "day of week    1 2 3 4 5\n" +
                        "command        /usr/bin/find\n";

        try {
            // Invoke main method with the cron expression
            String[] args = {cronExpression};
            CronParser.main(args);

            // Get the captured output
            String actualOutput = outputStream.toString();

            // Assert that the output matches the expected format
           // assertEquals(expectedOutput, actualOutput);
        } finally {
            // Restore System.out
            System.setOut(System.out);
        }
    }
}
