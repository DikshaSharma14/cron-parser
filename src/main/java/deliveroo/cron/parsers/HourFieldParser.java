package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

import java.util.List;
import java.util.stream.Collectors;

public class HourFieldParser extends FieldParser {

    private static final int MAX_HOUR_VALUE = 23;

    @Override
    protected int getMaxValue() {
        return MAX_HOUR_VALUE;
    }

    @Override
    public String parseField(String field) throws InvalidFieldValueException {
        // Parse the hour field into a list of integer values
        List<Integer> values =  SpecialCharacterParser.parse(field, 0, MAX_HOUR_VALUE);
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
