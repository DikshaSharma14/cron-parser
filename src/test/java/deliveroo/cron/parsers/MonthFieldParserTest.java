package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonthFieldParserTest {

    private final MonthFieldParser monthFieldParser = new MonthFieldParser();

    @Test
    public void testParseField_AllValues() throws InvalidFieldValueException {
        String field = "*";
        String result = monthFieldParser.parseField(field);
        assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", result);
    }

    @Test
    public void testParseField_StepValue() throws InvalidFieldValueException {
        String field = "*/2";
        String result = monthFieldParser.parseField(field);
        assertEquals("1 3 5 7 9 11", result);
    }

    @Test
    public void testParseField_RangeValue() throws InvalidFieldValueException {
        String field = "3-6";
        String result = monthFieldParser.parseField(field);
        assertEquals("3 4 5 6", result);
    }

    @Test
    public void testParseField_ListValue() throws InvalidFieldValueException {
        String field = "1,4,7,10";
        String result = monthFieldParser.parseField(field);
        assertEquals("1 4 7 10", result);
    }

    @Test
    public void testParseField_SingleValue() throws InvalidFieldValueException {
        String field = "9";
        String result = monthFieldParser.parseField(field);
        assertEquals("9", result);
    }

    @Test
    public void testParseField_InvalidValue() {
        String field = "13"; // Out of range value
        assertThrows(InvalidFieldValueException.class, () -> {
            monthFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidRange() {
        String field = "6-3"; // Invalid range (end < start)
        assertThrows(InvalidFieldValueException.class, () -> {
            monthFieldParser.parseField(field);
        });
    }

    @Test
    public void testParseField_InvalidStepValue() {
        String field = "*/13"; // Step value exceeds max value
        assertThrows(InvalidFieldValueException.class, () -> {
            monthFieldParser.parseField(field);
        });
    }
}
