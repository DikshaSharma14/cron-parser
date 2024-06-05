package deliveroo.cron.parsers;

import deliveroo.cron.exceptions.InvalidFieldValueException;

public class CommandParser extends FieldParser {

    private static final int MAX_LENGTH_OF_COMMAND = -1;

    @Override
    protected int getMaxValue() {
        return -1;
    }
    @Override
    public String parseField(String field) throws InvalidFieldValueException {
        return field;
    }
}
