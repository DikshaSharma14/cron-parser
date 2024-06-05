package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

import java.util.List;
import java.util.stream.Collectors;

public class DayOfMonthFieldParser extends FieldParser {

    private static final int MAX_DAY_OF_MONTH_VALUE = 31;

    @Override
    protected int getMaxValue() {
        return MAX_DAY_OF_MONTH_VALUE;
    }

    @Override
    public String parseField(String field) throws InvalidFieldValueException {
        // Parse the day of month field into a list of integer values
        List<Integer> values =  SpecialCharacterParser.parse(field, 1, MAX_DAY_OF_MONTH_VALUE);
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }


}
