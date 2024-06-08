package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

import java.util.List;
import java.util.stream.Collectors;

public class YearFieldParser extends FieldParser {
    private static final int MAX_VALUE_OF_THE_YEAR = 3000;

    @Override
    protected int getMaxValue() {
        return 0;
    }

    @Override
    public String parseField(String field) throws InvalidFieldValueException {
        // Parse the day of month field into a list of integer values
        List<Integer> values =  SpecialCharacterParser.parse(field, 2000, MAX_VALUE_OF_THE_YEAR);
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
