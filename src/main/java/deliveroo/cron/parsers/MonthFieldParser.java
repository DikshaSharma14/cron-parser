package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

import java.util.List;
import java.util.stream.Collectors;

public class MonthFieldParser extends FieldParser {

    private static final int MAX_MONTH_VALUE = 12;

    @Override
    protected int getMaxValue() {
        return MAX_MONTH_VALUE;
    }
    @Override
    public String parseField(String field) throws InvalidFieldValueException {
        // Parse the month field into a list of integer values
        List<Integer> values =  SpecialCharacterParser.parse(field, 1, MAX_MONTH_VALUE);
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
