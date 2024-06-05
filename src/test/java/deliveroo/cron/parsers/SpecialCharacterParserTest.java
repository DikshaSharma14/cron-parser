package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecialCharacterParserTest {

    @Test
    public void testParse_AllValues() throws InvalidFieldValueException {
        List<Integer> result = SpecialCharacterParser.parse("*", 0,59);
        assertEquals(60, result.size()); // Including 0 to 59
    }

    @Test
    public void testParse_StepValue() throws InvalidFieldValueException {
        List<Integer> result = SpecialCharacterParser.parse("*/5", 0,59);
        assertEquals(List.of(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55), result);
    }

    @Test
    public void testParse_RangeValue() throws InvalidFieldValueException {
        List<Integer> result = SpecialCharacterParser.parse("10-15", 1,20);
        assertEquals(List.of(10, 11, 12, 13, 14, 15), result);
    }

    @Test
    public void testParse_ListValue() throws InvalidFieldValueException {
        List<Integer> result = SpecialCharacterParser.parse("1,3,5",1, 10);
        assertEquals(List.of(1, 3, 5), result);
    }

    @Test
    public void testParse_SingleValue() throws InvalidFieldValueException {
        int result = SpecialCharacterParser.parseSingleValue("JAN");
        assertEquals(1, result);
    }

    @Test
    public void testParse_InvalidStepValue() {
        assertThrows(InvalidFieldValueException.class, () -> {
            SpecialCharacterParser.parse("*/70", 0,59); // Step value exceeds max value
        });
    }

    @Test
    public void testParse_InvalidRangeValue() {
        assertThrows(InvalidFieldValueException.class, () -> {
            SpecialCharacterParser.parse("10-5", 0,20); // Invalid range (end < start)
        });
    }

    @Test
    public void testParse_InvalidListValue() {
        assertThrows(NumberFormatException.class, () -> {
            SpecialCharacterParser.parseListValue("1,abc,3",  10); // Invalid list value
        });
    }

    @Test
    public void testParse_InvalidSingleValue() {
        assertThrows(NumberFormatException.class, () -> {
            SpecialCharacterParser.parseSingleValue("XYZ"); // Invalid single value
        });
    }
}
