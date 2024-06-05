package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayOfMonthFieldParserTest {

    private final DayOfMonthFieldParser dayOfMonthFieldParser = new DayOfMonthFieldParser();

    @Test
    public void testParseField_AllValues() throws InvalidFieldValueException {
        String field = "*";
        String result = dayOfMonthFieldParser.parseField(field);
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31", result);
    }

    @Test
    public void testParseField_StepValue() throws InvalidFieldValueException {
        String field = "*/5";
        String result = dayOfMonthFieldParser.parseField(field);
        assertEquals("1 6 11 16 21 26 31", result);
    }

    @Test
    public void testParseField_RangeValue() throws InvalidFieldValueException {
        String field = "10-15";
        String result = dayOfMonthFieldParser.parseField(field);
        assertEquals("10 11 12 13 14 15", result);
    }

    @Test
    public void testParseField_ListValue() throws InvalidFieldValueException {
        String field = "1,15,30";
        String result = dayOfMonthFieldParser.parseField(field);
        assertEquals("1 15 30", result);
    }

    @Test
    public void testParseField_SingleValue() throws InvalidFieldValueException {
        String field = "25";
        String result = dayOfMonthFieldParser.parseField(field);
        assertEquals("25", result);
    }

    @Test
    public void testParseField_InvalidValue() {
        String field = "32"; // Out of range value
        assertThrows(InvalidFieldValueException.class, () -> {
            dayOfMonthFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidRange() {
        String field = "20-10"; // Invalid range (end < start)
        assertThrows(InvalidFieldValueException.class, () -> {
            dayOfMonthFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidStepValue() {
        String field = "*/32"; // Step value exceeds max value
        assertThrows(InvalidFieldValueException.class, () -> {
            dayOfMonthFieldParser.parseField(field);
        });
    }
}
