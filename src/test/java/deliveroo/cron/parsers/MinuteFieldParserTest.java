package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinuteFieldParserTest {

    private final MinuteFieldParser minuteFieldParser = new MinuteFieldParser();

    @Test
    public void testParseField_AllValues() throws InvalidFieldValueException {
        String field = "*";
        String result = minuteFieldParser.parseField(field);
        assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59", result);
    }

    @Test
    public void testParseField_StepValue() throws InvalidFieldValueException {
        String field = "*/10";
        String result = minuteFieldParser.parseField(field);
        assertEquals("0 10 20 30 40 50", result);
    }

    @Test
    public void testParseField_RangeValue() throws InvalidFieldValueException {
        String field = "5-15";
        String result = minuteFieldParser.parseField(field);
        assertEquals("5 6 7 8 9 10 11 12 13 14 15", result);
    }

    @Test
    public void testParseField_ListValue() throws InvalidFieldValueException {
        String field = "1,15,30";
        String result = minuteFieldParser.parseField(field);
        assertEquals("1 15 30", result);
    }

    @Test
    public void testParseField_SingleValue() throws InvalidFieldValueException {
        String field = "25";
        String result = minuteFieldParser.parseField(field);
        assertEquals("25", result);
    }

    @Test
    public void testParseField_InvalidValue() {
        String field = "60"; // Out of range value
        assertThrows(InvalidFieldValueException.class, () -> {
            minuteFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidRange() {
        String field = "30-20"; // Invalid range (end < start)
        assertThrows(InvalidFieldValueException.class, () -> {
            minuteFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidStepValue() {
        String field = "*/70"; // Step value exceeds max value
        assertThrows(InvalidFieldValueException.class, () -> {
            minuteFieldParser.parseField(field);
        });
    }
}
