package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

import java.util.List;
import java.util.stream.Collectors;

public class MinuteFieldParser extends FieldParser {

    private static final int MAX_MINUTE_VALUE = 59;

    @Override
    public String parseField(String field) throws InvalidFieldValueException {
        // Parse the minute field into a list of integer values
        List<Integer> values =  SpecialCharacterParser.parse(field, 0, MAX_MINUTE_VALUE);
        return values.stream()
                .map(Object::toString)
                .collect(Collectors.joining(" "));
    }
    @Override
    protected int getMaxValue() {
        return MAX_MINUTE_VALUE;
    }



}
