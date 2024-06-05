package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HourFieldParserTest {

    private final HourFieldParser hourFieldParser = new HourFieldParser();

    @Test
    public void testParseField_AllValues() throws InvalidFieldValueException {
        String field = "*";
        String result = hourFieldParser.parseField(field);
        assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23", result);
    }

    @Test
    public void testParseField_StepValue() throws InvalidFieldValueException {
        String field = "*/2";
        String result = hourFieldParser.parseField(field);
        assertEquals("0 2 4 6 8 10 12 14 16 18 20 22", result);
    }

    @Test
    public void testParseField_RangeValue() throws InvalidFieldValueException {
        String field = "9-15";
        String result = hourFieldParser.parseField(field);
        assertEquals("9 10 11 12 13 14 15", result);
    }

    @Test
    public void testParseField_ListValue() throws InvalidFieldValueException {
        String field = "1,4,7";
        String result = hourFieldParser.parseField(field);
        assertEquals("1 4 7", result);
    }

    @Test
    public void testParseField_SingleValue() throws InvalidFieldValueException {
        String field = "3";
        String result = hourFieldParser.parseField(field);
        assertEquals("3", result);
    }

    @Test
    public void testParseField_InvalidValue() {
        String field = "30"; // Out of range value
        assertThrows(InvalidFieldValueException.class, () -> {
            hourFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidRange() {
        String field = "15-10"; // Invalid range (end < start)
        assertThrows(InvalidFieldValueException.class, () -> {
            hourFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidStepValue() {
        String field = "*/30"; // Step value exceeds max value
        assertThrows(InvalidFieldValueException.class, () -> {
            hourFieldParser.parseField(field);
        });
    }
}
