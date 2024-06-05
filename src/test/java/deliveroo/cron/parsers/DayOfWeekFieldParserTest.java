package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayOfWeekFieldParserTest {

    private final DayOfWeekFieldParser dayOfWeekFieldParser = new DayOfWeekFieldParser();

    @Test
    public void testParseField_AllValues() throws InvalidFieldValueException {
        String field = "*";
        String result = dayOfWeekFieldParser.parseField(field);
        assertEquals("1 2 3 4 5 6 7", result);
    }

    @Test
    public void testParseField_StepValue() throws InvalidFieldValueException {
        String field = "*/2";
        String result = dayOfWeekFieldParser.parseField(field);
        assertEquals("1 3 5 7", result);
    }

    @Test
    public void testParseField_RangeValue() throws InvalidFieldValueException {
        String field = "2-5";
        String result = dayOfWeekFieldParser.parseField(field);
        assertEquals("2 3 4 5", result);
    }

    @Test
    public void testParseField_ListValue() throws InvalidFieldValueException {
        String field = "1,4,7";
        String result = dayOfWeekFieldParser.parseField(field);
        assertEquals("1 4 7", result);
    }

    @Test
    public void testParseField_SingleValue() throws InvalidFieldValueException {
        String field = "3";
        String result = dayOfWeekFieldParser.parseField(field);
        assertEquals("3", result);
    }

    @Test
    public void testParseField_InvalidValue() {
        String field = "8"; // Out of range value
        assertThrows(InvalidFieldValueException.class, () -> {
            dayOfWeekFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidRange() {
        String field = "5-2"; // Invalid range (end < start)
        assertThrows(InvalidFieldValueException.class, () -> {
            dayOfWeekFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidStepValue() {
        String field = "*/8"; // Step value exceeds max value
        assertThrows(InvalidFieldValueException.class, () -> {
            dayOfWeekFieldParser.parseField(field);
        });
    }
}
